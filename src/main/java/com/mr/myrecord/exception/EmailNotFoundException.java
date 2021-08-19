package com.mr.myrecord.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String error) {
        super(error);
    }
}