package com.driving_tester.backend.quizzes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.quizzes.model.SavedQuestion;

public interface SavedQuestionRepository extends JpaRepository<SavedQuestion, Long> {
    Optional<SavedQuestion> findByUserAndQuestion(User user, Question question);
    List<SavedQuestion> findByUser(User user);
    void deleteByUserAndQuestion(User user, Question question);
}
