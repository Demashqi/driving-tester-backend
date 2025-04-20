package com.driving_tester.backend.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository; // JPA interface
import org.springframework.stereotype.Repository; // Marks this as a repository

import com.driving_tester.backend.questions.modal.QuestionTranslation;

@Repository // Allows Spring to inject this where needed
public interface QuestionTranslationRepository extends JpaRepository<QuestionTranslation, Long> {
    // Add queries later like: findByLanguage, findByQuestionId, etc.
}
