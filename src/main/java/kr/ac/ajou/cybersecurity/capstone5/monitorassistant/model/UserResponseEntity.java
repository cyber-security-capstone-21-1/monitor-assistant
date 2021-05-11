package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.model;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.UserEntity;
import lombok.Builder;

@Builder
public class UserResponseEntity extends CustomResponseEntity<UserEntity>{
    private Long id;
    private String email;

}
