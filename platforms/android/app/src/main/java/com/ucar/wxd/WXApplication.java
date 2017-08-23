package com.ucar.wxd;

import android.app.Application;

import com.ucar.weex.UWXInit;

/**
 * weex 初始化
 */
public class WXApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //ucar weex 初始化
        UWXInit.init(this);

    }
}
