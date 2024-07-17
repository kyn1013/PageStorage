package com.example.PageStorage.common.exception.member;

import jakarta.persistence.EntityNotFoundException;

public class LoginIdNotFoundException extends EntityNotFoundException {

    Integer status;

    public LoginIdNotFoundException(){}

    public LoginIdNotFoundException(String message) {
        super(message);
    }

    public LoginIdNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
