package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.model.CustomResponseEntity;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class UserResponse extends ApiResponse<CustomResponseEntity> {
    @NonNull
    @Builder
    public UserResponse(final CustomResponseEntity data, final String status, final List<String> errors) {
        super(data);
        this.setStatus(status);
        this.setErrors(errors);
    }
}
