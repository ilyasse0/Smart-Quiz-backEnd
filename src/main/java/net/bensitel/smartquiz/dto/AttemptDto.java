package net.bensitel.smartquiz.dto;

import lombok.*;
import net.bensitel.smartquiz.entity.Attempt;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttemptDto {
    private Long id;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private int score;
    private Long qcmSetId;



    public static AttemptDto fromEntity(Attempt attempt) {
        return new AttemptDto(
                attempt.getId(),
                attempt.getStartedAt(),
                attempt.getCompletedAt(),
                attempt.getScore(),
                attempt.getQcmSet().getId()
        );
    }
}
