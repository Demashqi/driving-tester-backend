package com.driving_tester.backend.questions.modal;


import com.driving_tester.backend.accounts.model.User; // Import the User entity for FK
import jakarta.persistence.*; // JPA mapping annotations
import lombok.*; // Lombok for less boilerplate
import java.time.LocalDateTime; // Time handling for when attempts are made

@Entity // Mark as a JPA entity
@Getter // Auto-generate getters
@Setter // Auto-generate setters
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
@Builder // Builder pattern support
public class QuestionAttempt {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
    private Long id; // Unique identifier for this attempt

    @ManyToOne // Many attempts can be made by one user
    @JoinColumn(name = "user_id") // FK to user table
    private User user; // Reference to the user who made the attempt

    @ManyToOne // Many attempts can belong to one question
    @JoinColumn(name = "question_id") // FK to question table
    private Question question; // Reference to the question attempted

    private boolean correct; // Whether the attempt was successful

    private int attemptCount; // How many attempts this user has made on this question

    private LocalDateTime attemptedAt; // Timestamp for when the attempt was made
}
