package com.zerobase.hseungho.stockdevidend.global.exception.internal;

public abstract class InternalException extends RuntimeException {

    abstract public InternalErrorCode getErrorCode();
    abstract public int getCode();

}
