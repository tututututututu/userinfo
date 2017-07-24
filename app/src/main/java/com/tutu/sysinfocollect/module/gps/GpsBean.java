package com.tutu.sysinfocollect.module.gps;

/**
 * Created by tutu on 2017/7/24.
 */

public class GpsBean {
    private String lat;
    private String lng;

    public GpsBean() {
    }

    public GpsBean(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "GpsBean{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
