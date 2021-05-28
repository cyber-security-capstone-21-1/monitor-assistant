package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 그냥 게시물 scrapping하기 위한 entity
// ****list 형식으로 Bean에 등록하는 방법 찾아보기*****


public class PostEntity {
    private String site;
    private String title;
    private String view;
    private LocalDateTime created_at;
    private String author;
    private String url;
    private String type;
}
