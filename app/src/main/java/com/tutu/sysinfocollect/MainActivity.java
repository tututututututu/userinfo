package com.tutu.sysinfocollect;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tutu.sysinfocollect.utils.ToastUtils;
import com.tutu.sysinfocollect.utils.Utils;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private TextView tv_version;
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_version = (TextView) findViewById(R.id.tv_version);

        tv_version.setText("版本号:" + Utils.getAppVersionName(this.getApplication(), getPackageName()));
        rxPermissions = new RxPermissions(this);


        rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            tv_version.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MainActivity.this, JsBridgeWebViewActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 3000);
                        } else {
                            ToastUtils.showShortToast("没有获取到需要的权限");
                            finish();
                        }
                    }
                });


    }
}
