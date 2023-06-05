package com.zerobase.hseungho.stockdevidend.service.finance;

import com.zerobase.hseungho.stockdevidend.model.ScrapedResult;

public interface FinanceService {

    ScrapedResult getDividendByCompanyName(String companyName);

}
