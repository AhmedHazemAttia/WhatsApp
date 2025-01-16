package com.ahmed.whatsappclone.chat;


import com.ahmed.whatsappclone.common.StringResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService service;

    @PostMapping
    public ResponseEntity<StringResponse> createChat(
            @RequestParam(name = "sender_id") String senderId,
            @RequestParam(name = "receiver_id") String receiverId){

        final String chatId = service.createChat(senderId,receiverId);
        StringResponse response = StringResponse.builder().response(chatId).build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatsByReceiver(Authentication auth){
        return ResponseEntity.ok(service.getChatByReceiverId(auth));
    }

}
