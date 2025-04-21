package com.driving_tester.backend.quizzes.dto;

import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionTranslation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-arg constructor
@AllArgsConstructor // Generates an all-args constructor
public class QuizQuestionDTO {

    private String customId; // Unique identifier consistent across languages

    private String language;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String explanation;
    private String category;
    private String imgUrl; // From the parent Question entity

    // Custom constructor to build DTO using translation and parent question
    public QuizQuestionDTO(QuestionTranslation t, Question parentQuestion) {
        this.customId = parentQuestion.getCustomId(); // Grab custom ID from parent
        this.language = t.getLanguage(); // Set translation language
        this.questionText = t.getQuestionText(); // Localized question
        this.option1 = t.getOption1();
        this.option2 = t.getOption2();
        this.option3 = t.getOption3();
        this.option4 = t.getOption4();
        this.answer = t.getAnswer(); // Localized correct answer
        this.explanation = t.getExplanation(); // Localized explanation
        this.category = t.getCategory(); // Localized category
        this.imgUrl = parentQuestion.getImgUrl(); // Image from parent question
    }
}
