package net.bensitel.smartquiz.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private char optionLabel; // A, B, C, D

    @Lob
    private String text;

    // getters and setters
}