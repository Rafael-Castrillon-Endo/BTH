package iio.github.rafaelcastrillonendo.bht_app.message.dto;

import java.util.List;

public record MessagePage(
        List<MessageSummary> messages,
        boolean hasNext,
        Long nextCursor
) {
    public MessagePage {
        messages = List.copyOf(messages);
        if(hasNext && nextCursor == null){
            throw new IllegalArgumentException("nextCursor required when hasNext is true");
        }
    }
}
