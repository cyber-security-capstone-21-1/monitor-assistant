package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.model.UserResponseEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.IntelligenceResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.UserResponse;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public class UserAdapter {

    public static UserResponseEntity userResponseEntity(final Long id, final String email) {
        return UserResponseEntity.builder()
            .email(email)
            .id(id)
            .build();
    }

    public static UserResponse userResponse(final UserResponseEntity userResponseEntity, final String status, final List<String> errors) {
        return UserResponse.builder()
                .data(userResponseEntity)
                .status(status)
                .errors(errors)
                .build();
    }
}
