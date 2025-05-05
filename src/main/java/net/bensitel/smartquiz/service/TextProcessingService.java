package net.bensitel.smartquiz.service;

import net.bensitel.smartquiz.dto.QcmDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TextProcessingService {
    //List<QcmDto> generateQcmFromText(String extractedText);
    String extractTextFromFile(InputStream inputStream) throws IOException;

}
