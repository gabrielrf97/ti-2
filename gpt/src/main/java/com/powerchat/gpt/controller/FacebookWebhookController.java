package com.powerchat.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.RequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerchat.gpt.BananaHttpClient;
import com.powerchat.gpt.PowerChatHttpClient;
import com.powerchat.gpt.core.ModelType;
import com.powerchat.gpt.core.PythonBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacebookWebhookController {

    @Autowired
    FacebookMessageController messageController;

    @PostMapping("meta-whatsapp-webhook")
    public ResponseEntity<String> getFacebookWebhookMessage(@RequestBody String payload) throws Exception {
        System.out.println(payload);
        ObjectMapper objectMapper = new ObjectMapper();
        WhatsAppBusinessAccount waAccount = objectMapper.readValue(payload, WhatsAppBusinessAccount.class);
        String message = waAccount.getSentMessage();
        ModelType type = PythonBridge.classify(message);
        process(message, type);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private void process(String message, ModelType type) {
        switch (type) {
            case text -> {
                PowerChatHttpClient powerChatHttpClient = new PowerChatHttpClient();
//                String gptResponse = powerChatHttpClient.requestOpenAICompletion(message);
                System.out.println("calling process");
                messageController.sendReplyMessage("5561981849449", "Teste");
            }
            case image -> {
                BananaHttpClient bananaHttpClient = new BananaHttpClient();
                String bananaResponse = bananaHttpClient.requestBananaDevCompletion(message);
                //CALL S3 to host.
                messageController.sendReplyImage("5561981849449", "");
            }
        }
    }
}

record Profile(String name) {}
record Contact(Profile profile, String wa_id) {}
record Text(String body) {}
record Message(String from, String id, String timestamp, String type, Text text) {}
record Metadata(String display_phone_number, String phone_number_id) {}
record Value(String messaging_product, Metadata metadata, List<Contact> contacts, List<Message> messages) {}
record Change(String field, Value value) {}
record Entry(String id, List<Change> changes) {}
record WhatsAppBusinessAccount(String object, List<Entry> entry) {
    public String getSentMessage() {
        Entry entry = entry().stream().findFirst().get();
        Change change = entry.changes().stream().findFirst().get();
        Message message = change.value().messages().stream().findFirst().get();
        String body = message.text().body();
        return body;
    }
}
