package com.example.PageStorage.common.exception.recommendation;

public class MaxRetriesExceededException extends RuntimeException{

    Integer status;

    public MaxRetriesExceededException() {}

    public MaxRetriesExceededException(String message) {
        super(message);
    }
    public MaxRetriesExceededException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
