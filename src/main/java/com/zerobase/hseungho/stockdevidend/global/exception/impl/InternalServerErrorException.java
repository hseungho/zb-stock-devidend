package com.zerobase.hseungho.stockdevidend.global.exception.impl;

import com.zerobase.hseungho.stockdevidend.global.exception.AbstractException;

public class InternalServerErrorException extends AbstractException {
    @Override
    public int getStatusCode() {
        return 500;
    }

    @Override
    public String getMessage() {
        return "시스템에 오류가 발생하였습니다. 관리자에게 문의해주세요.";
    }
}
