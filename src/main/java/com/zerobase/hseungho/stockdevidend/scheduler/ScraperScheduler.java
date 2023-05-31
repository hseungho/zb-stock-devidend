package com.zerobase.hseungho.stockdevidend.scheduler;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.DividendRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.DividendEntity;
import com.zerobase.hseungho.stockdevidend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScraperScheduler {

    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("[Scraper Scheduler] --> started");

        // 저장된 회사 목록 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        companies.forEach(company -> {
            log.info("scraping scheduler is started -> {}", company.getName());
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
                    Company.builder()
                            .name(company.getName())
                            .ticker(company.getTicker())
                            .build());

            // 스크래핑한 배당금 정보 중 DB에 없는 값은 저장
            scrapedResult.getDividends().stream()
                    .map(e -> DividendEntity.of(company.getId(), e))
                    .filter(e -> !this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate()))
                    .forEach(this.dividendRepository::save);

            log.info("scraping scheduler is ended -> {}", company.getName());

            // 연속 스크래핑 요청을 하지 않도록 일정 시간(3sec.) 딜레이
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        log.info("[Scraper Scheduler] --> ended");
    }

}
