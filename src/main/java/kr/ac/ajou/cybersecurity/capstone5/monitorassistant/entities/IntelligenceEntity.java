package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "intelligence")
// 첩보물 (즉, 진짜 위험성 여부가 있다고 생각하는 게시글들을 DB에 저장하기 위한 Entity)
public class IntelligenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title",nullable = false, length = 500)
    private String title;

    @Column(name = "url", length = 500)
    private String url;

    @Column(name = "site", length = 500)
    private String site;

    @Column(name = "author", length = 500)
    private String author;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "created_at", length = 500)
    private LocalDateTime created_at;

    @Column(name = "action_plan", length = 500)
    private String action_plan;

    @Column(name = "archived_UID" ,nullable= false)
    private String archived_UID;

    //@lob --> 대용량 데이터 저장에 용이  content 할때 써도될듯

}
