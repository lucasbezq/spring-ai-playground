package br.com.lucasbezq.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    @Value("${spring.ai.openai.chat.options.model}")
    private String DEFAULT_MODEL;

    @Value("${spring.ai.openai.chat.options.temperature}")
    private double DEFAULT_TEMPERATURE;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getResponse(String prompt) {
        return chatModel.call(prompt);
    }

    public String getResponseWithOptions(String prompt) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OpenAiChatOptions.builder()
                                .model(DEFAULT_MODEL)
                                .temperature(DEFAULT_TEMPERATURE)
                                .build()
                ));

        return response.getResult().getOutput().getText();
    }

}
