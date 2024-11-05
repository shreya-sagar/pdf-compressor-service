package com.shreyasagar.pdfcompressor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class PdfCompressionServiceImpl implements com.shreyasagar.pdfcompressor.service.PdfCompressionService {

    private static final Logger log = LoggerFactory.getLogger(PdfCompressionServiceImpl.class);

    @Override
    public File compressPdf(MultipartFile file, String compressionLevel) {
        try(InputStream inputStream = file.getInputStream();
            PDDocument document = Loader.loadPDF(new RandomAccessReadBuffer(inputStream))) {

            //TODO: Add compression logic
            document.setAllSecurityToBeRemoved(true);

            for(PDPage page : document.getPages()) {
                PDResources resources = page.getResources();
                for (COSName cosName : resources.getXObjectNames()) {
                    if(resources.isImageXObject(cosName)) {
                        compressImage(page, resources.getXObject(cosName), compressionLevel);
                    }
                }
            }
            File compressedFile = new File("compressed_" + file.getOriginalFilename());
            document.save(compressedFile);
            return compressedFile;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void compressImage(PDPage page, PDXObject xObject, String compressionLevel) {

    }
}
