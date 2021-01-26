package com.fyb.x5webdemo.jsInterface;


import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


public class MyJavaScriptInterface {

    private Context mContext;


    public MyJavaScriptInterface(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void hello(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

}
