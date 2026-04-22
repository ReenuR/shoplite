package com.shoplite.userservice.exception;


public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(String e) {
        super(e);
    }
}
