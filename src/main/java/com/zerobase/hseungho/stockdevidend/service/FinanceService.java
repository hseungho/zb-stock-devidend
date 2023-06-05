package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.global.exception.impl.NoCompanyException;
import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;
import com.zerobase.hseungho.stockdevidend.model.constants.CacheKey;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.DividendRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.DividendEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Transactional(readOnly = true)
    @Cacheable(key = "#companyName", value = CacheKey.FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.debug("request get dividend {} company", companyName);

        // 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = companyRepository.findByName(companyName)
                .orElseThrow(NoCompanyException::new);

        // 조회된 회사 ID로 배당금 정보 조회
        List<DividendEntity> dividends = dividendRepository.findAllByCompanyId(company.getId());

        log.debug("response get dividend {} company", companyName);
        // 결과 조합 후 반환
        return ScrapedResult.fromEntity(company, dividends);
    }

}
