package com.hoarom.ezMobile.interfaces;

import android.os.Parcelable;

/**
 * Created by Hoarom on 12/19/2017.
 */

public interface IModel extends Parcelable {

    public static int T_QUOTE = 1;
    public static int T_QUOTE_DETAIL = 2;
     public static int T_QUOTE_DETAIL_ITEM = 4;//chi tiết dưới bảng giá

    public int getViewType();

    public void setViewType(int viewType);
}
