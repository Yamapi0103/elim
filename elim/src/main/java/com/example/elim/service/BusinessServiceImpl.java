package com.example.elim.service;

import com.example.elim.dao.BusinessDao;
import com.example.elim.dao.BusinessRepository;
import com.example.elim.dto.BusinessFilter;
import com.example.elim.excel.ExcelUtils;
import com.example.elim.model.Business;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessDao businessDao;

    @Autowired
    private ExcelUtils excelUtils;

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
        List<Business> businessList = businessDao.getExportData(filter);
        excelUtils.exportDate(businessList,"BusinessList");
        return "OK";
    }
}


