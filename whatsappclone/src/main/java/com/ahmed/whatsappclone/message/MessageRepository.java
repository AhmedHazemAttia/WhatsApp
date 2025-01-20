package com.ahmed.whatsappclone.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

import static com.ahmed.whatsappclone.message.MessageConstants.FIND_MESSAGE_BY_CHAT_ID;
import static com.ahmed.whatsappclone.message.MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(name = FIND_MESSAGE_BY_CHAT_ID)
    List<Message> findMessageByChatId(String chatId);

    @Query(name = SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChatId(@Param("chatId") String chatId,@Param("newState") MessageState state);
}
