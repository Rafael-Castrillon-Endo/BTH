package iio.github.rafaelcastrillonendo.bht_app.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "nickname", length = 50, nullable = false, unique = true)
    private String nickname;

    @Column(name = "password_hash", length = 100, nullable = false)
    private String passwordHash;

    @Column(name = "last_seen")
    private Instant lastSeen;

    @CreationTimestamp 
    @Column(name = "created_at")
    private Instant createdAt;

    public User() {
    }

    public User(String username, String nickname, String passwordHash) {
        this.username = username;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword_hash() {
        return passwordHash;
    }

    public Instant getLast_seen() {
        return lastSeen;
    }

    public Instant getCreated_at() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword_hash(String password_hash) {
        this.passwordHash = password_hash;
    }

    public void setLast_seen(Instant last_seen) {
        this.lastSeen = last_seen;
    }

    public void setCreated_at(Instant created_at) {
        this.createdAt = created_at;
    }

}
