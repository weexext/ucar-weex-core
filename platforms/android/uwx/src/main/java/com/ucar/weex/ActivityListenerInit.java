package com.ucar.weex;

import android.app.Application;
import android.os.Build;

import com.ucar.weex.init.manager.ActivityLifecycleListener;
import com.ucar.weex.init.manager.WXActivityManagerHelper;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

public class ActivityListenerInit {
    private static boolean hasInited = false;

    public ActivityListenerInit() {
    }

    public static synchronized void init(Application application) {
        if (!hasInited) {
            if (Build.VERSION.SDK_INT >= 14) {
                application.registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
            }
            hasInited = true;
        }

    }
}
