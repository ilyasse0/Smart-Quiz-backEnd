package net.bensitel.smartquiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qcm_sets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QcmSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String topic;

    @Column(nullable = true)
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document  document;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt;


}
