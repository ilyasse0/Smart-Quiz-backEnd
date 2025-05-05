package net.bensitel.smartquiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// Attempt.java
@Entity
@Table(name = "attempts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "qcm_set_id")
    private QcmSet qcmSet;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private int score;

    // getters and setters
}