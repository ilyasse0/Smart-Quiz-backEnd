package net.bensitel.smartquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttemptRequest {
    private Long qcmSetId;
    private List<AnswerSubmission> answers;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerSubmission {
        private Long questionId;
        private char selectedOprion;
    }
}
