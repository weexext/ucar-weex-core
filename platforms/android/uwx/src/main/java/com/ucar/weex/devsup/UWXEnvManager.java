package com.ucar.weex.devsup;

import android.content.Context;

import com.taobao.weex.WXEnvironment;
import com.ucar.weex.UWXApplication;
import com.ucar.weex.update.UWXResManager;

/**
 * Created by chenxi.cui on 2017/6/29.
 */

public class UWXEnvManager {

    public static String getJSBundleHost() {
        WXEnvDetailHelper instance = WXEnvDetailHelper.getInstance();
        if (instance.isLocalServer() || !UWXApplication.isDebug()) {
            return "file://" + UWXResManager.getInstance().getWXResPath() + "jsBundle/views/";
//            return "file://assets/weex/jsbundle/views/";
        }
        return "http://" + instance.getHost() + ":" + instance.getPort() + "/dist/native/views/";
    }

    public static String getWXResHost() {
        WXEnvDetailHelper instance = WXEnvDetailHelper.getInstance();
        if (instance.isLocalServer() || !UWXApplication.isDebug()) {
            return "file://" + UWXResManager.getInstance().getWXResPath() + "res";
        }
        return "http://" + instance.getHost() + ":" + instance.getPort() + "/src/assets";
    }

    public static void initDebugEnvironment(Context context) {
        if (UWXApplication.isDebug()) {
            WXEnvDetailHelper instance = WXEnvDetailHelper.getInstance();
            WXEnvironment.sDebugServerConnectable = WXEnvDetailHelper.getInstance().debugInChrome();
            WXEnvironment.sRemoteDebugMode = WXEnvDetailHelper.getInstance().debugRemote();
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + instance.getHost() + ":8088/debugProxy/native";
            WXEnvironment.sLogLevel = WXEnvDetailHelper.getInstance().getLogLevel();
        } else {
            WXEnvironment.sDebugServerConnectable = false;
            WXEnvironment.sRemoteDebugMode = false;
        }
    }


}
