package com.zerobase.hseungho.stockdevidend.global.exception.impl;

import com.zerobase.hseungho.stockdevidend.global.exception.AbstractException;

public class MisMatchPasswordException extends AbstractException  {
    @Override
    public int getStatusCode() {
        return 400;
    }

    @Override
    public String getMessage() {
        return "비밀번호가 일치하지 않습니다.";
    }
}
