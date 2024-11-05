package com.shreyasagar.pdfcompressor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PdfCompressionService {
    File compressPdf(MultipartFile file, String compressionLevel);
}
