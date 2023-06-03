package com.zerobase.hseungho.stockdevidend.scheduler;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.model.constants.CacheKey;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.DividendRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.DividendEntity;
import com.zerobase.hseungho.stockdevidend.scraper.Scraper;
import com.zerobase.hseungho.stockdevidend.service.CompanyQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@EnableCaching
@RequiredArgsConstructor
public class ScraperScheduler {

    private final Scraper yahooFinanceScraper;
    private final CompanyQueryService companyQueryService;

    private final DividendRepository dividendRepository;

    @CacheEvict(value = CacheKey.FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("[Scraper Scheduler] --> started");

        // 저장된 회사 목록 조회
        List<CompanyEntity> companies = this.companyQueryService.getAllCompany();

        // 회사마다 배당금 정보를 새로 스크래핑
        companies.forEach(this::scrapCompanyAndSaveNewDividend);

        log.info("[Scraper Scheduler] --> ended");
    }

    @Transactional
    protected void scrapCompanyAndSaveNewDividend(CompanyEntity company) {
        log.info("scraping scheduler is started -> {}", company.getName());
        ScrapedResult scrapedResult = this.yahooFinanceScraper
                .scrap(Company.of(company.getTicker(), company.getName()));

        // 스크래핑한 배당금 정보 중 DB에 없는 값은 저장
        scrapedResult.getDividends().stream()
                .map(e -> DividendEntity.of(company.getId(), e))
                .filter(e -> !this.dividendRepository
                        .existsByCompanyIdAndDate(e.getCompanyId(), e.getDate()))
                .forEach(this.dividendRepository::save);

        log.info("scraping scheduler is ended -> {}", company.getName());

        // 연속 스크래핑 요청을 하지 않도록 일정 시간(3sec.) 딜레이
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
