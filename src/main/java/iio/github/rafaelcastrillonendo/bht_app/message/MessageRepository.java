package iio.github.rafaelcastrillonendo.bht_app.message;

import iio.github.rafaelcastrillonendo.bht_app.message.dto.MessageSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    List<MessageSummary> fetchNewerMessages(@Param("conversationId") Long conversationId);

    @Query("""
        SELECT new iio.github.rafaelcastrillonendo.bht_app.message.dto.MessageSummary(
            u.id, m.id, u.username, m.sentAt, m.content
            ) FROM Message  m
        LEFT JOIN User u ON u.id = m.user.id
        WHERE m.conversation.id = :conversationId AND m.id < :lastId
        ORDER BY m.id DESC
    """)
    List<MessageSummary> fetchOlderMessages(@Param("lastId") Long lastId, @Param("conversationId") Long conversationId, Pageable pageable);


}
