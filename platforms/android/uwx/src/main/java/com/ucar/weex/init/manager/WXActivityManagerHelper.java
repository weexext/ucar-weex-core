package com.ucar.weex.init.manager;

import android.app.Activity;
import android.app.Application;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

public class WXActivityManagerHelper {
    public WXActivityManagerHelper() {
    }

    public static void init(Application application) {
        WXContextManager.getInstance().init(application);
    }


    public static void onActivityDestroy(Activity activity) {
        WXActivityManager.getInstance().onActivityDestroy(activity);
    }
}
