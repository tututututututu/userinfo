package com.tutu.sysinfocollect.module.gps;

import com.tutu.sysinfocollect.constans.Constans;
import com.tutu.sysinfocollect.utils.SPUtils;

/**
 * Created by tutu on 2017/7/24.
 */

public class GpsGetHelper {
    public static GpsBean getGps() {
        GpsBean gpsBean = new GpsBean();
        gpsBean.setLat(SPUtils.getString(Constans.LAT));
        gpsBean.setLng(SPUtils.getString(Constans.LNG));
        return gpsBean;
    }
}
