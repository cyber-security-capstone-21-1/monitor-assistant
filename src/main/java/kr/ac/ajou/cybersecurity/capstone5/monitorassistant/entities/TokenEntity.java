package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {
    private String AccessToken;
    private String RefreshToken;
}
