package com.hoarom.ezMobile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hoarom.ezMobile.model.Company;
import com.hoarom.ezMobile.model.S;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Settings.COMPANIES;
import static com.hoarom.ezMobile.api.api.APIS;

/**
 * Created by Hoarom on 11/26/2017.
 */

public class Manager {

    public static long TIME_DELAY_AUTO_LOAD = 200;
    //định dạng tiền
    public static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###,###.##");

    public static DecimalFormat DECIMAL_FORMAT_NUM = new DecimalFormat("###,###,###,###");

    public static Company convertStringToCompany(String data, int index) {
        Log.w("convertStringToCompany", data);
        Company company = new Company();

        if (data == null) {
            return null;
        }

        if (index >= 0 && index < COMPANIES.size()) {

            company.setName(COMPANIES.get(index));
            company.setApi(APIS.get(index));

        }
        String[] list = data.replace(",", "").replace("|", "").split("▲|▼|■");
        String[] temp;

        if (list.length > 1) {
            company.setStatus(list[0].split("~")[1]);
            //▲~1.04~0.11~223,222,616~5,482.34~103,290~145~56~129~16,956,846~741.93~44~938.31~▲
        /* public Company(String status, Date date, Double main,
               Double num1, Double percent, Double kl, Double gt, Double numUp,
               Double numAverage, Double numDown, List<S> listS)*/
            if (list.length > 2) {
                company.setPrice(Double.parseDouble(list[0].split("~")[2] != null ? list[0].split("~")[2] : "0"));
            }
            temp = list[1].split("~");
            if (temp.length > 10) {
                company.setChange(Double.parseDouble(temp[1] == null ? "0" : temp[1]));
                company.setPercent(Double.parseDouble(temp[2] == null ? "0" : temp[2]));
                company.setNum(temp[3] == null ? "0" : temp[3]);
                company.setValues(temp[4] == null ? "0" : temp[4]);
                company.setNumUp(Double.parseDouble(temp[6] == null ? "0" : temp[6]));
                company.setNumAverage(Double.parseDouble(temp[7] == null ? "0" : temp[7]));
                company.setNumDown(Double.parseDouble(temp[8] == null ? "0" : temp[8]));
                company.setNum1(Double.parseDouble(temp[9] == null ? "0" : temp[9]));
                company.setValues1(Double.parseDouble(temp[10] == null ? "0" : temp[10]));

            }
            List<S> listS = new ArrayList<>();
            company.setPrice(Double.parseDouble(list[0].split("~")[2].toString().trim()));
            for (int i = 2; i < list.length; i++) {
                temp = list[i].split("~");
                listS.add(new S(Double.parseDouble(temp[5] == null ? "0" : temp[5]),
                        Double.parseDouble(temp[1] == null ? "0" : temp[1]), Double.parseDouble(temp[2] == null ? "0" : temp[2]),
                        Double.parseDouble(temp[3] == null ? "0" : temp[3]), Double.parseDouble(temp[4] == null ? "0" : temp[4])));
                Log.w("listS", listS.get(0).getSl() + " " + temp[3]);
            }
            company.setListS(listS);
        }

        return company;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
