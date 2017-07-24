package com.tutu.sysinfocollect.module.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tutu on 2017/7/24.
 */

public class SmsGetHelper {
    private Context context;

    public SmsGetHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    //得到所有的短信
    public List<SmsBean> getSmsInfos() {
        List<SmsBean> smsInfos = new ArrayList<>();
        Uri uri = Uri.parse("content://sms");
        Cursor c = context.getContentResolver().query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        while (c.moveToNext()) {
            String phone = c.getString(c.getColumnIndex("address"));
            String date = c.getString(c.getColumnIndex("date"));
            String type = c.getString(c.getColumnIndex("type"));
            String body = c.getString(c.getColumnIndex("body"));

            SmsBean smsInfo = new SmsBean(phone, date, type, body);
            smsInfos.add(smsInfo);
        }
        return smsInfos;
    }
}
