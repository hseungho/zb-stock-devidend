package com.zerobase.hseungho.stockdevidend.global.exception.internal;

import lombok.Getter;

@Getter
public enum InternalErrorCode {

    UNKNOWN_AUTHENTICATION(401, "인증에 실패하였습니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다.");

    private int status;
    private String message;

    InternalErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
