package com.innowise.customArrayApp.exception;

public class CustomArrayException extends RuntimeException {

    public CustomArrayException(String message) {
        super(message);
    }

    public CustomArrayException(String message, Throwable cause) {
        super(message, cause);
    }
}