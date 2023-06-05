package com.zerobase.hseungho.stockdevidend.service.company;

import com.zerobase.hseungho.stockdevidend.model.Company;

public interface CompanyCommandService {

    Company save(String ticker);

    void addAutocompleteKeyword(String keyword);

    void deleteAutocompleteKeyword(String keyword);

    String deleteCompany(String ticker);

}
