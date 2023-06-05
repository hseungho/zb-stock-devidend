package com.zerobase.hseungho.stockdevidend.global.exception.api.impl;

import com.zerobase.hseungho.stockdevidend.global.exception.api.AbstractException;

public class NotFoundMemberException extends AbstractException {
    @Override
    public int getStatusCode() {
        return 400;
    }

    @Override
    public String getMessage() {
        return "회원정보가 없습니다.";
    }
}
