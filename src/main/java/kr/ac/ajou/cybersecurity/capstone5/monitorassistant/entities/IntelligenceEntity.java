package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Data
@ToString(exclude="userEntity")
@Entity
@Table(name = "intelligence")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntelligenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "url", length = 500)
    private String url;

    @Column(name = "site", length = 500)
    private String site;

    @Column(name = "author", length = 500)
    private String author;

    @Column(name = "created_at", length = 500)
    private LocalDateTime created_at;

    @Column(name = "action_plan", length = 500)
    private String action_plan;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "search_keyword", length = 500)
    private String search_keyword;

    @Column(name = "intelligence_title", length = 500)
    private String intelligence_title;

    @Column(name = "crime_type", length = 500)
    private String crime_type;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @ManyToOne(targetEntity = UserEntity.class)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
