package com.ucar.weex;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ucar.weex.init.UWXConstant;
import com.ucar.weex.init.manager.WXActivityManager;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class UWXNative {
    public static void startWXActivity(Activity mActivity ,String module, Bundle bundle) {
        if(TextUtils.isEmpty(module)) {
            module = UWXConstant.MODULE_NAME;
        }
        bundle.putString("module", module);
        WXActivityManager.getInstance().startActivity(mActivity, bundle);
    }
    public static void startWXActivity(Activity mActivity , Bundle bundle) {
        bundle.putString("module", UWXConstant.MODULE_NAME);
        WXActivityManager.getInstance().startActivity(mActivity, bundle);
    }
}
