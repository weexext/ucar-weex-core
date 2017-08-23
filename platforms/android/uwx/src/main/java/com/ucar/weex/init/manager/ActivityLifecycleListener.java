package com.ucar.weex.init.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

@TargetApi(14)
public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
    private WXActivityManager wxActivityManager = WXActivityManager.getInstance();
    private boolean isNewActivity;

    public ActivityLifecycleListener() {
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.isNewActivity = false;
        if(savedInstanceState == null) {
            this.isNewActivity = true;
            this.wxActivityManager.onStartNewActivity(activity);
        } else {
            this.wxActivityManager.onBackToExistActivity(activity, true);
        }

        Log.e("wt>activityLifecycle", "onActivityCreated:" + activity.getLocalClassName());
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        if(!this.isNewActivity) {
            this.wxActivityManager.onBackToExistActivity(activity, false);
        }

        this.isNewActivity = false;
        Log.e("wt>activityLifecycle", "onActivityResumed");
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
