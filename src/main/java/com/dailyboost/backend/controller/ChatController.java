package com.dailyboost.backend.controller;

import com.dailyboost.backend.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")  // mesajele trimise la /app/send vor ajunge aici
    public void sendMessage(ChatMessage message) {
        // Trimite mesajul către destinatarul specific
        messagingTemplate.convertAndSend("/topic/chat/" + message.getToUserId(), message);
    }
}
