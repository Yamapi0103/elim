package com.example.elim.service;

import com.example.elim.dto.BusinessFilter;
import com.example.elim.dto.ExcelDataTest;
import com.example.elim.excel.ExcelFunParams;
import com.example.elim.excel.JXLSExcelUtil;
import com.example.elim.excel.reportOutputData.MonthSalaryStatistics;
import com.example.elim.model.Business;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private JXLSExcelUtil excelUtils;

    @Autowired
    private BusinessService businessService;

    @Override
    public String export(BusinessFilter filter) {
        Integer reportType = filter.getType();
        if(reportType == null){
            return "入參缺少報表類型Type";
        }
        String errorMsg = filter.checkTypeAndRequiredField();
        if(errorMsg != null){
            return errorMsg;
        }

        ExcelFunParams excelParams = new ExcelFunParams();
        List<Business> dataList = businessService.getExportData(filter);

        if (reportType == 1) {    // 匯出總表
            excelParams.setTemplateName("BusinessList");
            excelParams.setFolderName("總表");
            HashMap<String, Object> contextVarMap = new HashMap<>();
            contextVarMap.put("businessList", dataList);
            excelParams.setContextVar(contextVarMap);
            excelUtils.exportDate(excelParams);
        } else if (reportType == 2) {    // 匯出薪資結帳單
            MonthSalaryStatistics salaryStatistics = new MonthSalaryStatistics();
            salaryStatistics.setFare(dataList.stream().mapToInt(b -> Objects.isNull(b.getFare()) ? 0 : b.getFare()).sum());
            salaryStatistics.setExtraCash(dataList.stream().mapToInt(b -> Objects.isNull(b.getExtraCash()) ? 0 : b.getExtraCash()).sum());
            salaryStatistics.setFinalOrder(dataList.stream().mapToInt(b -> Objects.isNull(b.getFinalOrder()) ? 0 : b.getFinalOrder()).sum());
            salaryStatistics.setTip(dataList.stream().mapToInt(b -> Objects.isNull(b.getTip()) ? 0 : b.getTip()).sum());
            salaryStatistics.setTaxes(dataList.stream().mapToInt(b -> Objects.isNull(b.getTaxes()) ? 0 : b.getTaxes()).sum());
            salaryStatistics.setReimbursement(dataList.stream().mapToInt(b -> Objects.isNull(b.getReimbursement()) ? 0 : b.getReimbursement()).sum());
            salaryStatistics.setDriverShare(dataList.stream().mapToInt(b -> Objects.isNull(b.getDriverShare()) ? 0 : b.getDriverShare()).sum());
            salaryStatistics.calTotal();    // 讓物件本身自己計算兩個總結欄位

            HashMap<String, Object> contextVarMap = new HashMap<>();
            contextVarMap.put("businessList", dataList);
            contextVarMap.put("sum", salaryStatistics);
            contextVarMap.put("carNo", dataList.get(0).getCarNo());
            contextVarMap.put("sDate", dataList.get(0).getDate());
            excelParams.setTemplateName("MonthSalaryReport");
            excelParams.setFolderName("薪資結帳單");
            excelParams.setContextVar(contextVarMap);
            excelUtils.exportDate(excelParams);
        } else if (reportType == 3) {    // 匯出用車結帳單

        }

        return "匯出成功";
    }

    @Override
    public String exportOrdererMonthlyReports(List<ExcelDataTest> dataList) {
        Map<String, List<ExcelDataTest>> ordererMap = dataList.stream()
                .collect(Collectors.groupingBy(ExcelDataTest::getOrderer));

        ordererMap.forEach((key, value) -> {
            if(StringUtils.equals("讀資料結束",key)) return;
            ExcelFunParams params = new ExcelFunParams();
            params.setTemplateName("OrdererMonthlyReport");
            params.setFolderName("用車結帳單");
            params.setFileName("用車結帳單_" + key);
            HashMap<String, Object> contextVarMap = new HashMap<>();
            contextVarMap.put("ordererList", value);
            params.setContextVar(contextVarMap);
            ExportTask myTask = new ExportTask(params);
            myTask.run();
        });

        int fileCount = ordererMap.size() - 1 ;
        return "匯出成功，共" + fileCount + "份檔案";
    }
}
