package com.ucar.wxd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by chenxi.cui on 2017/9/11.
 */

public class SchemaActivity extends WXModule {
    @JSMethod
    public void push(String path, String param) {
        if ("nativeA".equals(path)) {
            Context context = (Activity) mWXSDKInstance.getContext();
            Intent intent = new Intent();
            intent.setClass(context, NativeActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putString("param", param);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
