package com.weeklyreport.backend.exceptions;

public class MethodArgumentTypeMismatchException extends RuntimeException {
    public MethodArgumentTypeMismatchException(String message) {
        super(message);
    }
}
