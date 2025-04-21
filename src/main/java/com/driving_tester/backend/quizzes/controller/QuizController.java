package com.driving_tester.backend.quizzes.controller;

import com.driving_tester.backend.quizzes.dto.QuizQuestionDTO;
import com.driving_tester.backend.quizzes.service.QuizService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController // This class handles REST API calls
@RequestMapping("/api/quizzes/") // Base URL for this quiz type
@RequiredArgsConstructor // Injects final services automatically
public class QuizController {

    private final QuizService quizService;

    // POST: Record that the user attempted a specific question using customId
    @PostMapping("/attempt")
    public ResponseEntity<String> recordAttempt(
            @RequestParam Long questionId, // Use Long questionId instead of customId
            @RequestParam boolean correct,
            Principal principal
    ) {
        quizService.saveAttempt(principal.getName(), questionId, correct); // Pass questionId instead of customId
        return ResponseEntity.ok("Attempt recorded");
    }

      // Get a translated question by its ID
    @GetMapping("/questions/{id}")
    public QuizQuestionDTO getQuestionById(
            @PathVariable Long id,
            @RequestParam String language
    ) {
        return quizService.getQuestionByIdAndLanguage(id, language);
    }
    

}
