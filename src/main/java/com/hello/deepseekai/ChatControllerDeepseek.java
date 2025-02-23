package com.hello.deepseekai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatControllerDeepseek {
    public final ChatClient chatClient;

    public ChatControllerDeepseek(@Qualifier("OllamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/deep/generate")
    public Map<String, String> chatDeep(@RequestParam(value = "message", defaultValue = "Hello") String message){
        String response = this.chatClient.prompt().user(message).call().content();
        assert response != null;
        return Map.of("Response", response);
    }

    @GetMapping("/deep/generateStream")
    public Flux<String> chatDeepStream(@RequestParam(value = "message", defaultValue = "Hello") String message){
        return this.chatClient.prompt().user(message).stream().content();
    }
}
