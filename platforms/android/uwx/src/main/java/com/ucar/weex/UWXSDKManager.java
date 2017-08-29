package com.ucar.weex;

import android.app.Application;

import com.ucar.weex.appfram.navigator.ActivityNavBarSetterImpl;
import com.ucar.weex.appfram.navigator.UWXActivityNavBarSetter;
import com.ucar.weex.devsup.UWXEnvManager;
import com.ucar.weex.init.manager.WXActivityManagerHelper;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class UWXSDKManager {
    private static UWXSDKManager sManager;
    private UWXActivityNavBarSetter activityNavBarSetter;

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

    public static UWXActivityNavBarSetter getActivityNavBarSetter() {
        return UWXSDKManager.getInstance().activityNavBarSetter;
    }

    public static void setActivityNavBarSetter(UWXActivityNavBarSetter activityNavBarSetter) {
        UWXSDKManager.getInstance().activityNavBarSetter = activityNavBarSetter;
    }

    public static void initialize(Application context) {
        UWXApplication.init(context);
        ActivityListenerInit.init(context);
        WXActivityManagerHelper.init(context);
        UWXSDKManager.setActivityNavBarSetter(new ActivityNavBarSetterImpl());
        UWXEnvManager.initDebugEnvironment(context);
    }

    public static void initialize(Application context, UInitConfig config) {
        UWXApplication.init(context);
        ActivityListenerInit.init(context);
        WXActivityManagerHelper.init(context);
        UWXSDKManager.setActivityNavBarSetter(new ActivityNavBarSetterImpl());
        UWXEnvManager.initDebugEnvironment(context);
    }
}
