package com.spring_rest.demo.rest;

public class StudentNonFoundException extends RuntimeException {

    public StudentNonFoundException(String message) {
        super(message);
    }

    public StudentNonFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNonFoundException(Throwable cause) {
        super(cause);
    }
}
