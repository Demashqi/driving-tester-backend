package com.driving_tester.backend.quizzes.model;

import java.time.LocalDateTime;

import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.questions.modal.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_saved_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Question question;

    private LocalDateTime savedAt;
}
