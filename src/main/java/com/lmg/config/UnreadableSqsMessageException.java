package com.lmg.config;

public class UnreadableSqsMessageException extends RuntimeException {

    public UnreadableSqsMessageException(String message, Throwable cause) {
        super(message, cause);
    }	
}
