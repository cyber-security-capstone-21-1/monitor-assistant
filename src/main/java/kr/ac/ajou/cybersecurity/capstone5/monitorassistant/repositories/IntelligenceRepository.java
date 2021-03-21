package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntelligenceRepository extends JpaRepository<IntelligenceEntity, Long> {
}
