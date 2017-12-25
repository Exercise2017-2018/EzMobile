package com.hoarom.ezMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hoarom.ezMobile.interfaces.IModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoarom on 11/23/2017.
 */

public class Quote implements IModel, Serializable, Parcelable {

    private int type;

    private String status;
    private String code;
    private Double percent;//%
    private String values;// giá trị - GT

    private Double numUp;
    private Double numAverage;
    private Double numDown;

    private Double num1;
    private Double values1;

    private String api;

    //phần main hiển thị
    //    @ABI#u#28.7#0.9#2,200#3#31.9#23.7#27.8
    //    Code#Color#MatchPrice#ChangePrice#TotalQtty#centerNo#Ceiling#Floor#refPrice\
    private String color;
    private Double matchPrice;
    private Double changePrice;
    private String totalQtty;//số lượng - SL
    private Double centerNo;
    private Double refPrice;//giá để so sánh ==> màu hiển thị
    //
//    //
    private Double ceiling;
    private Double floor;

    private Double open;
    private Double close;
    private Double average;
    private Double hightest;
    private Double lowest;

    private Double changepercent;
    private Double totalshares;
    private Double totalvalue;
//

    protected Quote(Parcel in) {
        type = in.readInt();
        status = in.readString();
        code = in.readString();

        if (in.readByte() == 0) {
            percent = null;
        } else {
            percent = in.readDouble();
        }

        values = in.readString();
        if (in.readByte() == 0) {
            numUp = null;
        } else {
            numUp = in.readDouble();
        }
        if (in.readByte() == 0) {
            numAverage = null;
        } else {
            numAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            numDown = null;
        } else {
            numDown = in.readDouble();
        }
        if (in.readByte() == 0) {
            num1 = null;
        } else {
            num1 = in.readDouble();
        }
        if (in.readByte() == 0) {
            values1 = null;
        } else {
            values1 = in.readDouble();
        }
        api = in.readString();
        color = in.readString();
        if (in.readByte() == 0) {
            matchPrice = null;
        } else {
            matchPrice = in.readDouble();
        }
        if (in.readByte() == 0) {
            changePrice = null;
        } else {
            changePrice = in.readDouble();
        }
        totalQtty = in.readString();
        if (in.readByte() == 0) {
            centerNo = null;
        } else {
            centerNo = in.readDouble();
        }
        if (in.readByte() == 0) {
            refPrice = null;
        } else {
            refPrice = in.readDouble();
        }
        if (in.readByte() == 0) {
            ceiling = null;
        } else {
            ceiling = in.readDouble();
        }
        if (in.readByte() == 0) {
            floor = null;
        } else {
            floor = in.readDouble();
        }
        if (in.readByte() == 0) {
            open = null;
        } else {
            open = in.readDouble();
        }
        if (in.readByte() == 0) {
            close = null;
        } else {
            close = in.readDouble();
        }
        if (in.readByte() == 0) {
            average = null;
        } else {
            average = in.readDouble();
        }
        if (in.readByte() == 0) {
            hightest = null;
        } else {
            hightest = in.readDouble();
        }
        if (in.readByte() == 0) {
            lowest = null;
        } else {
            lowest = in.readDouble();
        }
        if (in.readByte() == 0) {
            changepercent = null;
        } else {
            changepercent = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalshares = null;
        } else {
            totalshares = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalvalue = null;
        } else {
            totalvalue = in.readDouble();
        }
    }

    public static final Creator<Quote> CREATOR = new Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };
//                String name, String color, String matchPrice, Double changePrice,
//                        Double totalQtty, Double centerNo, Double ceiling, double floor, Double refPrice

    public Quote(String code, String color, Double matchPrice, Double changePrice,
                 String totalQtty, Double centerNo, Double ceiling, double floor, Double refPrice) {
        this.code = code;
        this.color = color;
        this.matchPrice = matchPrice;
        this.changePrice = changePrice;
        this.totalQtty = totalQtty;
        this.centerNo = centerNo;
        this.ceiling = ceiling;
        this.floor = floor;
        this.refPrice = refPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getMatchPrice() {
        return matchPrice;
    }

    public void setMatchPrice(Double matchPrice) {
        this.matchPrice = matchPrice;
    }

    public Double getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(Double changePrice) {
        this.changePrice = changePrice;
    }

    public String getTotalQtty() {
        return totalQtty;
    }

    public void setTotalQtty(String totalQtty) {
        this.totalQtty = totalQtty;
    }

    public Double getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(Double centerNo) {
        this.centerNo = centerNo;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getHightest() {
        return hightest;
    }

    public void setHightest(Double hightest) {
        this.hightest = hightest;
    }

    public Double getLowest() {
        return lowest;
    }

    public void setLowest(Double lowest) {
        this.lowest = lowest;
    }

    public Double getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(Double changepercent) {
        this.changepercent = changepercent;
    }

    public Double getTotalshares() {
        return totalshares;
    }

    public void setTotalshares(Double totalshares) {
        this.totalshares = totalshares;
    }

    public Double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Double totalvalue) {
        this.totalvalue = totalvalue;
    }

    public Double getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(Double refPrice) {
        this.refPrice = refPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    private List<S> listS;

    public Quote() {
        listS = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
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

    @Override
    public int getViewType() {
        return T_QUOTE;
    }

    @Override
    public void setViewType(int viewType) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(code);
        if (percent == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(percent);
        }

        dest.writeString(values);
        if (numUp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(numUp);
        }
        if (numAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(numAverage);
        }
        if (numDown == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(numDown);
        }
        if (num1 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(num1);
        }
        if (values1 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(values1);
        }
        dest.writeString(api);
    }
//23/11/2017~Đóng cửa MainBoard~933.7~▲~1.04~0.11~220,043,076~5,424.31~103,282~145~56~129~13,815,936~688.02~37~938.31~
    // ▲~5.65~0.61~3,032,880~69.83~2,396~934.69~
    // ▲~2.03~0.22~205,241,349~4,912.91~98,619~933.7~
    // ▲~1.04~0.11~217,938,051~5,294.61~103,274|~~~~~~~~~~~~~~~
}
