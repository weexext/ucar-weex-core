package com.ucar.weex;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.ucar.weex.devsup.WXEnvManager;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/7/14.
 */

public class WXPageHelper {

    /**
     * @param activity
     * @param url      =http://10.99.44.56:12580/dist/page/personnel-application/personnel-application.js
     * @param params   @ApplyCenterParam
     */
    public static void openPage(Activity activity, String url, Serializable params) {
        String encodeParam = null;
        if (params != null) {
            encodeParam = Uri.encode(JSON.toJSONString(params));
        }
        openPage(activity, url, encodeParam);
    }

    /**
     * @param activity
     * @param url         =http://10.99.44.56:12588/dist/page/personnel-application/personnel-application.js
     * @param encodeParam "{'test':'testValue'}"
     */
    public static void openPage(Activity activity, String url, String encodeParam) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("params", encodeParam);
        WXNative.startWXActivity(activity, null, bundle);
    }

    /**
     * @param activity
     * @param url      =http://10.99.44.56:12580/dist/page/personnel-application/personnel-application.js
     */
    public static void openPage(Activity activity, String url) {
        openPage(activity, url, null);
    }

    /**
     * @param context
     * @param path    /page/personnel-application/personnel-application.js
     * @param params  @ApplyCenterParam
     */
    public static void openPageByPath(Activity context, String path, Serializable params) {
        openPage(context, WXEnvManager.getWXHost() + path, params);
    }

    /**
     * @param context
     * @param path    /page/personnel-application/personnel-application.js
     */
    public static void openPageByPath(Activity context, String path) {
        openPage(context, WXEnvManager.getWXHost() + path);
    }
}
