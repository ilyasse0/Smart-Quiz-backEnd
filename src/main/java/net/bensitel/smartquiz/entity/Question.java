package net.bensitel.smartquiz.entity;


import jakarta.persistence.*;
import lombok.*;
import net.bensitel.smartquiz.entity.enums.Difficulty;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "qcm_set_id")
    private QcmSet qcmSet;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private char correctOption;

    // getters and setters
}