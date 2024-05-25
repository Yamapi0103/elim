package com.example.elim.service;

import com.example.elim.dto.BusinessFilter;
import com.example.elim.dto.ExcelDataTest;

import java.util.List;

public interface ExcelService {

    String export(BusinessFilter filter);

    /**
     * group by "Orderer"
     * 匯出多份檔案
     * @param dataList
     * @return
     */
    String exportOrdererMonthlyReports(List<ExcelDataTest> dataList);
}