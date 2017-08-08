package com.ucar.weex.devsup;

import android.content.Context;

import com.taobao.weex.WXEnvironment;
import com.ucar.weex.UEnvironment;

/**
 * Created by chenxi.cui on 2017/6/29.
 */

public class WXEnvManager {

    public static String getWXHost() {
        WXEnvDetailHelper instance = WXEnvDetailHelper.getInstance();
        if (instance.isLocalServer() || !UEnvironment.isDebug()) {
            return "file://assets/weex/jsbundle/";
        }
        return "http://" + instance.getHost() + ":" + instance.getPort() + "/dist/native/";
    }

    public static void initDebugEnvironment(Context context) {
        if (UEnvironment.isDebug()) {
            WXEnvDetailHelper instance = WXEnvDetailHelper.getInstance();
            WXEnvironment.sDebugServerConnectable = WXEnvDetailHelper.getInstance().debugInChrome();
            WXEnvironment.sRemoteDebugMode = WXEnvDetailHelper.getInstance().debugRemote();
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + instance.getHost() + ":8088/debugProxy/native";
        } else {
            WXEnvironment.sDebugServerConnectable = false;
            WXEnvironment.sRemoteDebugMode = false;
        }
    }


}
