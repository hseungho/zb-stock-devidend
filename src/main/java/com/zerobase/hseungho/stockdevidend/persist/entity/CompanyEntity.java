package com.zerobase.hseungho.stockdevidend.persist.entity;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.persist.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "COMPANY")
@Table(indexes = {
        @Index(name = "idx__name", columnList = "name"),
        @Index(name = "idx__ticker", columnList = "ticker")
})
@Getter
@ToString
@NoArgsConstructor
public class CompanyEntity extends BaseEntity {

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
