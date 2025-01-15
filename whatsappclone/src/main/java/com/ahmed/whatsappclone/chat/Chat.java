package com.ahmed.whatsappclone.chat;

import com.ahmed.whatsappclone.common.BaseAuditingEntity;
import com.ahmed.whatsappclone.message.Message;
import com.ahmed.whatsappclone.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.ahmed.whatsappclone.chat.ChatConstants.FIND_CHAT_BY_SENDER_ID;
import static com.ahmed.whatsappclone.chat.ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER;
import static com.ahmed.whatsappclone.message.MessageState.SENT;
import static com.ahmed.whatsappclone.message.MessageType.TEXT;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
@NamedQuery(name = FIND_CHAT_BY_SENDER_ID,
            query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.recipient.id = :senderId ORDER BY createdDate DESC")
@NamedQuery(name = FIND_CHAT_BY_SENDER_ID_AND_RECEIVER,
            query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.recipient.id = :recipientId) OR (c.sender.id = :recipientId AND c.recipient.id = :senderId) ORDER BY createdDate DESC")
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToMany(mappedBy = "chat", fetch = EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages;


//
//    /*
//    Case: if we have 2 chats Ahmed and Hazem
//    if the SenderId(Ahmed) then i must be chatting with Hazem, so the chatName would be Hazem and vice Versa
//     */
    @Transient
    public String getChatName(final String senderId){
        if(recipient.getId().equals(senderId)){
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }
//
    @Transient
    public Long getUnreadMessages(final String senderId){
        return messages
                .stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> SENT == m.getState())
                .count();
    }

    @Transient
    public String getLastMessage() {
        if(messages != null && !messages.isEmpty()){
            if(messages.get(0).getType() != TEXT){
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime(){
        if(messages != null && !messages.isEmpty()){
            return messages.get(0).getCreatedDate();
        }
        return null;
    }
}
