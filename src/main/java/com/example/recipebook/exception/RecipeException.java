package com.example.recipebook.exception;

import org.springframework.http.HttpStatus;

public class RecipeException {
    private String message = "";
    private Throwable throwable = new Throwable();
    private HttpStatus httpStatus = null;

    public RecipeException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}