package com.example.identityservice.customexceptions;

public class InvalidJwtToken extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidJwtToken(String message) {
        this.message = message;
    }

}
