package com.ucar.weex.module;

import android.content.Intent;

import com.taobao.weex.WXGlobalEventReceiver;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by chenxi.cui on 2017/8/7.
 */

public class UWXGlobalEventModule extends WXModule {
    @JSMethod
    public void sendGlobalEvent(String eventName, String params) {
        Intent intent = new Intent(WXGlobalEventReceiver.EVENT_ACTION);
        intent.putExtra(WXGlobalEventReceiver.EVENT_NAME, eventName);
        intent.putExtra(WXGlobalEventReceiver.EVENT_PARAMS, params);
        mWXSDKInstance.getContext().sendBroadcast(intent);
    }
}
