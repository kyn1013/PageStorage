package com.example.PageStorage.common.exception.history;

import jakarta.persistence.EntityNotFoundException;

public class HistoryNotFoundException  extends EntityNotFoundException {

    Integer status;

    public HistoryNotFoundException(){}

    public HistoryNotFoundException(String message) {
        super(message);
    }

    public HistoryNotFoundException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
