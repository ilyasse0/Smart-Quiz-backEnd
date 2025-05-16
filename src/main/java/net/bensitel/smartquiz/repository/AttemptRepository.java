package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}
