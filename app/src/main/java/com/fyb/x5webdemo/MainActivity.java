package com.fyb.x5webdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fyb.x5webdemo.common.Const;
import com.fyb.x5webdemo.views.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

public class MainActivity extends AppCompatActivity {

    /**
     * 加载网页的 webview
     */
    private X5WebView mWebView;
    /**
     * 网页未加载出来之前，展示的图片控件
     */
    private ImageView mIv;

    /**
     * 记录用户点击后退按钮的时间差
     */
    private long endTime;

    /**
     * 回调方法：
     * 相当于 vue 组件中的  created 。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {

        //  初始化视图
        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        mIv = findViewById(R.id.iv);
        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl(Const.BASE_WEB_URL);
        mWebView.setOnWebViewListener(new X5WebView.OnWebViewListener() {
            @Override
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    mIv.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 监听 android 后退按钮点击事件。
     * 1、首先判断当前网页是否还可以进行后退页面的操作，如果可以的话那么就后退网页。
     * 2、如果网页已经不可以进行后退操作了（即：网页在首页中，虚拟任务栈中，只包含了 imooc 。）
     * 在这种情况下，则会提示 "再按一次退出程序" ， 用户 两秒内再次点击后退按钮，则退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        监听 android 后退按钮点击事件。
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            1、首先判断当前网页是否还可以进行后退页面的操作，如果可以的话那么就后退网页。
            if (mWebView.canGoBack() &&
                    !Const.BASE_WEB_URL.equals(mWebView.getUrl())) {
                mWebView.goBack();
                return true;
            }
//            2、如果网页已经不可以进行后退操作了（即：网页在首页中，虚拟任务栈中，只包含了 imooc 。）
//          在这种情况下，则会提示 "再按一次退出程序" ， 用户 两秒内再次点击后退按钮，则退出应用
            if (System.currentTimeMillis() - endTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                endTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }
}