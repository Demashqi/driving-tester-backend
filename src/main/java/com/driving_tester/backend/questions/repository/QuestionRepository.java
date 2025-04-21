package com.driving_tester.backend.questions.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // Interface for JPA
import org.springframework.stereotype.Repository; // Marks this as a Spring repository

import com.driving_tester.backend.questions.modal.Question;

@Repository // Enables Spring to discover this interface
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // You can later add custom queries like findByCategory, etc.
    Optional<Question> findByCustomId(String customId); // Fetch by CSV-defined custom ID

    
    // This uses nested property to search inside translations table for matching category
    List<Question> findByTranslations_CategoryIgnoreCase(String category);
}
