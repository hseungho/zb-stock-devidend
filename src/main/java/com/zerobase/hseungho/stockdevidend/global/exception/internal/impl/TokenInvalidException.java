package com.zerobase.hseungho.stockdevidend.global.exception.internal.impl;

import com.zerobase.hseungho.stockdevidend.global.exception.internal.InternalErrorCode;

public class TokenInvalidException extends TokenException {
    public TokenInvalidException() {
        super(InternalErrorCode.INVALID_TOKEN);
    }
}
