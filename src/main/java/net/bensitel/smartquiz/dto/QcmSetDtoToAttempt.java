package net.bensitel.smartquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QcmSetDtoToAttempt  {
    private Long qcmSetId;
    private String title;
    private String topic;
    private List<QuestionDtoToAttempt> questions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionDtoToAttempt {
        private Long questionId;
        private String content;
        private List<OptionDto> options;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OptionDto {
        private Long optionId;
        private char optionLabel;
        private String text;
    }
}
