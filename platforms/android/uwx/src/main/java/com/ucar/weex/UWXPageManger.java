package com.ucar.weex;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.devsup.UWXEnvManager;
import com.ucar.weex.init.fragment.UWXBaseFragment;
import com.ucar.weex.init.utils.ArgumentsUtil;

/**
 * Created by chenxi.cui on 2017/7/14.
 */

public class UWXPageManger {

    /**
     * @param activity
     * @param url      =http://10.99.44.56:12588/dist/page/personnel-application/personnel-application.js
     * @param params
     */
    private static void openPageByUrl(Activity activity, String url, JSONObject params) {
        String encodeParam = null;
        if (params != null) {
            encodeParam = Uri.encode(JSON.toJSONString(params));
        }
        openPageByUrl(activity, url, encodeParam);
    }

    /**
     * @param activity
     * @param url
     * @param encodeParam
     */
    public static void openPageByUrl(Activity activity, String url, String encodeParam) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        if (!TextUtils.isEmpty(encodeParam)) {
            bundle.putString("params", encodeParam);
        }
        UWXNative.startWXActivity(activity, bundle);
    }

    /**
     * @param context
     * @param jsBundle /page/personnel-application.js
     * @param bundle   @ApplyCenterParam
     */
    public static void openPage(Activity context, String jsBundle, Bundle bundle) {
        JSONObject jsonObject = null;
        if (bundle != null) {
            jsonObject = ArgumentsUtil.fromBundle(bundle);
        }
        openPageByUrl(context, UWXEnvManager.getJSBundleHost() + jsBundle, jsonObject);
    }

    /**
     * @param context
     * @param jsBundle /page//personnel-application.js
     */
    public static void openPage(Activity context, String jsBundle) {
        openPage(context, jsBundle, null);
    }

    /**
     * @param context
     * @param layoutId
     * @param jsBundle
     * @param bundle
     */
    public static void createWXPage(FragmentActivity context, int layoutId, String jsBundle, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("_jsBundle", UWXEnvManager.getJSBundleHost() + jsBundle);
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment instantiate = Fragment.instantiate(context, UWXBaseFragment.class.getName(), bundle);
        transaction.add(layoutId, instantiate, jsBundle);
        transaction.commitAllowingStateLoss();
    }
}
