package com.hello.deepseekai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatControllerOllama {

    private final ChatClient chatClient;

    @Autowired
    public ChatControllerOllama(@Qualifier("OllamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ollama/generate")
    public Map<String,String> generate(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        String response = this.chatClient.prompt().user(message).call().content();
        assert response != null;
        return Map.of("generation", response);
    }

    @GetMapping("/ollama/generateStream")
	public Flux<ChatResponse> generateStream(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return this.chatClient.prompt().user(message).stream().chatResponse();
    }

    @GetMapping("/ollama/chatStream")
    public Flux<String> chat(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message){
        return chatClient.prompt().user(message).stream().content();
    }

}