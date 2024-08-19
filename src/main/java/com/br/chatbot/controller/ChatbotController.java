package com.br.chatbot.controller;

import com.br.chatbot.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/whatsapp")
public class ChatbotController {

        private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }


    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveMessage(@RequestParam("From") String from,
                                               @RequestParam("Body") String body) {
        String responseMessage = chatbotService.processMessage(from, body);
        return ResponseEntity.ok().build();
    }
}
