package com.ucar.weex.commons.adapter;

import android.app.Application;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.IWXDebugAdapter;

/**
 * Created by chenxi.cui on 2017/7/27.
 */

public class DebugAdapter implements IWXDebugAdapter {
    @Override
    public void initDebug(Application application) {

    }

    @Override
    public View wrapContainer(WXSDKInstance instance, View wxView) {
        return wxView;
    }

    @Override
    public void putDebugOptions(String key, String value) {

    }

    @Override
    public String getDebugOptions(String key) {
        return null;
    }
}
