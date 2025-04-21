package com.driving_tester.backend.quizzes.controller;

import com.driving_tester.backend.quizzes.dto.QuizQuestionDTO;
import com.driving_tester.backend.quizzes.model.SavedQuestion;
import com.driving_tester.backend.quizzes.service.SavedQuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes/saved")
@RequiredArgsConstructor
public class SavedQuestionController {

    private final SavedQuestionService savedQuestionService;

    @PostMapping("/toggle")
    public ResponseEntity<Void> toggleSaved(
            @RequestParam Long questionId,
            Principal principal
    ) {
        savedQuestionService.toggleSavedQuestion(principal.getName(), questionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public QuizQuestionDTO getSavedQuestionById(
            @PathVariable Long id,
            @RequestParam String language,
            Principal principal
    ) {
        return savedQuestionService.getSavedQuestionById(id, principal.getName(), language);
    }


    @GetMapping("/all")
    public ResponseEntity<List<QuizQuestionDTO>> getSavedQuestions(Principal principal, String language) {
        List<QuizQuestionDTO> savedQuestions = savedQuestionService.getSavedQuestionsForUser(principal.getName(), language);
        return ResponseEntity.ok(savedQuestions);
    }
    
}
