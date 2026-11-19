package iio.github.rafaelcastrillonendo.bht_app.invitation.dto;

import java.time.Instant;

public record InvitationSummary(
        Long id,
        Long conversationId,
        String groupName,
        String inviterUsername,
        Instant createdAt
) {
}
