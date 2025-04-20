package com.driving_tester.backend.questions.service; // Package declaration

// ✅ Required imports
import com.driving_tester.backend.questions.modal.Question;
import com.driving_tester.backend.questions.modal.QuestionTranslation;
import com.driving_tester.backend.questions.repository.QuestionRepository;
import com.driving_tester.backend.questions.repository.QuestionTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service // Marks the class as a service component in Spring
@RequiredArgsConstructor // Lombok generates constructor for final fields
public class CSVUploadService {

    private final QuestionRepository questionRepository; // DB access for base question
    private final QuestionTranslationRepository translationRepository; // DB access for translations

    /**
     * Parses and saves the CSV file data to DB.
     * Assumes 'CustomQuestionId' column is present in CSV to link translations to the same Question.
     */
    public void parseAndSaveCSV(MultipartFile file) {
        try (
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
            );
            CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build())
        ) {
            for (CSVRecord record : csvParser) {
                // ✅ Extract fields from CSV
                String customId = record.get("CustomQuestionId");
                String language = record.get("Language");
                String questionText = record.get("Question");
                String option1 = record.get("Option1");
                String option2 = record.get("Option2");
                String option3 = record.get("Option3");
                String option4 = record.get("Option4");
                String answer = record.get("Answer");
                String explanation = record.get("Explanation");
                String image = record.get("Img");
                String category = record.get("Category");

                // ✅ Check if a question with this customId exists
                Optional<Question> existingQuestionOpt = questionRepository.findByCustomId(customId);
                Question question = existingQuestionOpt.orElseGet(() -> {
                    // Create new question if not found
                    Question newQ = Question.builder()
                            .customId(customId)
                            .imgUrl(image)
                            .build();
                    return questionRepository.save(newQ);
                });

                // ✅ Create translation for this language
                QuestionTranslation translation = QuestionTranslation.builder()
                        .language(language)
                        .questionText(questionText)
                        .option1(option1)
                        .option2(option2)
                        .option3(option3)
                        .option4(option4)
                        .answer(answer)
                        .explanation(explanation)
                        .category(category)
                        .question(question)
                        .build();

                translationRepository.save(translation); // Save translation
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse and save CSV file: " + e.getMessage());
        }
    }
}
