package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.TokenEntity;
import lombok.Builder;

import java.util.List;

public class TokenResponse extends ApiResponse<TokenEntity>{

    @Builder
    public TokenResponse(final TokenEntity tokenEntity) {
        super(tokenEntity);
    }
}
