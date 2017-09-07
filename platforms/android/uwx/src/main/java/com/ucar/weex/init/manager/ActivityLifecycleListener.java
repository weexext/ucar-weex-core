package com.ucar.weex.init.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.ucar.weex.init.utils.UWLog;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

@TargetApi(14)
public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = "ActivityLifecycleTAG";
    private boolean isNewActivity;
    private boolean isDisActivity = true;

    public ActivityLifecycleListener() {
    }


    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.isNewActivity = false;
        if (savedInstanceState == null) {
            this.isNewActivity = true;
            WXContextManager.getInstance().recordContext(activity.getClass());
        }
        UWLog.v(TAG, activity.getClass().getSimpleName() + "\nonActivityCreated\n" + isNewActivity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        UWLog.v(TAG, activity.getClass().getSimpleName() + "\nonActivityStarted");
    }

    public void onActivityResumed(Activity activity) {
        if (!isNewActivity) {
            isDisActivity = true;
            WXContextManager.getInstance().receiveBack(activity);
        }
        UWLog.v(TAG, activity.getClass().getSimpleName() + "\nonActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        this.isNewActivity = false;
        UWLog.v(TAG, activity.getClass().getSimpleName() + "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        UWLog.v(TAG, activity.getClass().getSimpleName() + "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        UWLog.v(TAG, activity.getClass().getSimpleName() + "\nonActivitySaveInstanceState\n" + (outState == null));
        isDisActivity = false;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        UWLog.v(TAG, activity.getClass().getSimpleName() + "\nonActivityDestroyed");
        if (isDisActivity) {
            WXContextManager.getInstance().removeContext(activity.getClass());
        }
        isDisActivity = true;
    }
}
