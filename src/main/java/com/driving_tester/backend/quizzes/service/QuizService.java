package com.driving_tester.backend.quizzes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.accounts.repository.UserRepository;
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionAttempt;
import com.driving_tester.backend.questions.modal.QuestionTranslation;
import com.driving_tester.backend.questions.repository.QuestionAttemptRepository;
import com.driving_tester.backend.questions.repository.QuestionRepository;
import com.driving_tester.backend.quizzes.dto.QuizQuestionDTO;

import lombok.RequiredArgsConstructor;

@Service // Marks this as a Spring-managed service class
@RequiredArgsConstructor // Lombok generates a constructor for final fields
public class QuizService {
    
    private final QuestionRepository questionRepository;
    private final QuestionAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepo;



     // Save or update an attempt using customId (language-independent tracking)
    public void saveAttempt(String email, Long questionId, boolean correct) {
        User user = userRepository.findByEmail(email).orElseThrow();
    
        // Find the question by questionId
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found for questionId: " + questionId));
    
        // Check if an attempt already exists
        Optional<QuestionAttempt> existing = attemptRepository.findByUserAndQuestion(user, question);
    
        if (existing.isPresent()) {
            QuestionAttempt attempt = existing.get();
            attempt.setAttemptCount(attempt.getAttemptCount() + 1);
            attempt.setCorrect(correct);
            attempt.setAttemptedAt(LocalDateTime.now());
            attemptRepository.save(attempt);
        } else {
            QuestionAttempt attempt = QuestionAttempt.builder()
                    .user(user)
                    .question(question)
                    .correct(correct)
                    .attemptCount(1)
                    .attemptedAt(LocalDateTime.now())
                    .build();
            attemptRepository.save(attempt);
        }
    }


    public QuizQuestionDTO getQuestionByIdAndLanguage(Long questionId, String language) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Optional<QuestionTranslation> translationOpt = question.getTranslations().stream()
                .filter(t -> t.getLanguage().equalsIgnoreCase(language))
                .findFirst();

        return translationOpt.map(t -> new QuizQuestionDTO(
                question.getId(),
                t.getLanguage(),
                t.getQuestionText(),
                t.getOption1(),
                t.getOption2(),
                t.getOption3(),
                t.getOption4(),
                t.getAnswer(),
                t.getExplanation(),
                t.getCategory(),
                question.getImgUrl()
        )).orElseThrow(() -> new RuntimeException("Translation not found for language: " + language));
    }

    public List<QuizQuestionDTO> getAllQuestionsByLanguage(String language) {
        List<Question> allQuestions = questionRepository.findAll();

        return allQuestions.stream()
                .map(question ->
                    question.getTranslations().stream()
                            .filter(t -> t.getLanguage().equalsIgnoreCase(language))
                            .findFirst()
                            .map(t -> new QuizQuestionDTO(t, question)) // Combine translation + base question
                            .orElse(null)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


}
