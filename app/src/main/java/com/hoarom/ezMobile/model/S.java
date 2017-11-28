package com.hoarom.ezMobile.model;

/**
 * Created by Hoarom on 11/23/2017.
 */

public class S {
    private Double main;//số chính
    private Double num1;
    private Double percent;//phần trăm
    private Double sl;//số lượng
    private Double gt;//giá trị

    public S() {

    }

    public S(Double main, Double num1, Double percent, Double sl, Double gt) {
        this.main = main;
        this.num1 = num1;
        this.percent = percent;
        this.sl = sl;
        this.gt = gt;
    }

    public Double getMain() {
        return main;
    }

    public void setMain(Double main) {
        this.main = main;
    }

    public Double getNum1() {
        return num1;
    }

    public void setNum1(Double num1) {
        this.num1 = num1;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public Double getGt() {
        return gt;
    }

    public void setGt(Double gt) {
        this.gt = gt;
    }
}
