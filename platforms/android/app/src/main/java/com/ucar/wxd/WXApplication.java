package com.ucar.wxd;

import android.app.Application;

import com.ucar.weex.WXInit;

public class WXApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WXInit.init(this);
    }
}
