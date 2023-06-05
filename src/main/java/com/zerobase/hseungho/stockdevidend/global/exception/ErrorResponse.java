package com.zerobase.hseungho.stockdevidend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class ErrorResponse {
    private int code;
    private String message;
}
