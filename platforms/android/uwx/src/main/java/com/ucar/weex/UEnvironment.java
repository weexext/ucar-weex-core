package com.ucar.weex;

import android.app.Application;
import android.content.pm.ApplicationInfo;

/**
 * Created by chenxi.cui on 2017/7/26.
 */

public class UEnvironment {

    public static Application sApplication;
    private static boolean isDebug = true;

    public static void init(Application sApplication) {
        UEnvironment.sApplication = sApplication;
    }

    public static Application getsApplication() {
        return sApplication;
    }

    public static boolean isDebug() {
        if (sApplication == null) {
            return false;
        }
        if (!isDebug) {
            return false;
        }
        try {
            ApplicationInfo info = sApplication.getApplicationInfo();
            isDebug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return isDebug;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
