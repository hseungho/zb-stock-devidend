package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.model.constants.CacheKey;
import com.zerobase.hseungho.stockdevidend.persist.CompanyRepository;
import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

    private final Trie<String, String> trie;

    private final CompanyRepository companyRepository;

    @Override
    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public List<String> autocomplete(String keyword) {
        return this.trie.prefixMap(keyword).keySet().stream()
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = CacheKey.COMPANY_LIST)
    public List<CompanyEntity> getAllCompany() {
        return this.companyRepository.findAll();
    }

}
