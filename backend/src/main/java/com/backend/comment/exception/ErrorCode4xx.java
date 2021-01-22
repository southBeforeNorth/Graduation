package com.backend.comment.exception;

public class ErrorCode4xx extends RuntimeException {
    public ErrorCode4xx(){

    }
    public ErrorCode4xx(String message) {
        super(message);
    }
}
