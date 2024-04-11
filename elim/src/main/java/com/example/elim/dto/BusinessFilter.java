package com.example.elim.dto;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BusinessFilter {

    /**
     * 報表類型
     * 1 = 總表清單 BusinessList
     * 2 = 薪資結帳單 MonthSalaryReport
     */
    private Integer type;

    private String carNo;

    private String orderer;

    private String route;

    @NotNull(message = "請輸入起始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

//    @NotNull(message = "請輸入結束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    // ==============================

    public String checkTypeAndRequiredField(){
        if(this.type == 2){
            //  薪資結帳單一定要選擇車號或是調車人
            if(StringUtils.isBlank(this.carNo) && StringUtils.isBlank(this.orderer)){
                return "欲匯出薪資結帳單，車號或是調車人必填";
            }
        }
        return null;
    }

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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
