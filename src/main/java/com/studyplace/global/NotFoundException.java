package com.studyplace.global;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}