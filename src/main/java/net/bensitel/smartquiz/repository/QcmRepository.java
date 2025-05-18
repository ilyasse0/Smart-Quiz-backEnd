package net.bensitel.smartquiz.repository;

import net.bensitel.smartquiz.entity.QcmSet;
import net.bensitel.smartquiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QcmRepository extends JpaRepository<QcmSet , Long> {
        List<QcmSet> findByCreatedBy(User user);
        List<QcmSet> findByIsPublicTrue();


}

