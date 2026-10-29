package iio.github.rafaelcastrillonendo.bht_app.conversation;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import iio.github.rafaelcastrillonendo.bht_app.Message.Message;
import iio.github.rafaelcastrillonendo.bht_app.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "last_message_id")
    private Message lastMessage;

    public Conversation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public User getAdmin() {
        return admin;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

}
