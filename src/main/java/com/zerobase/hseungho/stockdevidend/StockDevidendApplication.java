package com.zerobase.hseungho.stockdevidend;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class StockDevidendApplication {

    public static void main(String[] args) {
//        SpringApplication.run(StockDevidendApplication.class, args);

        try {
            Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history?period1=99100800&period2=1685318400&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
            Document document = connection.get();

            Elements elements = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element element = elements.get(0);
            Element tbody = element.children().get(1);
            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }
                String[] lines = txt.split(" ");
                String month = lines[0];
                int day = Integer.parseInt(lines[1].replace(",", ""));
                int year = Integer.parseInt(lines[2]);
                String dividend = lines[3];

                System.out.println(year+"/"+month+"/"+day+" -> "+dividend);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
