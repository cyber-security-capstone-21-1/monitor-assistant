package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntelligenceRepository extends JpaRepository<IntelligenceEntity, Long> {
    Optional<IntelligenceEntity> findByUid(@Param("uid") Long uid);
}
