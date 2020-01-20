package com.sunny.service.person.exception;

/**
 * This class represents an exception scenario where a particular resource (read message) is not found
 */
public class ResourceNotFoundException extends Exception{

    public PersonValidationExceptionType getMsgBoardServicePersonValidationExceptionType() {
        return msgBoardServicePersonValidationExceptionType;
    }

    private PersonValidationExceptionType msgBoardServicePersonValidationExceptionType;


    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message, PersonValidationExceptionType msgBoardServicePersonValidationExceptionType) {
        super(message);
        this.msgBoardServicePersonValidationExceptionType = msgBoardServicePersonValidationExceptionType;
    }
}