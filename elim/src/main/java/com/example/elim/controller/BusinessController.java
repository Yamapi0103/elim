package com.example.elim.controller;

import com.example.elim.dao.BusinessDao;
import com.example.elim.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/business")
@RestController
public class BusinessController {

    @Autowired
    private BusinessDao businessDao;

    @PostMapping("/total")
    public void total(){
        long trips = businessDao.count();
        System.out.println("總共" + trips + "筆生意");
    }

    @PostMapping("/save")
    public Business save(@RequestBody Business business){
        Business model = businessDao.save(business);
        System.out.print("執行新增");
        return model;
    }

    @PutMapping("/{id}")
    public Business update(@PathVariable Integer id,
                       @RequestBody Business business){
        Business model = businessDao.findById(id).orElse(null);
        if(model != null){
            businessDao.save(business);
            System.out.print("執行update");
        }else{
            System.out.print("查無資料");
        }
        return model;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        businessDao.deleteById(id);
        System.out.print("執行刪除");
    }

    @GetMapping("/{id}")
    public Business read(@PathVariable Integer id){
        Business model = businessDao.findById(id).orElse(null);
        return model;
    }

    @GetMapping("/list")
    public List<Business> list(){
        return businessDao.findAll();
    }
 }
