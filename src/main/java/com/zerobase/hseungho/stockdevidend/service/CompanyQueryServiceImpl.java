package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.model.constants.CacheKey;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Trie;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

    private final Trie<String, String> trie;

    private final CompanyRepository companyRepository;

    @Override
    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        log.debug("get all company -> {}", pageable);
        return companyRepository.findAll(pageable);
    }

    @Override
    public List<String> autocomplete(String keyword) {
        log.debug("get autocomplete -> {}", keyword);
        return this.trie.prefixMap(keyword).keySet().stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCompanyNamesByKeyword(String keyword) {
        log.debug("get autocomplete -> {}", keyword);
        Pageable limit = PageRequest.of(0, 10);
        return this.companyRepository.findAllByNameStartingWithIgnoreCase(keyword, limit).stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = CacheKey.COMPANY_LIST)
    public List<CompanyEntity> getAllCompany() {
        log.debug("get all company");
        return this.companyRepository.findAll();
    }

}
