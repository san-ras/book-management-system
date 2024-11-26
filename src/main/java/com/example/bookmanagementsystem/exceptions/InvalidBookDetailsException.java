package com.example.bookmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookDetailsException extends RuntimeException {
    public InvalidBookDetailsException(String message) {
        super(message);
    }
}
