package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.IntelligenceAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.IntelligenceRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.IntelligenceResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return intelligenceRepository.findAll();
    }

    @PostMapping("/intelligences")
    public IntelligenceResponse save(@RequestBody IntelligenceEntity entity) {
        System.out.println(entity);
        intelligenceRepository.save(entity);
        System.out.println(entity.getContent());
        return IntelligenceAdapter.intelligenceResponse(entity, "save success",null);
    }

    @GetMapping("/intelligences/{uid}")
    @Transactional
    public IntelligenceEntity findOne(@PathVariable String uid) {
        return intelligenceRepository.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("No data"));
    }

    @DeleteMapping("/intelligences/{uid}")
    @Transactional
    public String deleteOne(@PathVariable String uid) {
        if(intelligenceRepository.findByUid(uid).isEmpty())
            return uid+": no data";
        else {
            intelligenceRepository.deleteByUid(uid);
            return uid+": delete success";
        }
    }
}
