package com.example.elim.dao;

import com.example.elim.dto.BusinessFilter;
import com.example.elim.model.Business;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BusinessDao {

    @Autowired
    private BusinessRepository businessRepository;

    public Page<Business> findByFilter(BusinessFilter filter, int pageNum, int pagesize){

        Specification<Business> spec = this.newSpecificationByCondition(filter);
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(new Sort.Order(Sort.Direction.DESC,"date"));
        sorts.add(new Sort.Order(Sort.Direction.DESC,"id"));
        Pageable pageable = PageRequest.of(pageNum, pagesize, Sort.by(sorts));

        return businessRepository.findAll(spec, pageable);
    }

    public List<Business> getExportData(BusinessFilter filter){
        Specification<Business> spec = this.newSpecificationByCondition(filter);
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(new Sort.Order(Sort.Direction.ASC,"date"));
        sorts.add(new Sort.Order(Sort.Direction.ASC,"id"));

        return businessRepository.findAll(spec, Sort.by(sorts));
    }

    private Specification<Business> newSpecificationByCondition(BusinessFilter filter){
        Date sDate = filter.getStartDate();
        Date eDate = filter.getEndDate();
        String carNo = filter.getCarNo();
        String orderer = filter.getOrderer();
        String route = filter.getRoute();

        Specification<Business> spec = Specification.where(null);
        if(sDate != null && eDate != null){
            // AND date between 'sDate' and 'eDate'
            spec = spec.and(Business.dateBetween(sDate, eDate));
        }else if (sDate != null) {
            // AND date >= 'sDate'
            spec = spec.and(Business.dateGreaterThanOrEqualTo(sDate));
        }else if(eDate != null){
            // AND date <= 'eDate'
            spec = spec.and(Business.dateLessThanOrEqualTo(eDate));
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

        return spec;
    }
}
