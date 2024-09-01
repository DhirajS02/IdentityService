package com.example.identityservice.customexceptions;

public class RefreshTokenDoesNotExist extends Exception{
    String message;

    public RefreshTokenDoesNotExist(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
