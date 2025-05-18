package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.entity.Attempt;
import net.bensitel.smartquiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUserOrderByCompletedAtDesc(User user);
}
