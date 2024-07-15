package com.example.PageStorage.common.exception.keyword;

import jakarta.persistence.EntityNotFoundException;

public class KeywordNotFoundException extends EntityNotFoundException {

    Integer status;

    public KeywordNotFoundException(){}

    public KeywordNotFoundException(String message) {
        super(message);
    }

    public KeywordNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
