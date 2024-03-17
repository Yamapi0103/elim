package com.example.elim.controller;

import com.example.elim.dao.BusinessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @Autowired
    private BusinessDao businessDao;

    @PostMapping("/report")
    public void countByCarNo(){
        long trips = businessDao.count();
        System.out.print("總共" + trips + "筆生意");
    }

 }
