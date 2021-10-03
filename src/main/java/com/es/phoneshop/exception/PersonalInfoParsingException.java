package com.es.phoneshop.exception;

public class PersonalInfoParsingException extends RuntimeException {

    public PersonalInfoParsingException(String message, Throwable cause) {
        super(message, cause);
    }
    public PersonalInfoParsingException(String message) {
        super(message);
    }

    public PersonalInfoParsingException() {
    }

}
