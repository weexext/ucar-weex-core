package com.ucar.weex.module;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXGlobalEventReceiver;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;
import com.ucar.weex.UWXJumpUtil;
import com.ucar.weex.UWXSDKManager;
import com.ucar.weex.commons.adapter.UWXNavigatorAdapter;
import com.ucar.weex.init.activity.UWXTheme;
import com.ucar.weex.init.activity.UWXThemeManager;
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
    public void pushNative(String encodeParam, JSCallback callback) {
        if (TextUtils.isEmpty(encodeParam)) {
            return;
        }
        try {
            JSONObject options = JSON.parseObject(encodeParam);
            String url = "";
            UWXTheme.NavBar navBar = null;
            JSONObject param = null;
            if (options.containsKey(UWXBundleInfo.KEY_URL)) {
                url = options.getString(UWXBundleInfo.KEY_URL);
            }
            if (options.containsKey(UWXBundleInfo.KEY_NAV_BAR)) {
                String _navBar = options.getString(UWXBundleInfo.KEY_NAV_BAR);
                if (!TextUtils.isEmpty(_navBar)) {
                    navBar = JSON.parseObject(_navBar, UWXTheme.NavBar.class);
                }
            }
            if (options.containsKey(UWXBundleInfo.KEY_PARAM)) {
                param = options.getJSONObject(UWXBundleInfo.KEY_PARAM);
            }
            UWLog.v("params=" + encodeParam);
            if (!TextUtils.isEmpty(url)) {
                UWXNavigatorAdapter navigatorAdapter = UWXSDKManager.getInstance().getNavigatorAdapter();
                if (navigatorAdapter != null) {
                    navigatorAdapter.pushNative((Activity) mWXSDKInstance.getContext(),url ,param);
                    if (callback != null) {
                        callback.invoke(MSG_SUCCESS);
                    }
                }
            } else {
                callback.invoke(MSG_FAILED);
            }
        } catch (Exception e) {
            WXLogUtils.eTag(TAG, e);
            if (callback != null) {
                callback.invoke(MSG_FAILED);
            }
        }
    }

    @JSMethod
    public void push(String encodeParam, JSCallback callback) {
        if (TextUtils.isEmpty(encodeParam)) {
            return;
        }
        try {
            JSONObject options = JSON.parseObject(encodeParam);
            String url = "";
            UWXTheme.NavBar navBar = null;
            JSONObject param = null;
            boolean animated = false;
            if (options.containsKey(UWXBundleInfo.KEY_URL)) {
                url = options.getString(UWXBundleInfo.KEY_URL);
            }
            if (options.containsKey(UWXBundleInfo.KEY_NAV_BAR)) {
                String _navBar = options.getString(UWXBundleInfo.KEY_NAV_BAR);
                if (!TextUtils.isEmpty(_navBar)) {
                    navBar = JSON.parseObject(_navBar, UWXTheme.NavBar.class);
                }
            }
            if (options.containsKey(UWXBundleInfo.KEY_PARAM)) {
                param = options.getJSONObject(UWXBundleInfo.KEY_PARAM);
            }

            if (options.containsKey(UWXBundleInfo.KEY_SCENE_CONFIGS)) {
                animated = options.getBoolean(UWXBundleInfo.KEY_SCENE_CONFIGS);
            }
            UWLog.v("params=" + encodeParam);
            UWXTheme theme = null;
            if (navBar == null) {
                theme = UWXThemeManager.getInstance().getPageTheme();
            } else {
                UWXThemeManager.getInstance().getPageTheme().setNavBar(navBar);
                theme = UWXThemeManager.getInstance().getPageTheme();
            }
            if (!TextUtils.isEmpty(url)) {

                UWXJumpUtil.openPageByUrl((Activity) mWXSDKInstance.getContext(), url, param, theme);
                if (callback != null) {
                    callback.invoke(MSG_SUCCESS);
                }
            } else {
                callback.invoke(MSG_FAILED);
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
        JSONObject options = JSON.parseObject(encodeParam);
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

        if (callback != null) {
            callback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod
    public void quit(JSCallback callback) {
        AppExitUtil.quitApp(mWXSDKInstance.getContext());
        if (callback != null) {
            callback.invoke(MSG_SUCCESS);
        }
    }

}

