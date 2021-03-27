package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class PostEntity {
    private String site;
    private String title;
    private String created_at;
    private String author;
    private String content;
    private String url;
    private String type;
}
