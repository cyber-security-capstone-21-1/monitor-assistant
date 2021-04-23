package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapeEntity {
    private String site;
    private String title;
    private String created_at;
    private String author;
    private String content;
    private String url;
}
