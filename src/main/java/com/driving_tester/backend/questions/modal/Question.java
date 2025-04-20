package com.driving_tester.backend.questions.modal;

import jakarta.persistence.*; // Import JPA annotations for ORM mapping
import lombok.*; // Import Lombok annotations to reduce boilerplate code
import java.util.ArrayList; // ArrayList used to initialize lists
import java.util.List; // List interface for collections

@Entity // Marks this class as a JPA entity to map to a DB table
@Getter // Lombok: auto-generates all getters
@Setter // Lombok: auto-generates all setters
@NoArgsConstructor // Lombok: generates a no-args constructor
@AllArgsConstructor // Lombok: generates a full-args constructor
@Builder // Lombok: allows using the builder pattern to construct objects
public class Question {

    @Id // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto-generates this ID
    private Long id; // Unique identifier for the question

    private String imgUrl; // Optional image URL linked to the question

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    // One question has many translations (EN, AR, etc.)
    private List<QuestionTranslation> translations = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    // Tracks all attempts on this question by users
    private List<QuestionAttempt> attempts = new ArrayList<>();

    public double getSuccessRate() {
        long total = attempts.size(); // Total attempts made on this question
        long correct = attempts.stream().filter(QuestionAttempt::isCorrect).count(); // How many were correct
        return total == 0 ? 0.0 : (double) correct / total; // Avoid division by 0
    }
}
