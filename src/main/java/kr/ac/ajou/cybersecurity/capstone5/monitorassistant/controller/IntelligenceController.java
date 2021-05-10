package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.IntelligenceAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.IntelligenceRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.IntelligenceResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "intelligences", description = "첩보 API")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class IntelligenceController {

    @Autowired
    private IntelligenceRepository intelligenceRepository;

    @GetMapping("/intelligences")
    @Transactional
    public List<IntelligenceEntity> all() {
        // 전체 User에 대한 List 반환 중 --> 향후 JWT Token 사용해 User에 따른 List 반환
        return intelligenceRepository.findAll();
    }

    @PostMapping("/intelligences")
    public IntelligenceResponse save(@RequestBody IntelligenceEntity entity) {
//        intelligenceRepository.save(entity);
        System.out.println(entity.getContent());

        return IntelligenceAdapter.intelligenceResponse(entity, null);
    }

    @GetMapping("/intelligences/{uid}")
    @Transactional
    public IntelligenceEntity findOne(@PathVariable String iid) {
        return intelligenceRepository.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("No data"));
    }

}
