package com.example.elim.service;

import com.example.elim.dao.BusinessDao;
import com.example.elim.dto.BusinessFilter;
import com.example.elim.model.Business;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessDao businessDao;

    @Override
    public long total(){
        return  businessDao.count();
    }

    @Override
    public Business save(Business business){
        Business model = businessDao.save(business);
        System.out.print("執行新增");
        return model;
    }

    @Override
    public Business update(Integer id, Business business){
        Business model = businessDao.findById(id).orElse(null);
        if(model != null){
            businessDao.save(business); // 必須先檢查有沒有資料，如果沒有，執行save會變成新增
            System.out.print("執行update");
        }else{
            System.out.print("查無資料");
        }
        return model;
    }

    @Override
    public void deleteById(Integer id){
        businessDao.deleteById(id);
        System.out.print("執行刪除");
    }

    @Override
    public List<Business> list(){
        return businessDao.findAll();
    }

    @Override
    public Business findById(Integer id) {
        return businessDao.findById(id).orElse(null);
    }

    @Override
    public List<Business> findByFilter(BusinessFilter filter) {
        String carNo = filter.getCarNo();
        String orderer = filter.getOrderer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(filter.getStartDate());
        String eDate = sdf.format(filter.getEndDate());

        if (!StringUtils.isBlank(carNo)) {
            return businessDao.findByCarNoAndDate(carNo, sDate, eDate);
        } else if (!StringUtils.isBlank(orderer)) {
            return businessDao.findByOrdererAndDate(orderer, sDate, eDate);
        }
        return new ArrayList<Business>();
    }
}


