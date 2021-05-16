package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.config.JwtTokenUtil;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.IntelligenceRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.UserRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.BasicResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.CommonResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Tag(name = "intelligences", description = "첩보 API")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class IntelligenceController {

    @Autowired
    private IntelligenceRepository intelligenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/intelligences")
    @Transactional
    public ResponseEntity<? extends BasicResponse> all() {
        return ResponseEntity.ok()
                .body(new CommonResponse<List<IntelligenceEntity>>(intelligenceRepository.findAll(), "ok"));
    }

    @PostMapping("/intelligences")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody IntelligenceEntity entity, HttpServletRequest req) {
        String str = req.getHeader("Authorization");
        if (str.startsWith("Bearer ")) {
            str = str.substring(7);
        }
        String email = jwtTokenUtil.getUsernameFromToken(str);
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            entity.setUserEntity(user.get());
            try {
                Object savedEntity = intelligenceRepository.saveAndFlush(entity);
                return ResponseEntity.ok()
                        .body(new CommonResponse<Object>(savedEntity, "ok"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ErrorResponse(e.getMessage(), 500));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("존재하지 않는 사용자입니다.", 404));
        }
    }

    @GetMapping("/intelligences/{uid}")
    @Transactional
    public ResponseEntity<? extends BasicResponse> findOne(@PathVariable String uid) {
        Optional<IntelligenceEntity> intelligenceEntity = intelligenceRepository.findByUid(uid);
        if (intelligenceEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponse("No Data exists", 404));
        }
        return ResponseEntity.ok()
                .body(new CommonResponse<Optional<IntelligenceEntity>>(intelligenceEntity, "ok"));
    }

    @DeleteMapping("/intelligences/{id}")
    @Transactional
    public ResponseEntity<? extends BasicResponse> deleteOne(@PathVariable Long id) {
        if (intelligenceRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponse("No Data exists", 404));
        else {
            intelligenceRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(new CommonResponse<String>("", "Deleted"));
        }
    }
}
