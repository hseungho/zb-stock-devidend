package com.zerobase.hseungho.stockdevidend.persist.entity;

import com.zerobase.hseungho.stockdevidend.model.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "COMPANY")
@Getter
@ToString
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String ticker;
    private String name;

    private CompanyEntity(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }

    public static CompanyEntity of(Company company) {
        return new CompanyEntity(
                company.getTicker(),
                company.getName()
        );
    }

}
