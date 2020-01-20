package com.sunny.service.person.exception;

/**
 * This class represents the various validation exceptions that can happen while processing request
 */
public class PersonValidationException extends Exception{

    public PersonValidationExceptionType getExceptionType() {
        return msgBoardServicePersonValidationExceptionType;
    }

    private PersonValidationExceptionType msgBoardServicePersonValidationExceptionType;


    public PersonValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonValidationException(String message, PersonValidationExceptionType msgBoardServicePersonValidationExceptionType) {
        super(message);
        this.msgBoardServicePersonValidationExceptionType = msgBoardServicePersonValidationExceptionType;
    }
}
