package com.example.elim.excel.reportOutputData;

public class MonthSalaryStatistics {

    private Integer fare;

    private Integer extraCash;

    private Integer finalOrder;

    private Integer tip;

    private Integer taxes;

    private Integer reimbursement;

    private Integer driverShare;

    /**
     * 總結1 = 車資-公帳支出
     */
    private Integer total01;

    /**
     * 總結2 = 司機可得-外收現金-勞健保
     * 可能是負數
     */
    private Integer total02;

    /**
     * 本月可分帳
     * 待確認
     */
    private Integer myProfit;

    /*public MonthSalaryOutput(Integer Integerfare, Integer extraCash){
        this.fare = fare;
        this.extraCash = extraCash;
    }*/

    public void calTotal(){
        this.total01 = fare - reimbursement;
        this.total02 = driverShare - extraCash;
    }

    //===============================

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

    public Integer getFinalOrder() {
        return finalOrder;
    }

    public void setFinalOrder(Integer finalOrder) {
        this.finalOrder = finalOrder;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Integer getTaxes() {
        return taxes;
    }

    public void setTaxes(Integer taxes) {
        this.taxes = taxes;
    }

    public Integer getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(Integer reimbursement) {
        this.reimbursement = reimbursement;
    }

    public Integer getDriverShare() {
        return driverShare;
    }

    public void setDriverShare(Integer driverShare) {
        this.driverShare = driverShare;
    }

    public Integer getTotal01() {
        return total01;
    }

    public void setTotal01(Integer total01) {
        this.total01 = total01;
    }

    public Integer getTotal02() {
        return total02;
    }

    public void setTotal02(Integer total02) {
        this.total02 = total02;
    }

    public Integer getMyProfit() {
        return myProfit;
    }

    public void setMyProfit(Integer myProfit) {
        this.myProfit = myProfit;
    }
}
