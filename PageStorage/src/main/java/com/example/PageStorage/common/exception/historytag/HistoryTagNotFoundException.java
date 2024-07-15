package com.example.PageStorage.common.exception.historytag;

import jakarta.persistence.EntityNotFoundException;

public class HistoryTagNotFoundException extends EntityNotFoundException {

    Integer status;

    public HistoryTagNotFoundException(){}

    public HistoryTagNotFoundException(String message) {
        super(message);
    }

    public HistoryTagNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
