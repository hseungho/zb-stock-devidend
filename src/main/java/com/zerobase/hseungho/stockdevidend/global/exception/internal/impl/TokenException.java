package com.zerobase.hseungho.stockdevidend.global.exception.internal.impl;

import com.zerobase.hseungho.stockdevidend.global.exception.internal.InternalErrorCode;
import com.zerobase.hseungho.stockdevidend.global.exception.internal.InternalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenException extends InternalException {

    protected InternalErrorCode errorCode;

    @Override
    public InternalErrorCode getErrorCode() {
        return this.errorCode;
    }

    @Override
    public int getCode() {
        return errorCode.getStatus();
    }
}
