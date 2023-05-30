package com.zerobase.hseungho.stockdevidend.scraper;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.Dividend;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class YahooFinanceScraper implements Scraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";

    @Override
    public ScrapedResult scrap(Company company) {
        var scrapedResult = new ScrapedResult();
        scrapedResult.setCompany(company);

        try {
            long now = System.currentTimeMillis() / 1000;
            String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, now);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableElement = parsingDivs.get(0);
            Element tbody = tableElement.children().get(1);
            List<Dividend> dividends = new ArrayList<>();

            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] lines = txt.split(" ");
                int month = Month.strToNumber(lines[0]);
                int day = Integer.parseInt(lines[1].replace(",", ""));
                int year = Integer.parseInt(lines[2]);
                String dividend = lines[3];

                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + lines[0]);
                }

                dividends.add(Dividend.builder()
                                .date(LocalDateTime.of(year, month, day, 0, 0))
                                .dividend(dividend)
                                .build());
            }
            scrapedResult.setDividendEntities(dividends);

        } catch (IOException e) {
            // TODO
            throw new RuntimeException(e);
        }

        return scrapedResult;
    }

    @Override
    public Optional<Company> scrapCompanyByTicker(String ticker) {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleElement = document.getElementsByTag("h1").get(0);
            String title = titleElement.text().split(" - ")[1].trim();

            return Optional.of(Company.builder()
                                .ticker(ticker)
                                .name(title)
                                .build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
