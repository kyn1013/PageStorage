package com.example.PageStorage.common.exception.member;

import jakarta.persistence.EntityNotFoundException;

public class BothLoginIdAndMailExistsException extends EntityNotFoundException {

    Integer status;

    public BothLoginIdAndMailExistsException(){}

    public BothLoginIdAndMailExistsException(String message) {
        super(message);
    }

    public BothLoginIdAndMailExistsException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
