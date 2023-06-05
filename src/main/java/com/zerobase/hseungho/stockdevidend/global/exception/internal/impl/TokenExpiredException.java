package com.zerobase.hseungho.stockdevidend.global.exception.internal.impl;

import com.zerobase.hseungho.stockdevidend.global.exception.internal.InternalErrorCode;

public class TokenExpiredException extends TokenException {
    public TokenExpiredException() {
        super(InternalErrorCode.EXPIRED_TOKEN);
    }
}
