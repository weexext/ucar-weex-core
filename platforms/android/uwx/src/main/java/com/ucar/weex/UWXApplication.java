package com.ucar.weex;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.graphics.Typeface;

/**
 * Created by chenxi.cui on 2017/7/26.
 */

public class UWXApplication {

    public static Application sApplication;
    private static boolean isDebug = true;
    private static Typeface iconFont;

    public static void init(Application sApplication) {
        UWXApplication.sApplication = sApplication;
    }

    public static Application getsApplication() {
        return sApplication;
    }

    public static Typeface getIconFont() {
        if (iconFont == null) {
            synchronized (Typeface.class) {
                if (iconFont == null) {
                    iconFont = Typeface.createFromAsset(sApplication.getAssets(), "iconfont.ttf");
                }
            }
        }
        return iconFont;
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
