package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IntelligenceRepository extends JpaRepository<IntelligenceEntity, Long> {
    Optional<IntelligenceEntity> findByUid(@Param("uid") String uid);
//    @Query("select log from Log log where log.user_id = ?1")
    List<IntelligenceEntity> findByuserEntity(@Param("user_id") UserEntity userEntity);
}
