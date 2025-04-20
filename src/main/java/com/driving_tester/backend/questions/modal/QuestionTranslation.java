package com.driving_tester.backend.questions.modal;

import jakarta.persistence.*; // JPA annotations for database entity mapping
import lombok.*; // Lombok to eliminate boilerplate code

@Entity // Declares this class as a JPA entity
@Getter // Lombok: auto-generates getter methods
@Setter // Lombok: auto-generates setter methods
@NoArgsConstructor // Lombok: generates a no-args constructor
@AllArgsConstructor // Lombok: generates a full-args constructor
@Builder // Lombok: allows building objects with a fluent interface
public class QuestionTranslation {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy for the primary key
    private Long id; // Unique identifier for this translation

    private String language; // Language code, e.g., "en" or "ar"

    private String questionText; // Question content in this language

    private String option1; // Option A
    private String option2; // Option B
    private String option3; // Option C
    private String option4; // Option D

    private String answer; // Correct answer text

    @Column(length = 1000) // Adjust length as needed
    private String explanation; // Explanation for the answer

    private String category; // Category of the question (in the selected language)

    @ManyToOne // Many translations belong to one base Question
    @JoinColumn(name = "question_id") // FK to the Question table
    private Question question; // Parent question reference
}
