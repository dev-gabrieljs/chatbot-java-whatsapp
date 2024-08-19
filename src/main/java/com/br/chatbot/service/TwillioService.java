package com.br.chatbot.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwillioService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.number}")
    private String fromPhoneNumber;

    @Value("${twilio.whatsapp.tonumber}")
    private String toPhoneNumber;

    @PostConstruct
    private void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendMessage(String body, String toPhoneNumber) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    body
            ).create();

            System.out.println("Message sent with SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
