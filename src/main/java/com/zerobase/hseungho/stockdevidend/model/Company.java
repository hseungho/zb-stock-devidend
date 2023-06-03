package com.zerobase.hseungho.stockdevidend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Company {

    private String ticker;
    private String name;

}
