package com.zerobase.hseungho.stockdevidend.scraper;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;

public interface Scraper {

    long START_TIME = 86400;   // 60 * 60 * 24

    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);

}
