package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.UserAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.config.JwtTokenUtil;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.UserRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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
    public UserResponse SignUp(@RequestBody Map<String, String> user) {
        Long id;
        List<String> errors = new ArrayList<>();
        try {
            id = userRepository.save(
                    UserEntity.builder()
                            .email(user.get("email"))
                            .password_encrypted(passwordEncoder.encode(user.get("password")))
                            .role(UserEntity.Role.USER.ordinal())
                            .name(user.get("name"))
                            .build()
            ).getId();

        } catch (Exception e) {
            errors.add(e.getMessage());
            return UserAdapter.userResponse(
                    UserAdapter.userResponseEntity(null, null), "failed", errors);
        }
        return UserAdapter.userResponse(
                UserAdapter.userResponseEntity(id, user.get("email")), "created", errors);
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

    @PostMapping("/emailvalidity")
    public Boolean emailValidity(@RequestBody Map<String, String> user) {
        if (userRepository.findByEmail(user.get("email")).isPresent()) {
            return true;
        } else return false;
    }
}
