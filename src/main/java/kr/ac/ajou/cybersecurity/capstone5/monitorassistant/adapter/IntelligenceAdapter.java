package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.IntelligenceEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.IntelligenceResponse;

import java.util.List;

public class IntelligenceAdapter {

    public static IntelligenceResponse intelligenceResponse(final IntelligenceEntity intelligenceEntity,final String status, final List<String> errors) {
        return IntelligenceResponse.builder()
                .status(status)
                .intelligenceEntity(intelligenceEntity)
                .errors(errors)
                .build();
    }
//    public static IntelligenceResponse intelligenceErrorResponse(final IntelligenceEntity intelligenceEntity, final List<String> errors) {
//        return IntelligenceResponse.builder()
//                .intelligenceEntity(intelligenceEntity)
//                .errors(errors)
//                .build();
//    }
}
