package iio.github.rafaelcastrillonendo.bht_app.invitation;

import iio.github.rafaelcastrillonendo.bht_app.enums.InvitationStatus;
import iio.github.rafaelcastrillonendo.bht_app.invitation.dto.InvitationSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    @Query("""
        SELECT  new iio.github.rafaelcastrillonendo.bht_app.invitation.dto.InvitationSummary(
            i.id, c.id, c.name, inv.username, i.createdAt
            )
        FROM Invitation i
        JOIN Conversation c ON c.id = i.conversation.id
        JOIN User inv ON inv.id = i.inviterUser.id
        WHERE i.invitedUser.id = :userId AND i.status = :status
        ORDER BY i.createdAt DESC
    """)
    List<InvitationSummary> findReceivedByStatus(@Param("userId") Long userId, @Param("status") InvitationStatus status);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Invitation i
        SET i.status = :status, i.respondedAt = CURRENT_TIMESTAMP
        WHERE i.id = :invitationId AND i.invitedUser.id = :invitedUserId AND i.status = 'PENDING'
    """)
    int respond(@Param("invitationId") Long invitationId, @Param("invitedUserId") Long invitedUserId, @Param("status") InvitationStatus status);

    @Modifying(clearAutomatically = true)
    @Query("""
        DELETE FROM Invitation i
        WHERE i.status = 'PENDING' AND i.id = :invitationId AND i.inviterUser = :inviterId
    """)
    int cancelPending(@Param("invitationId") Long invitationId, @Param("inviterId") Long inviterId);

    @Modifying(clearAutomatically = true)
    @Query("""
        DELETE FROM Invitation i
        WHERE i.conversation.id = :conversationId
    """)
    int deleteAllByConversationId(@Param("conversationId") Long conversationId);




}
