package com.example.ECommerce.Exceptions;

public class EmailAlreadyPresentException extends Throwable {
    public EmailAlreadyPresentException(String message) {
        super(message);
    }
}
