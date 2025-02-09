package com.hello.deepseekai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DeepseekaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepseekaiApplication.class, args);
	}

	@Bean
	public ChatClient openAiChatClient(OpenAiChatModel chatModel){
		return ChatClient.create(chatModel);
	}

	@Bean
	public ChatClient OllamaChatClient(OllamaChatModel chatModel){
		return ChatClient.create(chatModel);
	}
}
