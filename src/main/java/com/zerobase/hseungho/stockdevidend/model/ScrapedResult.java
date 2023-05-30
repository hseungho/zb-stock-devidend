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
    private List<Dividend> dividendEntities;

    public ScrapedResult() {
        this.dividendEntities = new ArrayList<>();
    }

    public static ScrapedResult fromEntity(CompanyEntity companyEntity,
                                           List<DividendEntity> dividendEntities) {

        Company company = Company.builder()
                .ticker(companyEntity.getTicker())
                .name(companyEntity.getName())
                .build();

        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> Dividend.builder()
                        .dividend(e.getDividend())
                        .date(e.getDate())
                        .build())
                .collect(Collectors.toList());

        return new ScrapedResult(company, dividends);
    }
}
