package iio.github.rafaelcastrillonendo.bht_app.conversation.dto;

import java.time.Instant;

public record ConversationSummary(
        Long id,
        boolean isGroup,
        String groupName,
        String otherMemberUsername,
        String lastMessagePreview,
        Instant lastMessageAt,
        String lastMessageSenderUsername
) {}