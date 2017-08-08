package com.ucar.weex.module;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;
import com.ucar.weex.USDKManager;
import com.ucar.weex.WXPageHelper;
import com.ucar.weex.init.activity.WXFrameBaseActivity;
import com.ucar.weex.init.manager.WXActivityManager;
import com.ucar.weex.init.model.WXInfo;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.utils.AppExitUtil;

/**
 * Created by chenxi.cui on 2017/7/3.
 */

public class UNavigatorModule extends WXModule {

    public static final String MSG_SUCCESS = "WX_SUCCESS";
    public static final String MSG_FAILED = "WX_FAILED";
    private final static String TAG = "UNavigatorModule";

    @JSMethod
    public void push(String url, String params, JSCallback callback) {
        UWLog.v("url=" + url + "params=" + params);
        try {
            if (!TextUtils.isEmpty(url)) {
                if (USDKManager.getActivityNavBarSetter() != null) {
                    if (USDKManager.getActivityNavBarSetter().push(url, params)) {
                        if (callback != null) {
                            callback.invoke(MSG_SUCCESS);
                        }
                        return;
                    }
                }
                WXPageHelper.openPage((Activity) mWXSDKInstance.getContext(), url, params);
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
            }
        } catch (Exception e) {
            WXLogUtils.eTag(TAG, e);
            if (callback != null) {
                callback.invoke(MSG_FAILED);
            }
        }
    }

    @JSMethod
    public void pop(int index,String backTag , String params, JSCallback callback) {
        if (USDKManager.getActivityNavBarSetter() != null) {
            if (USDKManager.getActivityNavBarSetter().pop(index,backTag, params)) {
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
                return;
            }
        }
        Activity activity = (Activity) mWXSDKInstance.getContext();
        if (mWXSDKInstance.getContext() instanceof Activity) {
            if (TextUtils.isEmpty(params)) {
                ((Activity) mWXSDKInstance.getContext()).finish();
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("params", params);
                bundle.putString("backTag", backTag);
                WXActivityManager.getInstance().backToActivity(activity, index, bundle);
//            Intent eventIntent = new Intent(WXGlobalEventReceiver.EVENT_ACTION);
//            eventIntent.putExtra(WXGlobalEventReceiver.EVENT_NAME, backCode);
//            eventIntent.putExtra(WXGlobalEventReceiver.EVENT_PARAMS, params);
//            mWXSDKInstance.getContext().sendBroadcast(eventIntent);
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
            }
        }
    }

    @JSMethod
    public void home(String params, JSCallback callback) {
        if (USDKManager.getActivityNavBarSetter() != null) {
            if (USDKManager.getActivityNavBarSetter().home(params)) {
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
                return;
            }
        }

        if (callback != null) {
            callback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod
    public void quit(JSCallback callback) {
        if (USDKManager.getActivityNavBarSetter() != null) {
            if (USDKManager.getActivityNavBarSetter().quit()) {
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
                return;
            }
        }
        AppExitUtil.quitApp(mWXSDKInstance.getContext());
        if (callback != null) {
            callback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod
    public void setNavBar(String navBarParam, JSCallback callback) {
        if (!TextUtils.isEmpty(navBarParam)) {
            Context context = mWXSDKInstance.getContext();
            if (context instanceof WXFrameBaseActivity) {
                WXFrameBaseActivity baseActivity = (WXFrameBaseActivity) context;
                FrameLayout flNavBar = baseActivity.getFlNavBar();
                if (flNavBar != null) {
                    WXInfo.NavBar navBar = JSON.parseObject(navBarParam, WXInfo.NavBar.class);
                    boolean b = USDKManager.getActivityNavBarSetter().setNavBar(flNavBar, navBar);
                    if (callback != null) {
                        if (b) {
                            callback.invoke(MSG_SUCCESS);
                        }else {
                            callback.invoke(MSG_FAILED);
                        }
                    }
                    return;
                }
            }

        }
        if (callback != null) {
            callback.invoke(MSG_FAILED);
        }
    }
}

