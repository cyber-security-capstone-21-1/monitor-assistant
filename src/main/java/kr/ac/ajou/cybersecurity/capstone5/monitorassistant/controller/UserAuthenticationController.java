package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.config.JwtTokenUtil;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserAuthenticationController {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> SignUp(@RequestBody Map<String, String> user) {
        Long result;
        try {
            result = userRepository.save(
                    UserEntity.builder()
                            .email(user.get("email"))
                            .password_encrypted(passwordEncoder.encode(user.get("password")))
                            .role(UserEntity.Role.USER.ordinal())
                            .name(user.get("name"))
                            .build()
            ).getId();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        UserEntity member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenUtil.generateToken(member);
    }
}
