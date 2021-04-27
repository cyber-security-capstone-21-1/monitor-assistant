package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.IntelligenceAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.IntelligenceRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.IntelligenceResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intelligences")
@AllArgsConstructor
public class IntelligenceController {

// DI 주입
     @Autowired
    private IntelligenceRepository intelligenceRepository;
    @PostMapping("/save")
    public IntelligenceResponse save(@RequestBody IntelligenceEntity entity)
    {
        intelligenceRepository.save(entity);

        return IntelligenceAdapter.intelligenceResponse(entity, null);
    }

    @GetMapping("/")
    public List<IntelligenceEntity> all() {
        return intelligenceRepository.findAll();
    }
    
}
