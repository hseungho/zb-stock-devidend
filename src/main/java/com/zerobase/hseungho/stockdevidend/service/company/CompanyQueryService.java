package com.zerobase.hseungho.stockdevidend.service.company;

import com.zerobase.hseungho.stockdevidend.persist.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyQueryService {

    Page<CompanyEntity> getAllCompany(Pageable pageable);

    List<String> autocomplete(String keyword);

    List<String> getCompanyNamesByKeyword(String keyword);

    List<CompanyEntity> getAllCompany();

}
