package com.example.exception;

public class EmployeeInvalidException extends RuntimeException{

    public EmployeeInvalidException(String message){
        super(message);
    }
    
}
