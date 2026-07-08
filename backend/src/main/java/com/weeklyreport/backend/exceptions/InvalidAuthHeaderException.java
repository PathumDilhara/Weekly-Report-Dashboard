package com.weeklyreport.backend.exceptions;

public class InvalidAuthHeaderException extends RuntimeException {
    public InvalidAuthHeaderException(String message){
        super(message);
    }
}
