package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.entity.QcmSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QcmRepository extends JpaRepository<QcmSet , Long> {
}
