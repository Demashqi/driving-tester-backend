package com.driving_tester.backend.questions.service; // Package declaration

// ✅ Required imports
import com.driving_tester.backend.questions.modal.Question; // Entity representing a base question
import com.driving_tester.backend.questions.modal.QuestionTranslation; // Entity representing language-specific question data
import com.driving_tester.backend.questions.repository.QuestionRepository; // Repository for base question
import com.driving_tester.backend.questions.repository.QuestionTranslationRepository; // Repository for translations
import lombok.RequiredArgsConstructor; // Lombok annotation for constructor injection
import org.apache.commons.csv.CSVFormat; // For CSV configuration
import org.apache.commons.csv.CSVParser; // Parses CSV rows
import org.apache.commons.csv.CSVRecord; // Represents each CSV record
import org.springframework.stereotype.Service; // Marks this class as a Spring Service
import org.springframework.web.multipart.MultipartFile; // Used for file upload

import java.io.BufferedReader; // Wraps character input stream for efficient reading
import java.io.InputStreamReader; // Converts byte stream to character stream
import java.nio.charset.StandardCharsets; // Standard charset for decoding bytes

@Service // Marks the class as a service component in Spring
@RequiredArgsConstructor // Lombok generates constructor for final fields
public class CSVUploadService {

    // Injecting repositories to interact with database
    private final QuestionRepository questionRepository; // For saving Question entity
    private final QuestionTranslationRepository translationRepository; // For saving QuestionTranslation entity

    /**
     * Parses the uploaded CSV file and stores questions and their translations.
     * @param file The uploaded CSV file
     */
    public void parseAndSaveCSV(MultipartFile file) {
        try (
            // Create a buffered reader using UTF-8 encoding for consistent reading
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
            );

            // Configure CSV parser using builder to avoid deprecated methods
            CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                    .setHeader() // Automatically use first row as headers
                    .setSkipHeaderRecord(true) // Skip that header record in the results
                    .setIgnoreHeaderCase(true) // Ignore case in headers (e.g., "Question" vs "question")
                    .setTrim(true) // Trim surrounding spaces in values
                    .build())
        ) {
            // Loop through each row in the CSV file
            for (CSVRecord record : csvParser) {
                // Extract values from the CSV row using header names
                String questionText = record.get("Question");
                String option1 = record.get("Option1");
                String option2 = record.get("Option2");
                String option3 = record.get("Option3");
                String option4 = record.get("Option4");
                String answer = record.get("Answer");
                String explanation = record.get("Explanation");
                String image = record.get("Img");
                String category = record.get("Category");

                // Build and save the core question entity (shared by all languages)
                Question question = Question.builder()
                        .imgUrl(image) // Set image URL
                        .build();
                questionRepository.save(question); // Save base question

                // Build and save the English translation of the question
                QuestionTranslation translation = QuestionTranslation.builder()
                        .language("en") // Currently setting default language to English
                        .category(category) // Save category inside translation
                        .questionText(questionText) // Actual question content
                        .option1(option1) // First choice
                        .option2(option2) // Second choice
                        .option3(option3) // Third choice
                        .option4(option4) // Fourth choice
                        .answer(answer) // Correct answer
                        .explanation(explanation) // Explanation of the answer
                        .question(question) // Link translation to the base question
                        .build();

                translationRepository.save(translation); // Save translation to DB
            }
        } catch (Exception e) {
            // In case of any exception (parsing or saving), throw a runtime error
            throw new RuntimeException("Failed to parse and save CSV file: " + e.getMessage());
        }
    }
}
