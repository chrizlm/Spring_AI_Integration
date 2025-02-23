package com.hello.deepseekai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
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

    @PostMapping("/ollama/file/upload")
    public String fileRead(
            @RequestParam("file")MultipartFile file
            ) throws IOException {
        String fileContent = new String(file.getBytes());

        String promptText = """
                Tell me what is written inside the text file
                {document}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("document", fileContent));

        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/ollama/rag")
    public String chatRag(
            @RequestParam(value = "message", defaultValue = "Summarize the trends") String message){
        return this.chatClient.prompt().user(message).call().content();
    }

}