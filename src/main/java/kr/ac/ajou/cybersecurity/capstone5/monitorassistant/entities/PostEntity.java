package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 그냥 게시물 scrapping하기 위한 entity
public class PostEntity {
    private String site;
    private String title;
    private String view;
    private String created_at;
    private String author;
    private String content;
    private String url;
    private String type;
}
