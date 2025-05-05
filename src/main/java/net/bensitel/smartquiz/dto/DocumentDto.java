package net.bensitel.smartquiz.dto;

import lombok.*;
import net.bensitel.smartquiz.entity.enums.DocumentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDto {
    private Long id;
    private String title;
    private String filename;
    private LocalDateTime uploadedAt;
    private DocumentStatus status;
    private UploaderByDto uploadedBy;
}
