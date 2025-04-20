package com.driving_tester.backend.accounts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessDeniedException extends RuntimeException {
    private final String message;
}