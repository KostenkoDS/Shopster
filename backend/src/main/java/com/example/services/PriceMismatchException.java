package com.example.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Prices are invalid")
public class PriceMismatchException extends RuntimeException {
    public PriceMismatchException(String message) {
        super(message);
    }
}
