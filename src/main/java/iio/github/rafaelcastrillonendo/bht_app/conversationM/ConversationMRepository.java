package iio.github.rafaelcastrillonendo.bht_app.conversationM;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import iio.github.rafaelcastrillonendo.bht_app.conversationM.dto.MemberSummary;

public interface ConversationMRepository extends JpaRepository<ConversationMember, Long> {

    boolean existsByUserIdAndConversationId(Long userId, Long conversationId);

    @Query("""
            SELECT COUNT(cm) FROM ConversationMember cm
            WHERE cm.conversation.id = :groupId
            """)
    Long countByGroupId(@Param("groupId") Long groupId);

    @Query("""
                SELECT new iio.github.rafaelcastrillonendo.bht_app.conversationM.dto.MemberSummary(u.id, u.username)
                FROM User u
                JOIN ConversationMember cm ON cm.user = u AND cm.conversation.id = :groupId
                ORDER BY u.username ASC
            """)
    List<MemberSummary> membersOfAConversation(@Param("groupId") Long groupId);

    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);

    @Modifying(clearAutomatically = true)
    @Query("""
                DELETE FROM ConversationMember cm
                WHERE cm.user.id = :userId AND cm.conversation.id = :conversationId AND
                cm.conversation.id = (SELECT c.id FROM Conversation c WHERE c.id = :conversationId AND c.admin.id = :adminId)
        """)
    int removeMemberFromAConversation(@Param("conversationId") Long conversationId,
                                        @Param("userId") Long userId , @Param("adminId") Long adminId);






}