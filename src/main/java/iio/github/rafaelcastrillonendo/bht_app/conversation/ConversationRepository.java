package iio.github.rafaelcastrillonendo.bht_app.conversation;

import java.util.List;
import java.util.Optional;

import iio.github.rafaelcastrillonendo.bht_app.conversation.dto.ConversationSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

        @Query("""
                        SELECT new iio.github.rafaelcastrillonendo.bht_app.conversation.dto.ConversationSummary(
                        c.id, c.isGroup, c.name, ou.username, ms.content, ms.sentAt, su.username                       
                        ) FROM Conversation  c
                        JOIN ConversationMember cm ON cm.conversation = c AND cm.user.id = :userId
                        LEFT JOIN ConversationMember om ON om.conversation = c AND om.user.id != :userId AND c.isGroup = FALSE
                        LEFT JOIN User ou ON ou.id = om.user.id
                        LEFT JOIN Message ms ON ms = c.lastMessage
                        LEFT JOIN User su ON su = ms.user
                        ORDER BY ms.sentAt DESC
                        NULLS LAST 
                        """)
        List<ConversationSummary> findAllByUserId(@Param("userId") Long userId);

        @Query("""
                        SELECT c FROM Conversation c
                        JOIN ConversationMember cm ON cm.conversation = c
                        LEFT JOIN Message ms ON ms = c.lastMessage
                        WHERE cm.user.id = :userId AND
                        LOWER(c.name) LIKE LOWER(CONCAT('%', :conversationName, '%'))
                        ORDER BY ms.sentAt DESC
                        NULLS LAST
                        """)
        List<Conversation> findConversationsByName(@Param("conversationName") String conversationName,
                        @Param("userId") Long userId);

        @Query("""
                        SELECT c FROM Conversation c
                        JOIN ConversationMember cm ON cm.conversation = c
                        WHERE cm.user.id = :userId AND c.id = :conversationId
                        """)
        Optional<Conversation> findConversationByIdUser(@Param("userId") Long userId,
                        @Param("conversationId") Long conversationId);

        @Query("""
                        SELECT c FROM Conversation c
                        JOIN ConversationMember cm ON cm.conversation = c AND cm.user.id = :firstUserId
                        WHERE c.isGroup = FALSE AND
                        c.id IN (SELECT csm.conversation.id FROM ConversationMember  csm WHERE csm.user.id = :secondUserId)
                        """)
        Optional<Conversation> findPrivateConversation(@Param("firstUserId") Long firstUserId,
                        @Param("secondUserId") Long secondUserId);

}
