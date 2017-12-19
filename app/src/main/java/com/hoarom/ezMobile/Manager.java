package com.hoarom.ezMobile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hoarom.ezMobile.model.Quote;
import com.hoarom.ezMobile.model.QuoteDetail;
import com.hoarom.ezMobile.model.S;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Settings.COMPANIES;
import static com.hoarom.ezMobile.Settings.HNX;
import static com.hoarom.ezMobile.Settings.HNX30;
import static com.hoarom.ezMobile.Settings.VNI;
import static com.hoarom.ezMobile.Settings.VNI30;
import static com.hoarom.ezMobile.api.api.APIS;
import static com.hoarom.ezMobile.api.api.ARGUMENT_C_HA;
import static com.hoarom.ezMobile.api.api.ARGUMENT_C_HNX30;
import static com.hoarom.ezMobile.api.api.ARGUMENT_C_HO;
import static com.hoarom.ezMobile.api.api.ARGUMENT_C_UP;
import static com.hoarom.ezMobile.api.api.ARGUMENT_C_VN30;

/**
 * Created by Hoarom on 11/26/2017.
 */

public class Manager {

    public static long TIME_DELAY_AUTO_LOAD = 200;
    //định dạng tiền
    public static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###,###.##");

    public static DecimalFormat DECIMAL_FORMAT_NUM = new DecimalFormat("###,###,###,###");

    public static Quote convertStringToCompany(String data, int index) {
        if (data == null) {
            return new Quote();
        }
        Log.w("Manager", "convertStringToCompany: " + data + " index = " + index);
        Quote quote = new Quote();

        if (data == null) {
            return null;
        }

        if (index >= 0 && index < COMPANIES.size()) {

            quote.setName(COMPANIES.get(index));
            quote.setApi(APIS.get(index));

        }
        String[] list = data.replace(",", "").replace("|", "").split("▲|▼|■");
        String[] temp;

        if (list.length > 1) {
            quote.setStatus(list[0].split("~")[1]);
            //▲~1.04~0.11~223,222,616~5,482.34~103,290~145~56~129~16,956,846~741.93~44~938.31~▲
        /* public Quote(String status, Date date, Double main,
               Double num1, Double percent, Double kl, Double gt, Double numUp,
               Double numAverage, Double numDown, List<S> listS)*/
            if (list.length > 2) {
                quote.setPrice(Double.parseDouble(list[0].split("~")[2] != null ? list[0].split("~")[2] : "0"));
            }
            temp = list[1].split("~");
            if (temp.length > 10) {
                quote.setChange(Double.parseDouble(temp[1] == null ? "0" : temp[1]));
                quote.setPercent(Double.parseDouble(temp[2] == null ? "0" : temp[2]));
                quote.setNum(temp[3] == null ? "0" : temp[3]);
                quote.setValues(temp[4] == null ? "0" : temp[4]);
                quote.setNumUp(Double.parseDouble(temp[6] == null ? "0" : temp[6]));
                quote.setNumAverage(Double.parseDouble(temp[7] == null ? "0" : temp[7]));
                quote.setNumDown(Double.parseDouble(temp[8] == null ? "0" : temp[8]));
                quote.setNum1(Double.parseDouble(temp[9] == null ? "0" : temp[9]));
                quote.setValues1(Double.parseDouble(temp[10] == null ? "0" : temp[10]));

            }
            List<S> listS = new ArrayList<>();
            quote.setPrice(Double.parseDouble(list[0].split("~")[2].toString().trim()));
            for (int i = 2; i < list.length; i++) {
                temp = list[i].split("~");
                listS.add(new S(Double.parseDouble(temp[5] == null ? "0" : temp[5]),
                        Double.parseDouble(temp[1] == null ? "0" : temp[1]), Double.parseDouble(temp[2] == null ? "0" : temp[2]),
                        Double.parseDouble(temp[3] == null ? "0" : temp[3]), Double.parseDouble(temp[4] == null ? "0" : temp[4])));
                Log.w("listS", listS.get(0).getSl() + " " + temp[3]);
            }
            quote.setListS(listS);
        }

        return quote;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static List<QuoteDetail> convertStringToQuoteDetail(String data) {
        Log.w("Manager", "convertStringToQuoteDetail: " + data);
        List<QuoteDetail> list = new ArrayList<>();

        if (data.length() > 0) {
            //temp[i] là quote thứ i
            String quote[] = data.split("@");

            for (int i = 0; i < quote.length; i++) {
                String temp[] = quote[i].replace(",", "").split("#");

//    @ABI#u#28.7#0.9#2,200#3#31.9#23.7#27.8
//    Code#Color#MatchPrice#ChangePrice#TotalQtty#CenterNo#Ceiling#Floor#RefPrice
//                String code, String color, String matchPrice, Double changePrice,
//                        Double totalQtty, Double centerNo, Double ceiling, double floor, Double refPrice
                list.add(new QuoteDetail(temp[0], temp[1], temp[2], Double.parseDouble(temp[3]),
                        Double.parseDouble(temp[4]), Double.parseDouble(temp[5]), Double.parseDouble(temp[6]),
                        Double.parseDouble(temp[7]), Double.parseDouble(temp[8])));
            }
        }

        Log.w("Manager", "convertStringToQuoteDetail: " + list.size());
        return list;
    }

    public static int getCByQuoteName(String quoteName) {
        if (quoteName.equals(VNI)) {
            return ARGUMENT_C_HO;
        } else if (quoteName.equals(VNI30)) {
            return ARGUMENT_C_HA;
        } else if (quoteName.equals(HNX)) {
            return ARGUMENT_C_VN30;
        } else if (quoteName.equals(HNX30)) {
            return ARGUMENT_C_HNX30;
        } else {//UPCO
            return ARGUMENT_C_UP;
        }
    }

    //trang detail
    //danh sách quote theo tăng mạnh nhất, giảm mạnh nhất, phổ biến nhất
    public static List<QuoteDetail> getListQuoteFromString(String data) {
        List<QuoteDetail> list = new ArrayList<>();
        //PXS#8.68#7.56#7.59#7.68#8.12#8.04#8.12#7.68#0.53#6.98%#657,310#5,286
        // @HRC#36.20#31.50#31.65#32.10#33.85#30.00#33.85#30.00#2.20#6.95%#98,010#2,940
        if (data != null) {
            String item[] = data.split("@");
            for (int i = 0; i < item.length; i++) {
                String temp[] = item[i].split("#");
                Log.w("Manager", "getListQuoteFromString: " + temp.length);
            }
        }

        return list;
    }
}
