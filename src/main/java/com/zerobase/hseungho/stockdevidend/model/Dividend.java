package com.zerobase.hseungho.stockdevidend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Dividend {

    private LocalDateTime date;
    private String dividend;

}
