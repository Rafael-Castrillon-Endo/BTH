package iio.github.rafaelcastrillonendo.bht_app.conversation;

import iio.github.rafaelcastrillonendo.bht_app.conversation.dto.ConversationDefSummary;
import iio.github.rafaelcastrillonendo.bht_app.conversation.dto.CreateGroupRequest;

public interface ConversationService {

    ConversationDefSummary createGroup(Long currentUserId, CreateGroupRequest  createGroupRequest);

}
