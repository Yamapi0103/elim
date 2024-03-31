package com.example.elim.service;

import com.example.elim.dao.BusinessDao;
import com.example.elim.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
}


