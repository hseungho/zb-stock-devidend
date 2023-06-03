package com.zerobase.hseungho.stockdevidend.model;

import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ScrapedResult {

    private Company company;
    private List<Dividend> dividends;

    public ScrapedResult() {
        this.dividends = new ArrayList<>();
    }

    public static ScrapedResult fromEntity(CompanyEntity companyEntity,
                                           List<DividendEntity> dividendEntities) {

        Company company = Company.of(companyEntity.getTicker(), companyEntity.getName());

        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> Dividend.of(e.getDate(), e.getDividend()))
                .collect(Collectors.toList());

        return new ScrapedResult(company, dividends);
    }
}
