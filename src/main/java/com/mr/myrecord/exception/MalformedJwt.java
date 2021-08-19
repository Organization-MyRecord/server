package com.mr.myrecord.exception;

public class MalformedJwt extends RuntimeException{
    public MalformedJwt(String error) {super(error);}
}
