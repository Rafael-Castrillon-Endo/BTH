package iio.github.rafaelcastrillonendo.bht_app.message.dto;

import java.time.Instant;

public record MessageSummary(
        Long senderId,
        Long messageId,
        String username,
        Instant sentAt,
        String content
){
}
