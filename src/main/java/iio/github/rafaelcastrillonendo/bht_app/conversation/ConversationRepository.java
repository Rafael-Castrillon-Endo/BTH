package iio.github.rafaelcastrillonendo.bht_app.conversation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("""
            SELECT c FROM Conversation c
            JOIN ConversationMember cm ON cm.conversation = c
            WHERE cm.user.id = :userId
            """)
    List<Conversation> findAllByUserId(@Param("userId") Long userId);

}
