package com.driving_tester.backend.quizzes.controller;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionAttempt;
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
    

}
