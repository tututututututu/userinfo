package com.tutu.sysinfocollect.module.callRecoder;

/**
 * Created by tutu on 2017/7/24.
 */

public class CallRecoderBean {
    private int _id;//通话记录里面数据库的id号
    private String phone;//电话号码
    private long date;//时间
    private int type;//类型
    private int duration;//通话时长

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Call [_id=" + _id + ", number=" + phone + ", date=" + date
                + ", type=" + type + ", duration=" + duration + "]";
    }
}
