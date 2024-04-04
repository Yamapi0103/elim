package com.example.elim.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="car_no")
    private String carNo;

    @Column(name="date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name="route")
    private String route;

    @Column(name="fare")
    private Integer fare;

    @Column(name="extra_cash")
    private Integer extraCash;

    @Column(name="final_order")
    private String finalOrder;

    @Column(name="tip")
    private String tip;

    @Column(name="taxes")
    private Integer taxes;

    @Column(name="orderer")
    private String orderer;

    @Column(name="reimbursement")
    private String reimbursement;

    @Column(name="memo")
    private String memo;

    @Column(name="driver_share")
    private Integer driverShare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Integer getExtraCash() {
        return extraCash;
    }

    public void setExtraCash(Integer extraCash) {
        this.extraCash = extraCash;
    }

    public String getFinalOrder() {
        return finalOrder;
    }

    public void setFinalOrder(String finalOrder) {
        this.finalOrder = finalOrder;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getTaxes() {
        return taxes;
    }

    public void setTaxes(Integer taxes) {
        this.taxes = taxes;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getDriverShare() {
        return driverShare;
    }

    public void setDriverShare(Integer driverShare) {
        this.driverShare = driverShare;
    }
}
