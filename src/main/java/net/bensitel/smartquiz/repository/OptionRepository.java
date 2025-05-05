package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.dto.QcmSetDtoToAttempt;
import net.bensitel.smartquiz.entity.Option;
import net.bensitel.smartquiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findByQuestionId(Long questionId);

}