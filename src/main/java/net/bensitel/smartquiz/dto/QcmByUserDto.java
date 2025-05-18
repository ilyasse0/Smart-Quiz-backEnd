package net.bensitel.smartquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bensitel.smartquiz.entity.QcmSet;
import net.bensitel.smartquiz.entity.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QcmByUserDto {
    private Long id;
    private String title;
    private String topic;
    private LocalDateTime createdAt;
    private Boolean isPublic;
    private String createdByUsername;



    public static QcmByUserDto fromEntity(QcmSet qcm) {
        User creator = qcm.getCreatedBy();
        return new QcmByUserDto(
                qcm.getId(),
                qcm.getTitle(),
                qcm.getTopic(),
                qcm.getCreatedAt(),
                qcm.isPublic(),
                creator.getUsername()
        );
    }
}
