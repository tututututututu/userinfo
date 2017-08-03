package com.tutu.sysinfocollect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.tutu.sysinfocollect.R;
import com.tutu.sysinfocollect.constans.Constans;
import com.tutu.sysinfocollect.net.NetEngine;
import com.tutu.sysinfocollect.utils.SPUtils;
import com.tutu.sysinfocollect.utils.ToastUtils;

public class LoginActivity extends BaseActivity {
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPhone = (EditText) findViewById(R.id.et_name);


        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showShortToast("用户名不能为空");
                    return;
                }

                NetEngine.registLogin(name, new NetEngine.onRequestResult() {
                    @Override
                    public void onFail(String code,String msg) {
                        ToastUtils.showShortToast(msg);
                    }

                    @Override
                    public void onSuccess(String o) {
                        SPUtils.putString(Constans.TOKEN, o);
                        Intent intent = new Intent(LoginActivity.this, JsBridgeWebViewActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}
