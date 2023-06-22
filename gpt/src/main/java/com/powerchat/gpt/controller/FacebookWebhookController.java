package com.powerchat.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.RequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerchat.gpt.BananaHttpClient;
import com.powerchat.gpt.PowerChatHttpClient;
import com.powerchat.gpt.Services.QuestionService;
import com.powerchat.gpt.Services.UserService;
import com.powerchat.gpt.controller.json_mapper_models.BananaImage;
import com.powerchat.gpt.controller.json_mapper_models.WhatsAppBusinessAccount;
import com.powerchat.gpt.core.ModelType;
import com.powerchat.gpt.core.PythonBridge;
//import com.powerchat.gpt.core.resource_upload.ResourceType;
//import com.powerchat.gpt.core.resource_upload.ResourceUploader;
import com.powerchat.gpt.utils.ByteBufferEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class FacebookWebhookController {

    @Autowired
    FacebookMessageController messageController;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @PostMapping("meta-whatsapp-webhook")
    public ResponseEntity<String> getFacebookWebhookMessage(@RequestBody String payload) throws Exception {
        System.out.println(payload);
        ObjectMapper objectMapper = new ObjectMapper();
        WhatsAppBusinessAccount waAccount = objectMapper.readValue(payload, WhatsAppBusinessAccount.class);
        String message = waAccount.getSentMessage();
        UUID subscriptionID = userService.createUserIfDoesNotExists(waAccount.getName(), waAccount.getPhoneNumber());
        process(waAccount.getPhoneNumber(), message, subscriptionID);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private void process(String phoneNumber, String message, UUID subscriptionID) throws Exception {
        ModelType type = PythonBridge.classify(message);
        switch (type) {
            case text -> {
                PowerChatHttpClient powerChatHttpClient = new PowerChatHttpClient();
                String gptResponse = powerChatHttpClient.requestOpenAICompletion(message);
                System.out.println(gptResponse);
                messageController = new FacebookMessageController();
                questionService.storeQuestion(message, gptResponse, subscriptionID);
                messageController.sendReplyMessage(phoneNumber, gptResponse);
            }
            case image -> {
//                BananaHttpClient bananaHttpClient = new BananaHttpClient();
//                messageController.sendReplyMessage(phoneNumber, "Uma imagem está sendo gerada");
//                BananaImage bananaImage = bananaHttpClient.requestBananaDevCompletion(message);
//                ByteBuffer imageBytes = ByteBufferEncoder.fromBase64(bananaImage.base64());
//                ResourceUploader uploader = new ResourceUploader();
//                String assetURL = uploader.uploadAssetAndGenerateURLAddress(imageBytes, ResourceType.image);
//                messageController.sendReplyImage(phoneNumber, assetURL);
            }
        }
    }
}
