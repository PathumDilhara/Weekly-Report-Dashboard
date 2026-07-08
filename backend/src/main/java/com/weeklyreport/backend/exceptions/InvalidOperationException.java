package com.weeklyreport.backend.exceptions;

public class InvalidOperationException extends RuntimeException{
    public InvalidOperationException(String message) {
        super(message);
    }

}
