package com.example.elim.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

public class BusinessFilter {

    private String carNo;

    private String orderer;

    //@NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    //@NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
