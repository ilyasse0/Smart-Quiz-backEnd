package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.DocumentDto;
import net.bensitel.smartquiz.entity.Document;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.entity.enums.DocumentStatus;
import net.bensitel.smartquiz.mapper.DocumentMapper;
import net.bensitel.smartquiz.repository.DocumentqRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentqRepository documentRepository;
    private final DocumentMapper documentMapper;


    public Document storeDocument(MultipartFile file, String title, User uploadedBy) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filepath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filepath);

        Document doc = Document.builder()
                .title(title)
                .filename(filename)
                .uploadedBy(uploadedBy)
                .uploadedAt(LocalDateTime.now())
                .status(DocumentStatus.PENDING)
                .build();

        documentRepository.save(doc);

        return doc;
    }
}
