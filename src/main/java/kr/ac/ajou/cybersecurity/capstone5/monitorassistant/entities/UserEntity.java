package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    public enum Role { USER, ADMIN };

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "password_encrypted", length = 500)
    private String password_encrypted;

    @Column(name = "email", length = 500, unique = true)
    private String email;

    @Column(name = "role")
    private int role;

    @OneToMany
    @JoinColumn(name = "id")
    private Set<IntelligenceEntity> intelligences;

    // Overrides from UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password_encrypted;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
