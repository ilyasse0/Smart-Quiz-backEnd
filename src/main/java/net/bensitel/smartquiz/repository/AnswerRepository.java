package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
