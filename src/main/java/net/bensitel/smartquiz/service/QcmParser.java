package net.bensitel.smartquiz.service;

import net.bensitel.smartquiz.dto.GeneratedQcm;
import net.bensitel.smartquiz.dto.GeneratedQuestion;
import net.bensitel.smartquiz.entity.enums.Difficulty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QcmParser {

    public GeneratedQcm parse(String rawText , String title , String topic) {
        GeneratedQcm qcm = new GeneratedQcm();
        qcm.setTitle(title);
        qcm.setTopic(topic);

        List<GeneratedQuestion> questions = new ArrayList<>();

        String[] parts = rawText.split("(?=Question \\d+:)");
        for (String part : parts) {
            if (part.trim().isEmpty()) continue;

            GeneratedQuestion question = new GeneratedQuestion();
            Map<Character, String> options = new HashMap<>();

            // Question line
            Pattern questionPattern = Pattern.compile("Question \\d+: (.+)");
            Matcher matcher = questionPattern.matcher(part);
            if (matcher.find()) {
                question.setContent(matcher.group(1).trim());
            }

            // Options
            for (char opt : new char[]{'A', 'B', 'C', 'D'}) {
                Pattern optionPattern = Pattern.compile(opt + "\\) (.+)");
                matcher = optionPattern.matcher(part);
                if (matcher.find()) {
                    options.put(opt, matcher.group(1).trim());
                }
            }

            // Correct answer
            Pattern correctPattern = Pattern.compile("Correct answer: ([A-D])");
            matcher = correctPattern.matcher(part);
            if (matcher.find()) {
                question.setCorrectOption(matcher.group(1).charAt(0));
            }

            question.setOptions(options);
            question.setDifficulty(Difficulty.EASY); // ou auto-analyse plus tard
            questions.add(question);
        }

        qcm.setQuestions(questions);
        return qcm;
    }
}
