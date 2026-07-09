package com.weeklyreport.backend.exceptions;

public class ObjNotFoundException extends RuntimeException{
    public ObjNotFoundException(String message){
        super(message);
    }
}