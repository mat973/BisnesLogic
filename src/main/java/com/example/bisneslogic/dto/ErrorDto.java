package com.example.bisneslogic.dto;

public class ErrorDto  {
    String message;
    String error;

    public ErrorDto(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public ErrorDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
