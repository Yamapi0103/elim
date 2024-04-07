package com.example.elim.service;

import com.example.elim.dto.BusinessFilter;
import com.example.elim.model.Business;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BusinessService {

    long total();

    Business save(Business business);

    Business update(Integer id, Business business);

    void deleteById(Integer id);

    List<Business> list();

    Business findById(Integer id);

    Page<Business> listPageByFilter(BusinessFilter filter, int pageNum, int pageSize);

    List<String> getCarNoOption();

    List<String> getOrdererOption();
}
