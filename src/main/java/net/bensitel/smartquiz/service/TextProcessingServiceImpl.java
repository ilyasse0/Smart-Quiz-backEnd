package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.QcmDto;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class TextProcessingServiceImpl implements  TextProcessingService {
    @Override
    public String extractTextFromFile(InputStream inputStream) throws IOException {

        try(PDDocument document = Loader.loadPDF(new RandomAccessReadBuffer(inputStream))){
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }

        // todo --> step 1: well clean the text and normalized it using nlp later

        // return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

    }


}
