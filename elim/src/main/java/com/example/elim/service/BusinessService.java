package com.example.elim.service;

import com.example.elim.dao.BusinessDao;
import com.example.elim.dto.BusinessFilter;
import com.example.elim.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BusinessService {

    long total();

    Business save(Business business);

    Business update(Integer id, Business business);

    void deleteById(Integer id);

    List<Business> list();

    Business findById(Integer id);

    List<Business> findByFilter(BusinessFilter filter);
}
