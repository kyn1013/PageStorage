package com.example.PageStorage.common.exception.recommendation;

public class BookItemsNotFoundException extends RuntimeException{

    Integer status;

    public BookItemsNotFoundException() {}

    public BookItemsNotFoundException(String message) {
        super(message);
    }
    public BookItemsNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}