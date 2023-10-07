package com.backend.quack.exception;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
