package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import lombok.Builder;

import java.util.List;

public class PostResponse extends ApiResponse<List<PostEntity>>{

    @Builder
    public PostResponse(final List<PostEntity> postEntities, final List<String> errors) {
        super(postEntities);
        this.setErrors(errors);
    }
    @Builder
    public PostResponse(final List<PostEntity> postEntities, final String status, final List<String> errors) {
        super(postEntities);
        this.setStatus(status);
        this.setErrors(errors);
    }
}
