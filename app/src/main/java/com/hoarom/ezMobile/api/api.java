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
}
