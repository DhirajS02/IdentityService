package com.example.identityservice.customexceptions;

public class InvalidRefreshToken extends Exception{
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InvalidRefreshToken(String message) {
        this.message = message;
    }
}
