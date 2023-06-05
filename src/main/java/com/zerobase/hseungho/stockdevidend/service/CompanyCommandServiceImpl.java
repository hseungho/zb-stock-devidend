package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.global.exception.impl.InternalServerErrorException;
import com.zerobase.hseungho.stockdevidend.global.exception.impl.NoCompanyException;
import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.model.constants.CacheKey;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.DividendRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.DividendEntity;
import com.zerobase.hseungho.stockdevidend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Trie;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyCommandServiceImpl implements CompanyCommandService {

    private final Trie<String, String> trie;
    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Override
    @CacheEvict(value = CacheKey.COMPANY_LIST, allEntries = true)
    public Company save(String ticker) {
        if (companyRepository.existsByTicker(ticker)) {
            throw new RuntimeException("already exists ticker -> " + ticker);
        }
        return this.storeCompanyAndDividend(ticker);
    }

    @Transactional
    protected Company storeCompanyAndDividend(String ticker) {
        log.info("saved company start -> {}", ticker);

        // ticker 를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker)
                .orElseThrow(() -> {
                    log.error("failed to scrap ticker -> " + ticker);
                    return new InternalServerErrorException();
                });

        // 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크리팽
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = companyRepository.save(CompanyEntity.of(company));
        List<DividendEntity> dividendEntities = scrapedResult.getDividends().stream()
                .map(dividend -> DividendEntity.of(companyEntity.getId(), dividend))
                .collect(Collectors.toList());

        dividendRepository.saveAll(dividendEntities);

        log.info("saved company end -> {}", ticker);
        return company;
    }

    @Override
    public void addAutocompleteKeyword(String keyword) {
        this.trie.put(keyword, null);
    }

    @Override
    public void deleteAutocompleteKeyword(String keyword) {
        this.trie.remove(keyword);
    }

    @Override
    @Transactional
    public String deleteCompany(String ticker) {
        log.info("deleted company start -> {}", ticker);

        CompanyEntity company = this.companyRepository.findByTicker(ticker)
                .orElseThrow(NoCompanyException::new);

        this.dividendRepository.deleteAllByCompanyId(company.getId());
        this.companyRepository.delete(company);

        this.deleteAutocompleteKeyword(company.getName());

        log.info("deleted company end -> {}", ticker);
        return company.getName();
    }

}
