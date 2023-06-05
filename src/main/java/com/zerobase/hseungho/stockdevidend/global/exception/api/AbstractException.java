package com.zerobase.hseungho.stockdevidend.global.exception.api;

public abstract class AbstractException extends RuntimeException {

    abstract public int getStatusCode();
    abstract public String getMessage();

}
