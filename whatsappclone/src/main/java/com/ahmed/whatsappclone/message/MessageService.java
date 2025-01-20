package com.ahmed.whatsappclone.message;


import com.ahmed.whatsappclone.chat.Chat;
import com.ahmed.whatsappclone.chat.ChatRepository;
import com.ahmed.whatsappclone.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ahmed.whatsappclone.message.MessageState.SEEN;
import static com.ahmed.whatsappclone.message.MessageState.SENT;
import static com.ahmed.whatsappclone.message.MessageType.IMAGE;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final FileService fileService;

    public void saveMessage(MessageRequest messageRequest){

        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat Not Found"));

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setType(messageRequest.getType());
        message.setState(SENT);

        messageRepository.save(message);
    }

    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessageByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat Not Found"));

        final String recipientId = getRecipientId(chat, authentication);
        messageRepository.setMessagesToSeenByChatId(chatId, SEEN);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication auth) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat Not Found"));

        final String senderId = getSenderId(chat, auth);

        final String recipientId = getRecipientId(chat, auth);

        final String filePath = fileService.saveFile(file, senderId);


        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setType(IMAGE);
        message.setState(SENT);
        message.setMediaFilePath(filePath);
        messageRepository.save(message);

        //TODO: Notification

    }

    private String getSenderId(Chat chat, Authentication auth) {

        if(chat.getSender().getId().equals(auth.getName())){
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {

        if(chat.getSender().getId().equals(authentication.getName())){
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }


}
