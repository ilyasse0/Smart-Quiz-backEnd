package net.bensitel.smartquiz.mapper;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.DocumentDto;
import net.bensitel.smartquiz.entity.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentMapper {

    private final UserMapper userMapper;

    public DocumentDto toDto(Document document) {
        if (document == null) return null;

        return DocumentDto.builder()
                .id(document.getId())
                .title(document.getTitle())
                .filename(document.getFilename())
                .uploadedAt(document.getUploadedAt())
                .status(document.getStatus())
                .uploadedBy(userMapper.toDto(document.getUploadedBy()))
                .build();
    }





}
