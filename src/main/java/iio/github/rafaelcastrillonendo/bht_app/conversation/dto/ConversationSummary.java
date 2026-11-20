package iio.github.rafaelcastrillonendo.bht_app.conversation.dto;

import java.time.Instant;

public record ConversationSummary(
        Long id,
        boolean isGroup,
        String groupName,
        String otherMemberUsername, // not null when isGroup = FALSE
        String lastMessagePreview,
        Instant lastMessageAt,
        String lastMessageSenderUsername
) {
    public ConversationSummary {
        if(!isGroup && otherMemberUsername == null){
            throw new IllegalArgumentException("Other member username must not be null when isGroup = false");
        }
    }
}