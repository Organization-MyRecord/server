package com.mr.myrecord.exception;

public class UnAuthorizationException extends RuntimeException{
    public UnAuthorizationException(String error) {
        super(error);
    }
}
