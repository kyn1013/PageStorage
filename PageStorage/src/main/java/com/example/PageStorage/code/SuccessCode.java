package com.example.PageStorage.code;

import lombok.Getter;

@Getter
public enum SuccessCode implements BodyCode{
    SUCCES( "PA01", "Done");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
