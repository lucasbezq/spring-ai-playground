package br.com.lucasbezq.service;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final OpenAiImageModel imageModel;

    public ImageService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public ImageResponse generateImage(String prompt, String quality, Integer quantity,
                                       Integer height, Integer width) {
        return imageModel.call(
                new ImagePrompt(
                        prompt,
                        OpenAiImageOptions.builder()
                                .quality(quality)
                                .N(quantity)
                                .height(height)
                                .width(width).build())
        );
    }
}
