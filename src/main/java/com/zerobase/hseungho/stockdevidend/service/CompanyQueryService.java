package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyQueryService {

    Page<CompanyEntity> getAllCompany(Pageable pageable);

    List<String> autocomplete(String keyword);

    List<CompanyEntity> getAllCompany();

}
