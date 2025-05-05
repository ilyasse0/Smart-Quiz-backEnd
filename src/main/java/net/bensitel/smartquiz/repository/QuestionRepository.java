package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQcmSet_Id(Long qcmSetId);
}
