package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import lombok.Builder;

import java.util.List;

public class IntelligenceResponse extends ApiResponse<IntelligenceEntity> {

    @Builder
    public IntelligenceResponse(final IntelligenceEntity intelligenceEntity, final List<String> errors) {
        super(intelligenceEntity);
        this.setErrors(errors);
    }
}
