package com.driving_tester.backend.quizzes.controller;

import com.driving_tester.backend.quizzes.dto.QuizQuestionDTO;
import com.driving_tester.backend.quizzes.service.MixedCategoryQuizService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController // This class handles REST API calls
@RequestMapping("/api/quizzes/mixed") // Base URL for this quiz type
@RequiredArgsConstructor // Injects final services automatically
public class MixedCategoryQuizController {

    private final MixedCategoryQuizService quizService;

    // GET: Retrieve a random list of unattempted (or least attempted) questions
    @GetMapping
    public ResponseEntity<List<QuizQuestionDTO>> getQuizQuestions(
            @RequestParam(defaultValue = "40") int count,
            @RequestParam(defaultValue = "en") String language,
            Principal principal
    ) {
        List<QuizQuestionDTO> questions = quizService.getQuizTranslations(principal.getName(), count, language);
        return ResponseEntity.ok(questions);
    }

}
