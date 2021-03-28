package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.PostResponse;

import java.util.List;

public class PostAdapter {
    public static PostResponse postResponse(final List<PostEntity> postEntities, final List<String> errors) {
        return PostResponse.builder().postEntities(postEntities).errors(errors).build();
    }
}
