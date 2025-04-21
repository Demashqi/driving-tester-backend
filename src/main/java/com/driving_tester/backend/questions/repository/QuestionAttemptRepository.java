package com.driving_tester.backend.questions.repository;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {

    // Finds all question attempts made by a specific user
    List<QuestionAttempt> findByUserId(Long userId);

     // Find an existing attempt by user and question
     Optional<QuestionAttempt> findByUserAndQuestion(User user, Question question);
}
