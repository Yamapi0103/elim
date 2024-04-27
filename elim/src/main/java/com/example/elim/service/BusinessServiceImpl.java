package com.example.elim.service;

import com.example.elim.dao.BusinessDao;
import com.example.elim.dao.BusinessRepository;
import com.example.elim.dto.BusinessFilter;
import com.example.elim.excel.ExcelFunParams;
import com.example.elim.excel.JXLSExcelUtil;
import com.example.elim.excel.reportOutputData.MonthSalaryStatistics;
import com.example.elim.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessDao businessDao;

    @Autowired
    private JXLSExcelUtil excelUtils;

    @Override
    public long total(){
        return  businessRepository.count();
    }

    @Override
    public Business save(Business business){
        Business model = businessRepository.save(business);
        System.out.print("執行新增");
        return model;
    }

    @Override
    public Business update(Integer id, Business business){
        Business model = businessRepository.findById(id).orElse(null);
        if(model != null){
            businessRepository.save(business); // 必須先檢查有沒有資料，如果沒有，執行save會變成新增
            System.out.print("執行update");
        }else{
            System.out.print("查無資料");
        }
        return model;
    }

    @Override
    public void deleteById(Integer id){
        businessRepository.deleteById(id);
        System.out.print("執行刪除");
    }

    @Override
    public List<Business> list(){
        return businessRepository.findAll();
    }

    @Override
    public Business findById(Integer id) {
        return businessRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Business> listPageByFilter(BusinessFilter filter, int pageNum, int pageSize) {
        return businessDao.findByFilter(filter, pageNum, pageSize);
    }

    @Override
    public List<String> getCarNoOption() {
        return businessRepository.getDistinctCarNo();
    }

    @Override
    public List<String> getOrdererOption() {
        return businessRepository.getDistinctOrderer();
    }

    /*@Override
    public String export(BusinessFilter filter) {
        List<Business> sqlResult = businessDao.findExportData(filter);
        List<BusinessWriteEntity> dataList = new ArrayList<>();
        for(Business data : sqlResult){
            BusinessWriteEntity entity = new BusinessWriteEntity();
            entity.setId(data.getId());
            entity.setDate(data.getDate());
            entity.setCarNo(data.getCarNo());
            entity.setRoute(data.getRoute());
            entity.setFare(data.getFare());
            entity.setExtraCash(data.getExtraCash());
            entity.setFinalOrder(data.getFinalOrder());
            entity.setTip(data.getTip());
            entity.setTaxes(data.getTaxes());
            entity.setOrderer(data.getOrderer());
            entity.setReimbursement(data.getReimbursement());
            entity.setMemo(data.getMemo());
            entity.setDriverShare(data.getDriverShare());
            dataList.add(entity);
        }

        String resultMsg = excelUtils.export(dataList);
        return resultMsg;
    }*/

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
        List<Business> dataList = businessDao.getExportData(filter);

        if (reportType == 1) {    // 匯出總表
            excelParams.setTemplateName("BusinessList");
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
            excelParams.setContextVar(contextVarMap);
            excelUtils.exportDate(excelParams);
        } else if (reportType == 3) {    // 匯出用車結帳單
            Map<String, List<Business>> ordererMap = dataList.stream()
                    .collect(Collectors.groupingBy(Business::getOrderer));

            ordererMap.forEach((key, value) -> {
                ExcelFunParams params = new ExcelFunParams();
                params.setTemplateName("OrdererMonthlyReport");
                HashMap<String, Object> contextVarMap = new HashMap<>();
                contextVarMap.put("businessList", value);
                params.setContextVar(contextVarMap);
                ExportTask myTask = new ExportTask(params);
                myTask.run();
            });
        }

        return "匯出成功";
    }
}


