package com.driving_tester.backend.questions.controller;


import lombok.RequiredArgsConstructor; // For auto-generating constructor with required dependencies
import org.springframework.http.ResponseEntity; // For sending HTTP responses
import org.springframework.security.access.prepost.PreAuthorize; // For restricting access to admin only
import org.springframework.web.bind.annotation.*; // For REST controller annotations
import org.springframework.web.multipart.MultipartFile; // To accept uploaded files

import com.driving_tester.backend.questions.service.CSVUploadService;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/questions/upload") // Base URL for this controller
@RequiredArgsConstructor // Lombok: generates constructor with required final fields
public class CSVUploadController {

    private final CSVUploadService csvUploadService; // Service to handle logic of CSV parsing and saving

    @PostMapping // Handles POST requests to upload the CSV
    @PreAuthorize("hasRole('ADMIN')") // Only allow users with role ADMIN to upload
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        // Call the service to handle upload and parsing
        csvUploadService.parseAndSaveCSV(file);
        // Return 200 OK response if successful
        return ResponseEntity.ok("CSV file uploaded and processed successfully.");
    }
}
