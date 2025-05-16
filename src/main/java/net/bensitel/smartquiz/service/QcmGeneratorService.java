package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.DocumentDto;
import net.bensitel.smartquiz.dto.GeneratedQcm;
import net.bensitel.smartquiz.dto.GeneratedQuestion;
import net.bensitel.smartquiz.dto.QcmSetDtoToAttempt;
import net.bensitel.smartquiz.entity.*;
import net.bensitel.smartquiz.mapper.DocumentMapper;
import net.bensitel.smartquiz.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QcmGeneratorService {
    private final QcmRepository qcmRepository;
    private final UserRepository userRepository;
    private final DocumentService documentService;
    private final DocumentMapper documentMapper;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;


    public QcmSet saveGeneratedQcm(GeneratedQcm gqcm, User user, MultipartFile file) throws IOException {
//        // Sauvegarde du document
//        Document doc = Document.builder()
//                .title(file.getOriginalFilename())
//                .filename(file.getOriginalFilename())
//                //.content(textProcessingService.extractTextFromFile(file.getInputStream()))
//                .uploadedAt(LocalDateTime.now())
//                .build();
//        qcmRepository.save(doc);
        Document doc = documentService.storeDocument(file, file.getOriginalFilename(), user);

        // Création du QcmSet
        QcmSet qcmSet = QcmSet.builder()
                .title(gqcm.getTitle())
                .topic(gqcm.getTopic())
                .document(doc)
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();
        qcmRepository.save(qcmSet);

        // Questions + Options
        for (GeneratedQuestion gq : gqcm.getQuestions()) {
            Question q = Question.builder()
                    .qcmSet(qcmSet)
                    .content(gq.getContent())
                    .difficulty(gq.getDifficulty())
                    .correctOption(gq.getCorrectOption())
                    .build();
            questionRepository.save(q);

            for (Map.Entry<Character, String> entry : gq.getOptions().entrySet()) {
                Option opt = Option.builder()
                        .question(q)
                        .optionLabel(entry.getKey())
                        .text(entry.getValue())
                        .build();
                optionRepository.save(opt);
            }
        }

        return qcmSet;
    }

    public QcmSetDtoToAttempt getQcmToAttempt(Long id) {
        // Fetch the QCM set
        QcmSet qcmSet = qcmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No Qcm found with id: " + id));

        // Fetch all questions related to the QCM set
        List<Question> questions = questionRepository.findByQcmSet_Id(id);

        // Initialize the DTO
        QcmSetDtoToAttempt dto = new QcmSetDtoToAttempt();
        dto.setQcmSetId(qcmSet.getId());
        dto.setTitle(qcmSet.getTitle());
        dto.setTopic(qcmSet.getTopic());

        // Convert questions to DTOs
        List<QcmSetDtoToAttempt.QuestionDtoToAttempt> questionDtos = questions.stream().map(q -> {
            QcmSetDtoToAttempt.QuestionDtoToAttempt qDto = new QcmSetDtoToAttempt.QuestionDtoToAttempt();
            qDto.setQuestionId(q.getId());
            qDto.setContent(q.getContent());

            // Fetch options by question ID (entity-based)
            List<Option> optionEntities = optionRepository.findByQuestionId(q.getId());

            List<QcmSetDtoToAttempt.OptionDto> options = optionEntities.stream().map(option -> {
                QcmSetDtoToAttempt.OptionDto oDto = new QcmSetDtoToAttempt.OptionDto();
                oDto.setOptionId(option.getId());
                oDto.setOptionLabel(option.getOptionLabel());
                oDto.setText(option.getText());
                return oDto;
            }).toList();

            qDto.setOptions(options);
            return qDto;
        }).toList();

        dto.setQuestions(questionDtos);
        return dto;
    }

    public QcmSet findById(Long qcmSetId) {
        ///  todo add some filters later !
        return qcmRepository.findById(qcmSetId).orElseThrow(() -> new RuntimeException("Problem with the qcm Id please check the config backend"));
    }
}





