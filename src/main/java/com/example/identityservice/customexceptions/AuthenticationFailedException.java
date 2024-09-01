package com.example.identityservice.customexceptions;

public class AuthenticationFailedException extends Exception {

    String message;

    public AuthenticationFailedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
