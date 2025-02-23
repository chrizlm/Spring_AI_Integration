package com.hello.deepseekai;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication(exclude = {OpenAiAutoConfiguration.class})
public class DeepseekaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepseekaiApplication.class, args);
	}


	public ChatClient openAiChatClient(OpenAiChatModel chatModel){
		return ChatClient.create(chatModel);
	}

	@Bean
	@Primary
	public ChatClient OllamaChatClient(OllamaChatModel chatModel, PgVectorStore vectorStore){
		return ChatClient
				.builder(chatModel)
				.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
				.build();
	}
}
