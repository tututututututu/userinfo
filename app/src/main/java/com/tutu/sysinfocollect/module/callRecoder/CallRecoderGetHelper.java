package com.tutu.sysinfocollect.module.callRecoder;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tutu on 2017/7/24.
 */

public class CallRecoderGetHelper {

    private ContentResolver contentResolver;
    //申明通话记录的uri(通过uri才能访问到通话记录的数据库)
    private Uri calllogUri = CallLog.Calls.CONTENT_URI;


    /**
     * 查询数据库数据
     */
    public List<CallRecoderBean> getCallsListFromDb(Context context) {
        // 得到内容分解者
        contentResolver = context.getApplicationContext().getContentResolver();


        ArrayList<CallRecoderBean> dataList = new ArrayList<>();
        // 查询通话记录, 根据uri,获取指定的数据
        Cursor cursor = contentResolver.query(calllogUri, new String[]{"_id",
                "number", "date", "duration", "type",}, null, null, null);
        CallRecoderBean call;
        while (cursor.moveToNext()) {
            call = new CallRecoderBean();
            call.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            call.setPhone(cursor.getString(cursor.getColumnIndex("number")));
            call.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));
            call.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            call.setType(cursor.getInt(cursor.getColumnIndex("type")));
            dataList.add(call);
        }
        return dataList;
    }
}
