package com.example.elim.controller;

import com.example.elim.dao.BusinessDao;
import com.example.elim.model.Business;
import com.example.elim.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/business")
@RestController
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @PostMapping("/total")
    public long total(){
        long trips = businessService.total();
        System.out.println("總共" + trips + "筆生意");
        return trips;
    }

    @PostMapping("/save")
    public Business save(@RequestBody Business business){
        Business model = businessService.save(business);
        System.out.print("執行新增");
        return model;
    }

    @PutMapping("/{id}")
    public Business update(@PathVariable Integer id,
                       @RequestBody Business business){
        return businessService.update(id, business);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        businessService.deleteById(id);
        System.out.print("執行刪除");
    }

    @GetMapping("/{id}")
    public Business read(@PathVariable Integer id){
        return businessService.findById(id);
    }

    @GetMapping("/list")
    public List<Business> list(){
        return businessService.list();
    }
 }
