package net.bensitel.smartquiz.mapper;

import net.bensitel.smartquiz.dto.AttemptDto;
import net.bensitel.smartquiz.entity.Attempt;

public class AttemptMapper {

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
