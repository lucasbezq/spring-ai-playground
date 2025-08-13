package br.com.lucasbezq.controller;

import br.com.lucasbezq.service.ChatService;
import br.com.lucasbezq.service.ImageService;
import br.com.lucasbezq.service.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenerativeAIController {

    private final ChatService chatService;
    private final RecipeService recipeService;
    private final ImageService imageService;

    public GenerativeAIController(ChatService chatService, RecipeService recipeService, ImageService imageService) {
        this.chatService = chatService;
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai-options")
    public String getResponseWithOptions(@RequestParam String prompt) {
        return chatService.getResponseWithOptions(prompt);
    }

    @GetMapping("/recipes")
    public String generateRecipe(@RequestParam String ingredients,
                                 @RequestParam(defaultValue = "any") String cuisine,
                                 @RequestParam(defaultValue = "none") String dietaryRestrictions) {
        return recipeService.generateRecipe(ingredients, cuisine, dietaryRestrictions);
    }

    @GetMapping("/images")
    public List<String> generateImage(@RequestParam String prompt,
                                      @RequestParam(defaultValue = "hd") String quality,
                                      @RequestParam(defaultValue = "1") Integer quantity,
                                      @RequestParam(defaultValue = "1024") Integer height,
                                      @RequestParam(defaultValue = "1024") Integer width) {
        var responses = imageService.generateImage(prompt, quality, quantity, height, width);
        return responses.getResults()
                .stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
    }
}
