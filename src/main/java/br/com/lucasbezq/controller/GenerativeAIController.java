package br.com.lucasbezq.controller;

import br.com.lucasbezq.service.ChatService;
import br.com.lucasbezq.service.ImageService;
import br.com.lucasbezq.service.RecipeService;
import br.com.lucasbezq.service.TranscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/generative-ai")
public class GenerativeAIController {

    private final ChatService chatService;
    private final RecipeService recipeService;
    private final ImageService imageService;
    private final TranscriptionService transcriptionService;

    public GenerativeAIController(ChatService chatService, RecipeService recipeService, ImageService imageService, TranscriptionService transcriptionService) {
        this.chatService = chatService;
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.transcriptionService = transcriptionService;
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

    @PostMapping("/transcriptions")
    public ResponseEntity<String> transcribeAudio(@RequestParam MultipartFile file) {
        try {
            var transcription = transcriptionService.transcribeAudio(file);
            return ResponseEntity.ok(transcription);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the audio file.");
        }
    }
}
