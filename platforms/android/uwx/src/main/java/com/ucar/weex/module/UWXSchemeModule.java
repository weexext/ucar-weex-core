package com.ucar.weex.module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.ucar.weex.init.utils.ArgumentsUtil;


/**
 * Created by chenxi.cui on 2017/8/7.
 */

public class UWXSchemeModule extends WXModule {
    private int mRequestCode = 110;
    private JSCallback callback;

    @Override
    public String getModuleName() {
        return "UScheme";
    }

    @JSMethod
    public void sendScheme(String url, JSONObject params, JSCallback callback) {
        this.callback = callback;
        Activity activity = (Activity) mWXSDKInstance.getContext();
//        SchemeDispatcher.sendScheme(context, downloadUrl, true);
        ++this.mRequestCode;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        Bundle bundle = ArgumentsUtil.fromJsonToBundle(params);
        if (bundle == null) {
            bundle = new Bundle();
        }
        intent.setFlags(65536);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, mRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode) {
            if (callback != null) {
//                Map<String, Object> result = new HashMap<>();
//                result.put("content", ArgumentsUtil.fromBundle(content.getExtras()));
                callback.invoke(ArgumentsUtil.fromBundle(data.getExtras()));
            }
        }
    }
}
