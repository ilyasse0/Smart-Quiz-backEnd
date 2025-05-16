package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.AttemptRequest;
import net.bensitel.smartquiz.entity.*;
import net.bensitel.smartquiz.repository.AnswerRepository;
import net.bensitel.smartquiz.repository.AttemptRepository;
import net.bensitel.smartquiz.repository.QcmRepository;
import net.bensitel.smartquiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttemptService {
    private final QcmRepository qcmSetRepository;
    private final QuestionRepository questionRepository;
    private final AttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;

    public int processAttempt(User user, AttemptRequest request) {
        QcmSet qcmSet = qcmSetRepository.findById(request.getQcmSetId())
                .orElseThrow(() -> new RuntimeException("QCM not found"));

        int score = 0;

        Attempt attempt = Attempt.builder()
                .user(user)
                .qcmSet(qcmSet)
                .startedAt(LocalDateTime.now())
                .completedAt(LocalDateTime.now())
                .build();

        attempt = attemptRepository.save(attempt);

        for (AttemptRequest.AnswerSubmission submission : request.getAnswers()) {
            Question question = questionRepository.findById(submission.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            boolean correct = submission.getSelectedOprion() == question.getCorrectOption();
            if (correct) score++;

            Answer answer = Answer.builder()
                    .attempt(attempt)
                    .question(question)
                    .selectedOption(submission.getSelectedOprion())
                    .isCorrect(correct)
                    .build();

            answerRepository.save(answer);
        }

        attempt.setScore(score);
        attemptRepository.save(attempt);

        return score;
    }
}

