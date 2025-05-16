package net.bensitel.smartquiz.controller;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.AttemptRequest;
import net.bensitel.smartquiz.dto.GeneratedQcm;
import net.bensitel.smartquiz.dto.QcmDto;
import net.bensitel.smartquiz.dto.QcmSetDtoToAttempt;
import net.bensitel.smartquiz.entity.QcmSet;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.service.*;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class QcmGenerationController {

    private final Tika tika;
    private final HuggingFaceService huggingFaceService;
    private final TextProcessingService textProcessingService;
    private final GeminiService geminiService;
    private final UserService userService;
    private final QcmParser qcmParser;
    private final  QcmGeneratorService qcmGeneratorService;
    private final AttemptService attemptService;

    @PostMapping(value = "/qcm" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> generateQcmFromText(@RequestParam("file") MultipartFile file ,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Extract text from file
            String extractedText = textProcessingService.extractTextFromFile(file.getInputStream());
            //System.out.println("the text extracted -->"+extractedText);
            // Generate QCM using Gemini API
            //
            String qcmRawText = geminiService.generateQcm(extractedText);
            /// here we parse and hadnle the raw qcm
            GeneratedQcm parsedQcm = qcmParser.parse(qcmRawText);
            // we save here the final form for the qcm
            User user = userService.findByUserName(userDetails.getUsername());
            QcmSet saved = qcmGeneratorService.saveGeneratedQcm(parsedQcm, user, file);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error generating QCM: " + e.getMessage());
        }
    }


    @GetMapping("/Qcm/{id}")
    public ResponseEntity<QcmSetDtoToAttempt> getQcmToAttempt(@PathVariable Long id) {
        QcmSetDtoToAttempt qcmSet  =qcmGeneratorService.getQcmToAttempt(id);
        return ResponseEntity.ok(qcmSet);
    }



    ///  todo -->  test this function ghada A w9
    @PostMapping("/submit")
    public ResponseEntity<?> submitAttemp(@RequestBody AttemptRequest attemptRequest,
                                          @AuthenticationPrincipal UserDetails userDetails
                                          ){
        User user = userService.findByUserName(userDetails.getUsername());
       // User user1 = userService.getUserById(userDetails.ge)
        int score = attemptService.processAttempt(user , attemptRequest);
        return ResponseEntity.ok(Map.of("score", score));
    }

    }



