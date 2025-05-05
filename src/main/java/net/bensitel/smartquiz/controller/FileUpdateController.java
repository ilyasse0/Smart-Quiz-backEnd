package net.bensitel.smartquiz.controller;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.DocumentDto;
import net.bensitel.smartquiz.dto.QcmDto;
import net.bensitel.smartquiz.entity.Document;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.service.DocumentService;
import net.bensitel.smartquiz.service.TextProcessingService;
import net.bensitel.smartquiz.service.UserService;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class FileUpdateController {

    private final Tika tika;
    private final  TextProcessingService textProcessingService;
    private final UserService userService;
    private final DocumentService documentService;

//
//    @PostMapping("/upload")
//   public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
//                                            @RequestParam("title") String title,
//                                            @AuthenticationPrincipal UserDetails userDetails) throws IOException {
//        User uploader = userService.findByUserName(userDetails.getUsername());
//        DocumentDto document = documentService.storeDocument(file, title, uploader);
//
//
//        ///  for extracting the text from the doc
//        String extractedText = textProcessingService.extractTextFromFile(file.getInputStream());
//        System.out.println("the text extracted -->"+extractedText);
//        return ResponseEntity.ok(document);
//    }
}
