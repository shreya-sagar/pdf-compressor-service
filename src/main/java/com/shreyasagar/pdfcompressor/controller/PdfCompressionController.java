package com.shreyasagar.pdfcompressor.controller;

import com.shreyasagar.pdfcompressor.service.PdfCompressionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/api/pdf")
public class PdfCompressionController {

    private static final Logger log = LoggerFactory.getLogger(PdfCompressionController.class);

    @Autowired
    PdfCompressionService pdfCompressionService;

    @PostMapping("/compress")
    public ResponseEntity<String> compressPdf(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "level", defaultValue = "medium") String compressionLevel) {
        try {
            File compressedFile = pdfCompressionService.compressPdf(file, compressionLevel);
            return ResponseEntity.ok("PDF compressed successfully : " + compressedFile.getName());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to compress PDF");
        }
    }
}
