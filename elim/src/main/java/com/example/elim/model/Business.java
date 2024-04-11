package com.example.elim.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "business")
public class Business implements Serializable {
    private static final Long serialVersionUID = 1L;

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

    /**
     * 日期 介於
     * @param "date"
     * @return date between spec
     */
    public static Specification<Business> dateBetween(Date sDate, Date eDate) {
        return (root, query, builder) ->
                builder.between(root.get("date"), sDate, eDate);
    }

    /**
     * 大於等於 開始日期
     * @param "date"
     * @return date 大於等於 spec
     */
    public static Specification<Business> dateGreaterThanOrEqualTo(Date sDate) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("date"), sDate);
    }

    /**
     * 小於等於 結束日期
     * @param "date"
     * @return date 小於等於 spec
     */
    public static Specification<Business> dateLessThanOrEqualTo(Date eDate) {
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("date"), eDate);
    }

    /**
     * 車號等於
     * @param carNo
     * @return 車號等於spec
     */
    public static Specification<Business> carNoEqual(String carNo) {
        return (root, query, builder) ->
                builder.equal(root.get("carNo"), carNo);
    }

    /**
     * 調車人等於
     * @param orderer
     * @return 調車人等於spec
     */
    public static Specification<Business> ordererEqual(String orderer) {
        return (root, query, builder) ->
                builder.equal(root.get("orderer"), orderer);
    }

    /**
     * 行程 模糊查詢
     * @param route
     * @return 行程 like spec
     */
    public static Specification<Business> routeLike(String route) {
        return (root, query, builder) ->
                builder.like(root.get("route"), route);
    }

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
