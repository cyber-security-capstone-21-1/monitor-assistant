package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//자동으로 bean 등록
public interface IntelligenceRepository extends JpaRepository<IntelligenceEntity, Long> {

    //여기 안에 아무것도 안 넣어도 CRUD 가능
}
