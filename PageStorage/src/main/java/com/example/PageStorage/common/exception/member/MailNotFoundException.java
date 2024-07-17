package com.example.PageStorage.common.exception.member;

import jakarta.persistence.EntityNotFoundException;

public class MailNotFoundException extends EntityNotFoundException {

    Integer status;

    public MailNotFoundException(){}

    public MailNotFoundException(String message) {
        super(message);
    }

    public MailNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
