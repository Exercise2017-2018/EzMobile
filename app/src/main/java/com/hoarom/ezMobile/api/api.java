package com.hoarom.ezMobile.api;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Hoarom on 11/23/2017.
 */

public class api {
    //http://m.fpts.com.vn/
    public static String VNI_API = "http://gateway.fpts.com.vn/g5g/fpts/?s=realtime_index_ho";

    public static String VNI30_API = "http://gateway.fpts.com.vn/g5g/fpts/?s=realtime_index_vni30";

    public static String HNX_API = "http://gateway.fpts.com.vn/g5g/fpts/?s=realtime_index_ha";

    public static String HNX30_API = "http://gateway.fpts.com.vn/g5g/fpts/?s=realtime_index_hnx30";

    public static String UPCOM_API = "http://gateway.fpts.com.vn/g5g/fpts/?s=realtime_index_up";

    public static ArrayList<String> APIS = new ArrayList<String>(Arrays.asList(VNI_API, VNI30_API, HNX_API, HNX30_API, UPCOM_API));

    //chi tiết cho từng quote
    // http://gateway.fpts.com.vn/G5G/fpts/?s=top_realtime&c=1&type=1
    public static String API_DETAIL = "http://gateway.fpts.com.vn/G5G/fpts/?s=top_realtime&";
//    PXS#8.68#7.56#7.59#7.68#8.12#8.04#8.12#7.68#0.53#6.98%#657,310#5,286
//    @HRC#36.20#31.50#31.65#32.10#33.85#30.00#33.85#30.00#2.20#6.95%#98,010#2,940

//    tham số c: 1,2,3,4,5; trong đó 1-HO; 2-HA; 3-UP; 4-VN30; 5-HNX30
//    tham số type: 1,2,3,4,5,6; trong đó 1: Price up; 2: price down; 3: quantity up; 4: quantity down;
// 5: value up; 6: value down

    public static int ARGUMENT_C_HO = 1;
    public static int ARGUMENT_C_HA = 2;
    public static int ARGUMENT_C_UP = 3;
    public static int ARGUMENT_C_VN30 = 4;
    public static int ARGUMENT_C_HNX30 = 5;

    public static int ARGUMENT_TYPE_PRICE_UP = 1;
    public static int ARGUMENT_TYPE_PRICE_DOWN = 2;
    public static int ARGUMENT_TYPE_QUANTITY_UP = 3;
    public static int ARGUMENT_TYPE_QUANTITY_DOWN = 4;
    public static int ARGUMENT_TYPE_VALUE_UP = 5;
    public static int ARGUMENT_TYPE_VALUE_DOWN = 6;

    //phần bảng giá
    // http://gateway.fpts.com.vn/G5G/fpts/?s=quotes&symbol=abt,acb,abi (Thông tin giá cơ bản các mã )
    public static String API_LIST_QUOTE = "http://gateway.fpts.com.vn/G5G/fpts/?s=quotes&symbol=";
//    trong đó
//    ABT#d#28.9#-0.2#60#1#31.1#27.1#29.1
//    @ACB#u#35#0.8#1,447,100#2#37.6#30.8#34.2
//    @ABI#u#28.7#0.9#2,200#3#31.9#23.7#27.8
//    Code#Color#MatchPrice#ChangePrice#TotalQtty#CenterNo#Ceiling#Floor#RefPrice
//    UpDown=u/d/n/c/f/b (u=Up; d=Down; n=NoChange; c=Ceiling; f=Floor; b=chưa khớp); CenterNo=1/2/3 (1-HO; 2-HA; 3-UP)


    //chart
    public static  String API_CHART = "http://liveprice3.fpts.com.vn/g5g/history/?s=vnindex";
}
