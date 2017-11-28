package com.hoarom.ezMobile.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoarom on 11/23/2017.
 */

public class Company {
    private String status;
    private String name;
    private Double price; //~933.7

    private Double change;
    private Double percent;//%
    private String num;//số lượng - SL
    private String values;// giá trị - GT
    private Double numUp;
    private Double numAverage;
    private Double numDown;

    private Double num1;
    private Double values1;

    private String api;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    private List<S> listS;

    public Company() {
        listS = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNum1() {
        return num1;
    }

    public void setNum1(Double num1) {
        this.num1 = num1;
    }

    public Double getValues1() {
        return values1;
    }

    public void setValues1(Double values1) {
        this.values1 = values1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public Double getNumUp() {
        return numUp;
    }

    public void setNumUp(Double numUp) {
        this.numUp = numUp;
    }

    public Double getNumAverage() {
        return numAverage;
    }

    public void setNumAverage(Double numAverage) {
        this.numAverage = numAverage;
    }

    public Double getNumDown() {
        return numDown;
    }

    public void setNumDown(Double numDown) {
        this.numDown = numDown;
    }

    public List<S> getListS() {
        return listS;
    }

    public void setListS(List<S> listS) {
        this.listS = listS;
    }
//23/11/2017~Đóng cửa MainBoard~933.7~▲~1.04~0.11~220,043,076~5,424.31~103,282~145~56~129~13,815,936~688.02~37~938.31~
    // ▲~5.65~0.61~3,032,880~69.83~2,396~934.69~
    // ▲~2.03~0.22~205,241,349~4,912.91~98,619~933.7~
    // ▲~1.04~0.11~217,938,051~5,294.61~103,274|~~~~~~~~~~~~~~~
}
