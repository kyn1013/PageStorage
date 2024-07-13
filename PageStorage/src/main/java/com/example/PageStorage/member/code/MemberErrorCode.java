package com.example.PageStorage.member.code;

import com.example.PageStorage.common.code.BodyCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements BodyCode {
    Member_NOT_FOUND("MEM01", "유저를 찾을 수 없습니다."),
    Member_ID_ALREADY_EXISTS("MEM02","아이디가 이미 존재합니다."),
    ;

    private final String message;
    private final String code;

    MemberErrorCode(String message, String code) {
        this.message = message;
        this.code = code;
    }


}
