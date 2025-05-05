package net.bensitel.smartquiz.entity;

import jakarta.persistence.*;
import lombok.*;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.entity.enums.DocumentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String filename; // stored filename/path

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status; // e.g., PROCESSED, PENDING

    // getters and setters
}