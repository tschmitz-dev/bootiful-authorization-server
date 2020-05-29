package de.tschmitz.web.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * User entity.
 */
@Entity
@Data
@NoArgsConstructor
public class User {

    public enum Role {
        USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    @ToString.Exclude
    private String password;

    @CreationTimestamp
    private LocalDateTime added;

    @Column(columnDefinition = "varchar(255) not null default 'USER'")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
