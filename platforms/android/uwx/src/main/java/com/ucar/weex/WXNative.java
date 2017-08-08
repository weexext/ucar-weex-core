package com.ucar.weex;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ucar.weex.init.UWXConstant;
import com.ucar.weex.init.manager.WXActivityManager;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class WXNative {
    public static void startWXActivity(Activity mActivity ,String module, Bundle initProps) {
        if(TextUtils.isEmpty(module)) {
            module = UWXConstant.MODULE_NAME;
        }
        initProps.putString("module", module);
        WXActivityManager.getInstance().startActivity(mActivity, initProps);
    }
}
