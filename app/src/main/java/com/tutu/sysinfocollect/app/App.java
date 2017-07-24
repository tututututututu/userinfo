package com.tutu.sysinfocollect.app;

import android.app.Application;

import com.tutu.sysinfocollect.utils.SPUtils;

/**
 * Created by tutu on 2017/7/24.
 */

public class App extends Application {
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        SPUtils.initSP("appData");
    }
}
