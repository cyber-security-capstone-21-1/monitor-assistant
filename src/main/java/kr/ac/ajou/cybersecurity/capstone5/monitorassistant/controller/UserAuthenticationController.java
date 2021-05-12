package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.TokenAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.UserAdapter;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.config.JwtTokenUtil;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.TokenEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.model.JwtRequest;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.repositories.UserRepository;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.TokenResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.JwtUserDetailsService;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

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
        return UserAdapter.userResponse(UserAdapter.userResponseEntity(id, user.get("email")), "created", errors);
    }

    @PostMapping("/emailvalidity")
    public Boolean emailValidity(@RequestBody Map<String, String> user) {
        if (userRepository.findByEmail(user.get("email")).isPresent()) {
            return true;
        } else return false;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TokenResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String accessToken = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setAccessToken(accessToken);
        tokenEntity.setRefreshToken(refreshToken);

        return TokenAdapter.tokenResponse(tokenEntity);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    //페이지 이동시 validity check
    @PostMapping(value="/authenticate")
    public Boolean checkAccessToken(@RequestHeader(value="Authorization") String jwtToken) throws Exception {
        jwtToken = jwtToken.substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        if(email != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            Boolean isValid = jwtTokenUtil.validateToken(jwtToken, userDetails);
            return isValid;
        }
        return false;
    }

    @PostMapping(value="/refreshAccess")
    public TokenResponse refreshingAccess(HttpEntity<byte[]> requestEntity) {
        byte[] requestBody = requestEntity.getBody();
        String refresh = new String(requestBody);

        refresh = refresh.substring(0, refresh.length()-1);
        String email = jwtTokenUtil.getUsernameFromToken(refresh);
        if(email != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            Boolean isValid = jwtTokenUtil.validateToken(refresh, userDetails);
            System.out.println(isValid);
            if(isValid) {
                final String accessToken = jwtTokenUtil.generateToken(userDetails);
                TokenEntity tokenEntity = new TokenEntity();
                tokenEntity.setAccessToken(accessToken);

                return TokenAdapter.tokenResponse(tokenEntity);
            }
        }
        return null;
    }
}
