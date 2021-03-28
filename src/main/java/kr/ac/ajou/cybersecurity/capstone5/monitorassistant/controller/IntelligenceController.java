package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.IntelligenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/intelligences")
@AllArgsConstructor
public class IntelligenceController {

    private IntelligenceRepository intelligenceRepository;
// hihi
    @GetMapping("/")
    public List<IntelligenceEntity> all() {
        return intelligenceRepository.findAll();
    }
    
}
