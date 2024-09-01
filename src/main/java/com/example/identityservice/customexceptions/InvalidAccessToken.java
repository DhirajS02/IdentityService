package com.example.identityservice.customexceptions;

public class InvalidAccessToken extends Exception{
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InvalidAccessToken(String message) {
        this.message = message;
    }
}
