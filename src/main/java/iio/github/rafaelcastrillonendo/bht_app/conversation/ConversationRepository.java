package iio.github.rafaelcastrillonendo.bht_app.conversation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("""
            SELECT c FROM Conversation c
            JOIN ConversationMember cm ON cm.conversation = c
            LEFT JOIN Message ms ON ms = c.lastMessage
            WHERE cm.user.id = :userId
            ORDER BY ms.createdAt DESC
            NULLS LAST
            """)
    List<Conversation> findAllByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT c FROM Conversation c
            JOIN ConversationMember cm ON cm.conversation = c
            LEFT JOIN Message ms ON ms = c.lastMessage
            WHERE cm.user.id = :userId AND
            LOWER(c.name) LIKE LOWER(CONCAT('%', :conversationName, '%'))
            ORDER BY ms.createdAt DESC
            NULLS LAST
            """)
    List<Conversation> findConversationsByName(
            @Param("conversationName") String conversationName,
            @Param("userId") Long userId);

}
