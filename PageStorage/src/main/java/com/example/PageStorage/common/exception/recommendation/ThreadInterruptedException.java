package com.example.PageStorage.common.exception.recommendation;

public class ThreadInterruptedException extends RuntimeException{

    Integer status;

    public ThreadInterruptedException() {}

    public ThreadInterruptedException(String message) {
        super(message);
    }

    public ThreadInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
    public ThreadInterruptedException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
