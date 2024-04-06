package com.example.elim.dao;

import com.example.elim.dto.BusinessFilter;
import com.example.elim.model.Business;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class BusinessDao {

    @Autowired
    private BusinessRepository businessRepository;

    public List<Business> findByFilter(BusinessFilter filter){

        Date sDate = filter.getStartDate();
        Date eDate = filter.getEndDate();
        String carNo = filter.getCarNo();
        String orderer = filter.getOrderer();
        String route = filter.getRoute();

        Specification<Business> spec = Specification.where(null);
        if(sDate != null && eDate != null){
            spec = spec.and(Business.dateBetween(sDate, eDate));
        }

        if (StringUtils.isNotBlank(carNo)) {
            spec = spec.and(Business.carNoEqual(carNo));
        }

        if (StringUtils.isNotBlank(orderer)) {
            spec = spec.and(Business.ordererEqual(orderer));
        }

        if (StringUtils.isNotBlank(route)) {
            spec = spec.and(Business.routeLike("%" + route + "%"));
        }

        return businessRepository.findAll(spec);
    }
}
