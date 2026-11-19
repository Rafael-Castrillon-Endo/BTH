package iio.github.rafaelcastrillonendo.bht_app.message;

import iio.github.rafaelcastrillonendo.bht_app.message.dto.MessageSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


public interface MessageRepository extends JpaRepository<Message, Long> {


    @Query("""
        SELECT new iio.github.rafaelcastrillonendo.bht_app.message.dto.MessageSummary(
            u.id, m.id, u.username, m.sentAt, m.content
            ) FROM Message  m
        LEFT JOIN User u ON u.id = m.user.id
        WHERE m.conversation.id = :conversationId
        ORDER BY m.id DESC
        LIMIT :Limit
    """)
    List<MessageSummary> fetchNewerMessages(@Param("conversationId") Long conversationId, Long Limit);

    @Query("""
        SELECT new iio.github.rafaelcastrillonendo.bht_app.message.dto.MessageSummary(
            u.id, m.id, u.username, m.sentAt, m.content
            ) FROM Message  m
        LEFT JOIN User u ON u.id = m.user.id
        WHERE m.conversation.id = :conversationId AND m.id < :lastId
        ORDER BY m.id DESC
        LIMIT :Limit
            
    """)
    List<MessageSummary> fetchOlderMessages(@Param("lastId") Long beforeId, @Param("conversationId") Long conversationId, Long Limit);

    @Modifying(clearAutomatically = true)
    @Query("""
        DELETE FROM Message m
        WHERE m.id = :messageId AND m.user.id = :userId AND m.conversation.id = :conversationId
    """)
    int deleteMessage(@Param("userId") Long userId, @Param("conversationId") Long conversationId,
                                                        @Param("messageId") Long messageId);

    @Query("""
        SELECT m FROM Message  m
        WHERE m.conversation.id = :conversationId AND m.id <> :excludedId
        ORDER BY m.id DESC
        LIMIT 1
    """)
    Optional<Message> findLastMessageExcluding(@Param("conversationId") Long conversationId, @Param("excludedId") Long excludedId);



}
