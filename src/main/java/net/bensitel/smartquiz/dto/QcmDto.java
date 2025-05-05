package net.bensitel.smartquiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QcmDto {
    private String question;
    private List<String> options;
    private String correctAnswer;
}
