package com.ucar.weex.init.manager;

import android.app.Activity;
import android.os.Bundle;

import com.ucar.weex.init.utils.Assertions;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class WXActivityManager extends WXBaseActivityManager {
    private static WXActivityManager ourInstance = new WXActivityManager();

    public static WXActivityManager getInstance() {
        return ourInstance;
    }

    private WXActivityManager() {
    }

    public void backToActivity(Activity activity, int index) {
        this.backToActivity(activity, index, (Bundle)null);
    }

    public void backToActivity(Activity activity, int index, Bundle bundle) {
        Class aClass = WXContextManager.getInstance().solveReactHybridIDAndIndex(index);
        Assertions.assertNotNull(aClass, "Can not find this activity in activity stack");
        super.backToActivity(activity, aClass, bundle);
    }
}
