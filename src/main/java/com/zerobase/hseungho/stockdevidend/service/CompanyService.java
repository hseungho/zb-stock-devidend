package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.model.Company;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.DividendRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.DividendEntity;
import com.zerobase.hseungho.stockdevidend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final Trie<String, String> trie;
    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {
        if (companyRepository.existsByTicker(ticker)) {
            throw new RuntimeException("already exists ticker -> " + ticker);
        }
        return this.storeCompanyAndDividend(ticker);
    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    private Company storeCompanyAndDividend(String ticker) {
        // ticker 를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("failed to scrap ticker -> " + ticker));

        // 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크리팽
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = companyRepository.save(CompanyEntity.of(company));
        List<DividendEntity> dividendEntities = scrapedResult.getDividendEntities().stream()
                .map(dividend -> DividendEntity.of(companyEntity.getId(), dividend))
                .collect(Collectors.toList());

        dividendRepository.saveAll(dividendEntities);
        return company;
    }

    public void addAutocompleteKeyword(String keyword) {
        this.trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword) {
        return this.trie.prefixMap(keyword).keySet().stream()
                .collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyword(String keyword) {
        this.trie.remove(keyword);
    }

}
