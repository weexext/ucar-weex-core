package com.ucar.weex.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.taobao.weex.WXGlobalEventReceiver;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;
import com.ucar.weex.UWXPageManger;
import com.ucar.weex.UWXSDKManager;
import com.ucar.weex.init.activity.UWXFrameBaseActivity;
import com.ucar.weex.init.manager.WXActivityManager;
import com.ucar.weex.init.model.UWXBundleInfo;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.utils.AppExitUtil;

/**
 * Created by chenxi.cui on 2017/7/3.
 */

public class UWXNavigatorModule extends WXModule {

    public static final String MSG_SUCCESS = "WX_SUCCESS";
    public static final String MSG_FAILED = "WX_FAILED";
    private final static String TAG = "UWXNavigatorModule";

    @JSMethod
    public void push(String encodeParam, JSCallback callback) {
        if (TextUtils.isEmpty(encodeParam)) {
            return;
        }
        try {
            com.alibaba.fastjson.JSONObject options = JSON.parseObject(encodeParam);
            String url = "";
            if (options.containsKey("url")) {
                url = options.getString("url");
            }
            UWLog.v("url=" + url + "params=" + encodeParam);

            if (!TextUtils.isEmpty(url)) {
                if (UWXSDKManager.getActivityNavBarSetter() != null) {
                    if (UWXSDKManager.getActivityNavBarSetter().push(url, encodeParam)) {
                        if (callback != null) {
                            callback.invoke(MSG_SUCCESS);
                        }
                        return;
                    }
                }
                UWXPageManger.openPageByUrl((Activity) mWXSDKInstance.getContext(), url, encodeParam);
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
    public void pop(String encodeParam, JSCallback callback) {
        if (TextUtils.isEmpty(encodeParam)) {
            return;
        }
        com.alibaba.fastjson.JSONObject options = JSON.parseObject(encodeParam);
        int index = 0;
        String tagCode = "";
        String param = "";
        if (options.containsKey("index")) {
            index = options.getInteger("index");
        }
        if (options.containsKey("tagCode")) {
            tagCode = options.getString("tagCode");
        }
        if (options.containsKey("param")) {
            param = options.getString("param");
        }

        if (UWXSDKManager.getActivityNavBarSetter() != null) {
            if (UWXSDKManager.getActivityNavBarSetter().pop(index, tagCode, encodeParam)) {
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
                return;
            }
        }
        Activity activity = (Activity) mWXSDKInstance.getContext();
        if (mWXSDKInstance.getContext() instanceof Activity) {
            if (TextUtils.isEmpty(param)) {
                ((Activity) mWXSDKInstance.getContext()).finish();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("params", encodeParam);
                bundle.putString("backTag", tagCode);
                WXActivityManager.getInstance().backToActivity(activity, index, bundle);
                //发出全局广播
                Intent eventIntent = new Intent(WXGlobalEventReceiver.EVENT_ACTION);
                eventIntent.putExtra(WXGlobalEventReceiver.EVENT_NAME, tagCode);
                eventIntent.putExtra(WXGlobalEventReceiver.EVENT_PARAMS, encodeParam);
                mWXSDKInstance.getContext().sendBroadcast(eventIntent);
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
            }
        }
    }

    @JSMethod
    public void home(String params, JSCallback callback) {
        if (UWXSDKManager.getActivityNavBarSetter() != null) {
            if (UWXSDKManager.getActivityNavBarSetter().home(params)) {
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
        if (UWXSDKManager.getActivityNavBarSetter() != null) {
            if (UWXSDKManager.getActivityNavBarSetter().quit()) {
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
            if (context instanceof UWXFrameBaseActivity) {
                UWXFrameBaseActivity baseActivity = (UWXFrameBaseActivity) context;
                FrameLayout flNavBar = baseActivity.getFlNavBar();
                if (flNavBar != null) {
                    UWXBundleInfo.NavBar navBar = JSON.parseObject(navBarParam, UWXBundleInfo.NavBar.class);
                    boolean b = UWXSDKManager.getActivityNavBarSetter().setNavBar(flNavBar, navBar);
                    if (callback != null) {
                        if (b) {
                            callback.invoke(MSG_SUCCESS);
                        } else {
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

