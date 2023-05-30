package com.zerobase.hseungho.stockdevidend;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.scraper.YahooFinanceScraper;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockDevidendApplication {

    public static void main(String[] args) {
//        SpringApplication.run(StockDevidendApplication.class, args);

        ScrapedResult result = new YahooFinanceScraper().scrap(Company.builder().ticker("O").build());
//        Company result = new YahooFinanceScraper().scrapCompanyByTicker("MMM");
        System.out.println(result);
    }

}
