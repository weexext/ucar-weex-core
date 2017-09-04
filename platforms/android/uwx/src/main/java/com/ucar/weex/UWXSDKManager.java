package com.ucar.weex;

import android.app.Application;

import com.ucar.weex.devsup.UWXEnvManager;
import com.ucar.weex.init.manager.WXActivityManagerHelper;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class UWXSDKManager {
    private static UWXSDKManager sManager;

    public static UWXSDKManager getInstance() {
        if (sManager == null) {
            synchronized (UWXSDKManager.class) {
                if (sManager == null) {
                    sManager = new UWXSDKManager();
                }
            }
        }
        return sManager;
    }

    public static void initialize(Application context) {
        UWXApplication.init(context);
        ActivityListenerInit.init(context);
        WXActivityManagerHelper.init(context);
        UWXEnvManager.initDebugEnvironment(context);
    }
}
