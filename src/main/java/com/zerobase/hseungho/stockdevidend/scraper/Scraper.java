package com.zerobase.hseungho.stockdevidend.scraper;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;

import java.util.Optional;

public interface Scraper {

    long START_TIME = 86400;   // 60 * 60 * 24

    Optional<Company> scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);

}
