package com.tutu.sysinfocollect.module.sms;

/**
 * Created by tutu on 2017/7/24.
 */

public class SmsBean {
    //电话号码
    private String phone;
    //日期
    private String date;
    //短信类型
    private String type;
    //短信内容
    private String body;

    public SmsBean() {
    }

    public SmsBean(String phone, String date, String type, String body) {
        super();
        this.phone = phone;
        this.date = date;
        this.type = type;
        this.body = body;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SmsInfo [address=" + phone + ", date=" + date + ", type="
                + type + ", body=" + body + "]";
    }
}
