package com.ucar.weex;

import com.ucar.weex.appfram.navigator.UActivityNavBarSetter;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class USDKManager {
    private static USDKManager sManager;
    private static UActivityNavBarSetter activityNavBarSetter;

    public static USDKManager getInstance() {
        if (sManager == null) {
            synchronized (USDKManager.class) {
                if (sManager == null) {
                    sManager = new USDKManager();
                }
            }
        }
        return sManager;
    }

    public static UActivityNavBarSetter getActivityNavBarSetter() {
        return activityNavBarSetter;
    }

    public static void setActivityNavBarSetter(UActivityNavBarSetter activityNavBarSetter) {
        USDKManager.activityNavBarSetter = activityNavBarSetter;
    }
}
