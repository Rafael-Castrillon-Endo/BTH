package iio.github.rafaelcastrillonendo.bht_app.conversation;

import iio.github.rafaelcastrillonendo.bht_app.conversation.dto.ConversationDefSummary;
import iio.github.rafaelcastrillonendo.bht_app.conversation.dto.CreateGroupRequest;
import iio.github.rafaelcastrillonendo.bht_app.conversationM.ConversationMember;
import iio.github.rafaelcastrillonendo.bht_app.conversationM.ConversationMemberRepository;
import iio.github.rafaelcastrillonendo.bht_app.exception.BadRequestException;
import iio.github.rafaelcastrillonendo.bht_app.exception.NotFoundException;
import iio.github.rafaelcastrillonendo.bht_app.invitation.Invitation;
import iio.github.rafaelcastrillonendo.bht_app.invitation.InvitationRepository;
import iio.github.rafaelcastrillonendo.bht_app.user.User;
import iio.github.rafaelcastrillonendo.bht_app.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConversationServiceImpl implements ConversationService{
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final ConversationMemberRepository conversationMemberRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository,  UserRepository userRepository,
                                   InvitationRepository invitationRepository,  ConversationMemberRepository conversationMemberRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.conversationMemberRepository = conversationMemberRepository;
    }

    @Override
    @Transactional
    public ConversationDefSummary createGroup(Long currentUserId, CreateGroupRequest createGroupRequest) {
        User currentUser = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new NotFoundException("User " +  currentUserId + " not found"));
        Set<Long> membersId = new HashSet<>(createGroupRequest.memberIds());
        membersId.remove(currentUser.getId()); // It is not necessary that membersId contains currentUser id
        if(membersId.isEmpty()){
            throw new BadRequestException("There must be at least one other member");
        }
        long countMembers = userRepository.countByIdIn(membersId);
        if(membersId.size() != countMembers){ // count members could never be greater than memberIds.size()
            throw new BadRequestException(membersId.size() - countMembers + " members not found");
        }
        Conversation newConversation = conversationRepository.save(new Conversation(
                createGroupRequest.name(),
                true,
                currentUser
        ));
        // currentUser will always be the first admin
        conversationMemberRepository.save(new ConversationMember(newConversation, currentUser));
        List<Invitation> invitations = membersId.stream()
                //Default invitation status = PENDING
                .map(idUser -> new Invitation(
                        newConversation,
                        currentUser,
                        userRepository.getReferenceById(idUser)
                ))
                .toList();
        invitationRepository.saveAll(invitations);
        return new ConversationDefSummary(
                newConversation.getId(),
                true,
                newConversation.getName()
        );
    }
}
