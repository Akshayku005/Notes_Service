package com.bridgelabz.FundooNotes_Service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public @Data class NotesException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public NotesException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public NotesException(String message) {
        super(message);
    }
}
