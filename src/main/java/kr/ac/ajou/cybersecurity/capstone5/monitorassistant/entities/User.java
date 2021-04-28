package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "password_encrypoted", length = 500)
    private String password_encrypted;

    @Column(name = "email", length = 500, unique = true)
    private String email;

    @Column(name = "role")
    private int role;

    @OneToMany
    @JoinColumn(name = "id")
    private Set<IntelligenceEntity> intelligences;
}
