package com.driving_tester.backend.quizzes.service;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.accounts.repository.UserRepository;
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionAttempt;
import com.driving_tester.backend.questions.repository.QuestionRepository;
import com.driving_tester.backend.quizzes.dto.QuizQuestionDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service // Marks this as a Spring-managed service class
@RequiredArgsConstructor // Lombok generates a constructor for final fields
public class MixedCategoryQuizService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    // Fetches 'count' translated questions, prioritizing unattempted ones
    public List<QuizQuestionDTO> getQuizTranslations(String email, int count, String language) {
        User user = userRepository.findByEmail(email).orElseThrow();
    
        List<Question> allQuestions = questionRepository.findAll();
    
        // Get IDs of already attempted questions
        Set<Long> attemptedIds = user.getAttempts().stream()
                .map(a -> a.getQuestion().getId()) // Use getId() instead of customId
                .collect(Collectors.toSet());
    
        // Filter unattempted questions
        List<Question> unattempted = allQuestions.stream()
                .filter(q -> !attemptedIds.contains(q.getId()))
                .collect(Collectors.toList());
    
        // Shuffle for randomness
        Collections.shuffle(unattempted);
    
        List<Question> result = new ArrayList<>();
    
        // Add as many unattempted as we can
        for (Question q : unattempted) {
            if (result.size() >= count) break;
            result.add(q);
        }
    
        // If we still need more, fetch least-attempted questions
        if (result.size() < count) {
            List<Question> attemptedSorted = allQuestions.stream()
                    .filter(q -> attemptedIds.contains(q.getId()))
                    .sorted(Comparator.comparingInt(q -> getUserAttemptCount(user, q)))
                    .collect(Collectors.toList());
    
            for (Question q : attemptedSorted) {
                if (result.size() >= count) break;
                if (!result.contains(q)) {
                    result.add(q);
                }
            }
        }
    
        // Map to DTOs and include questionId + image
        return result.stream()
                .map(question ->
                        question.getTranslations().stream()
                                .filter(t -> t.getLanguage().equalsIgnoreCase(language))
                                .findFirst()
                                .map(t -> new QuizQuestionDTO(t, question)) // Pass both translation + parent question
                                .orElse(null)
                )
                .filter(Objects::nonNull)
                .limit(count) // Just to be safe: ensure final list doesn't exceed count
                .collect(Collectors.toList());
    }
    

    // Get how many times a user attempted a specific question using questionId
    private int getUserAttemptCount(User user, Question question) {
        Long questionId = question.getId(); // Use questionId instead of customId
        return user.getAttempts().stream()
                .filter(a -> a.getQuestion().getId().equals(questionId)) // Match based on questionId
                .mapToInt(QuestionAttempt::getAttemptCount)
                .sum();
    }
    
}
