package com.hoarom.ezMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hoarom.ezMobile.interfaces.IModel;

import java.io.Serializable;

/**
 * Created by Hoarom on 12/13/2017.
 */

public class QuoteDetail implements IModel, Serializable, Parcelable {
    //phần main hiển thị
    //    @ABI#u#28.7#0.9#2,200#3#31.9#23.7#27.8
    //    Code#Color#MatchPrice#ChangePrice#TotalQtty#CenterNo#Ceiling#Floor#RefPrice\
    private String code;//mã
    private String color;
    private String matchPrice;
    private Double changePrice;
    private Double totalQtty;
    private Double CenterNo;
    private Double Ceiling;
    private Double Floor;
    private Double RefPrice;//giá để so sánh ==> màu hiển thị

    //
    private Double ceiling;
    private Double floor;
    private Double refercence;
    private Double open;
    private Double close;
    private Double average;
    private Double hightest;
    private Double lowest;
    private Double change;
    private Double changepercent;
    private Double totalshares;
    private Double totalvalue;


    public QuoteDetail() {
    }
//    Code#Color#MatchPrice#ChangePrice#TotalQtty#CenterNo#Ceiling#Floor#RefPrice\

    public QuoteDetail(String code, String color, String matchPrice, Double changePrice,
                       Double totalQtty, Double centerNo, Double ceiling, Double floor, Double refPrice) {
        this.code = code;
        this.color = color;
        this.matchPrice = matchPrice;
        this.changePrice = changePrice;
        this.totalQtty = totalQtty;
        CenterNo = centerNo;
        Ceiling = ceiling;
        Floor = floor;
        RefPrice = refPrice;
    }

    public QuoteDetail(String code, Double ceiling, Double floor, Double refercence, Double open,
                       Double close, Double average, Double hightest, Double lowest, Double change,
                       Double changepercent, Double totalshares, Double totalvalue) {
        this.code = code;
        this.ceiling = ceiling;
        this.floor = floor;
        this.refercence = refercence;
        this.open = open;
        this.close = close;
        this.average = average;
        this.hightest = hightest;
        this.lowest = lowest;
        this.change = change;
        this.changepercent = changepercent;
        this.totalshares = totalshares;
        this.totalvalue = totalvalue;
    }

    protected QuoteDetail(Parcel in) {
        code = in.readString();
        color = in.readString();
        matchPrice = in.readString();
        if (in.readByte() == 0) {
            changePrice = null;
        } else {
            changePrice = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalQtty = null;
        } else {
            totalQtty = in.readDouble();
        }
        if (in.readByte() == 0) {
            CenterNo = null;
        } else {
            CenterNo = in.readDouble();
        }
        if (in.readByte() == 0) {
            Ceiling = null;
        } else {
            Ceiling = in.readDouble();
        }
        if (in.readByte() == 0) {
            Floor = null;
        } else {
            Floor = in.readDouble();
        }
        if (in.readByte() == 0) {
            RefPrice = null;
        } else {
            RefPrice = in.readDouble();
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
            refercence = null;
        } else {
            refercence = in.readDouble();
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
            change = null;
        } else {
            change = in.readDouble();
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

    public static final Creator<QuoteDetail> CREATOR = new Creator<QuoteDetail>() {
        @Override
        public QuoteDetail createFromParcel(Parcel in) {
            return new QuoteDetail(in);
        }

        @Override
        public QuoteDetail[] newArray(int size) {
            return new QuoteDetail[size];
        }
    };

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMatchPrice() {
        return matchPrice;
    }

    public void setMatchPrice(String matchPrice) {
        this.matchPrice = matchPrice;
    }

    public Double getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(Double changePrice) {
        this.changePrice = changePrice;
    }

    public Double getTotalQtty() {
        return totalQtty;
    }

    public void setTotalQtty(Double totalQtty) {
        this.totalQtty = totalQtty;
    }

    public Double getCenterNo() {
        return CenterNo;
    }

    public void setCenterNo(Double centerNo) {
        CenterNo = centerNo;
    }

    public void setFloor(double floor) {
        Floor = floor;
    }

    public Double getRefPrice() {
        return RefPrice;
    }

    public void setRefPrice(Double refPrice) {
        RefPrice = refPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getCeiling() {
        return ceiling;
    }

    public void setCeiling(Double ceiling) {
        this.ceiling = ceiling;
    }

    public Double getFloor() {
        return floor;
    }

    public void setFloor(Double floor) {
        this.floor = floor;
    }

    public Double getRefercence() {
        return refercence;
    }

    public void setRefercence(Double refercence) {
        this.refercence = refercence;
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

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
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

    @Override
    public int getViewType() {
        return T_QUOTE_DETAIL;
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
        dest.writeString(code);
        dest.writeString(color);
        dest.writeString(matchPrice);
        if (changePrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(changePrice);
        }
        if (totalQtty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalQtty);
        }
        if (CenterNo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(CenterNo);
        }
        if (Ceiling == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Ceiling);
        }
        if (Floor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Floor);
        }
        if (RefPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(RefPrice);
        }
        if (ceiling == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ceiling);
        }
        if (floor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(floor);
        }
        if (refercence == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(refercence);
        }
        if (open == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(open);
        }
        if (close == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(close);
        }
        if (average == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(average);
        }
        if (hightest == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(hightest);
        }
        if (lowest == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lowest);
        }
        if (change == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(change);
        }
        if (changepercent == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(changepercent);
        }
        if (totalshares == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalshares);
        }
        if (totalvalue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalvalue);
        }
    }
}
