package com.example.elim.service;

import com.example.elim.dao.BusinessDao;
import com.example.elim.dao.BusinessRepository;
import com.example.elim.dto.BusinessFilter;
import com.example.elim.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessDao businessDao;

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
}


