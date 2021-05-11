package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.TokenEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.TokenResponse;

import java.util.List;

public class TokenAdapter {
    public static TokenResponse tokenResponse(final TokenEntity tokenEntity) {
        return TokenResponse.builder().tokenEntity(tokenEntity).build();
    }
}
