package com.driving_tester.backend.quizzes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.accounts.repository.UserRepository;
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionTranslation;
import com.driving_tester.backend.questions.repository.QuestionRepository;
import com.driving_tester.backend.quizzes.dto.QuizQuestionDTO;
import com.driving_tester.backend.quizzes.model.SavedQuestion;
import com.driving_tester.backend.quizzes.repository.SavedQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SavedQuestionService {

    private final SavedQuestionRepository savedQuestionRepo;
    private final UserRepository userRepo;
    private final QuestionRepository questionRepo;

    public List<QuizQuestionDTO> getSavedQuestionsForUser(String email, String language) {
        User user = userRepo.findByEmail(email).orElseThrow();

        List<SavedQuestion> savedQuestions = savedQuestionRepo.findByUser(user);

        return savedQuestions.stream()
            .map(saved -> {
                Question question = saved.getQuestion();

                // Get the matching translation
                Optional<QuestionTranslation> translationOpt = question.getTranslations().stream()
                        .filter(t -> t.getLanguage().equalsIgnoreCase(language))
                        .findFirst();

                // If translation not found, skip
                return translationOpt.map(translation -> new QuizQuestionDTO(
                        question.getId(),
                        translation.getLanguage(),
                        translation.getQuestionText(),
                        translation.getOption1(),
                        translation.getOption2(),
                        translation.getOption3(),
                        translation.getOption4(),
                        translation.getAnswer(),
                        translation.getExplanation(),
                        translation.getCategory(),
                        question.getImgUrl()
                )).orElse(null);
            })
            .filter(dto -> dto != null) // remove nulls (if translation not found)
            .collect(Collectors.toList());
    }

    // ✅ Toggle method
    public void toggleSavedQuestion(String email, Long questionId) {
        User user = userRepo.findByEmail(email).orElseThrow();
        Question question = questionRepo.findById(questionId).orElseThrow();

        Optional<SavedQuestion> existing = savedQuestionRepo.findByUserAndQuestion(user, question);
        if (existing.isPresent()) {
            savedQuestionRepo.delete(existing.get());
        } else {
            SavedQuestion saved = SavedQuestion.builder()
                .user(user)
                .question(question)
                .savedAt(LocalDateTime.now())
                .build();
            savedQuestionRepo.save(saved);
        }
    }


    public QuizQuestionDTO getSavedQuestionById(Long savedQuestionId, String email, String language) {
        User user = userRepo.findByEmail(email).orElseThrow();
    
        SavedQuestion saved = savedQuestionRepo.findById(savedQuestionId)
                .orElseThrow(() -> new RuntimeException("Saved question not found"));
    
        if (!saved.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized access to saved question.");
        }
    
        Question question = saved.getQuestion();
    
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
    
}
