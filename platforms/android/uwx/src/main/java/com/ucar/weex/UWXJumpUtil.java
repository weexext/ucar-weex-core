package com.ucar.weex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.devsup.UWXEnvManager;
import com.ucar.weex.init.UWXPageDataCallback;
import com.ucar.weex.init.activity.UWXTheme;
import com.ucar.weex.init.fragment.UWXBaseFragment;
import com.ucar.weex.init.manager.WXBaseActivityManager;
import com.ucar.weex.init.manager.WXContextManager;
import com.ucar.weex.init.model.UWXBundleInfo;
import com.ucar.weex.init.utils.ArgumentsUtil;

/**
 * Created by chenxi.cui on 2017/7/14.
 *
 *
 * @Override protected void onNewIntent(Intent intent) {
 *             super.onNewIntent(intent);
 *             setIntent(intent);
 * }
 */

public class UWXJumpUtil {

    public static void openPage(Activity activity, String jsPath) {
        openPage(activity, jsPath, (JSONObject) null);
    }

    public static void openPage(Activity activity, String jsPath, Bundle bundle, UWXTheme theme, UWXPageDataCallback callback) {
        JSONObject param = null;
        if (bundle != null) {
            param = ArgumentsUtil.fromBundle(bundle);
        }
        openPage(activity, jsPath, param, theme, callback);
    }

    public static void openPage(Activity activity, String jsPath, Bundle bundle, UWXPageDataCallback callback) {
        openPage(activity, jsPath, bundle, null, callback);
    }

    public static void openPage(Activity activity, String jsPath, Bundle bundle) {
        openPage(activity, jsPath, bundle, null);
    }

    public static void openPage(Activity activity, String jsPath, JSONObject param) {
        openPage(activity, jsPath, param, null);
    }

    public static void openPage(Activity activity, String jsPath, UWXTheme theme) {
        openPage(activity, jsPath, null, theme);
    }

    public static void openPage(Activity activity, String jsPath, JSONObject param, UWXTheme theme) {
        openPage(activity, jsPath, param, theme, null);
    }

    public static void openPage(Activity activity, String jsPath, JSONObject param, UWXTheme theme, UWXPageDataCallback callback) {
        String url = UWXEnvManager.getJSBundleHost() + jsPath;
        openPageByUrl(activity, url, param, theme, callback);
    }

    public static void openPageByUrl(Activity activity, String url, JSONObject param) {
        openPageByUrl(activity, url, param, null);
    }

    public static void openPageByUrl(Activity activity, String url, JSONObject param, UWXTheme theme) {
        openPageByUrl(activity, url, param, theme, null);
    }

    public static void openPageByUrl(Activity activity, String url, JSONObject param, UWXTheme theme, UWXPageDataCallback callback) {
        Bundle bundle = new Bundle();
        UWXBundleInfo info = new UWXBundleInfo();
        info.setUrl(url);
        info.setTheme(theme);
        info.setParam(param);
        bundle.putSerializable(UWXBundleInfo.TAG, info);
        WXContextManager.getInstance().addPageDataCallback(callback);
        UWXNative.startWXActivity(activity, bundle);
    }

    public static void openPage(Activity activity, String jsPath, UWXPageDataCallback callback) {
        openPageByUrl(activity, jsPath, null, null, callback);
    }

    public static void createWXPage(FragmentActivity context, int layoutId, String jsPath, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("_jsBundle", UWXEnvManager.getJSBundleHost() + jsPath);
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment instantiate = Fragment.instantiate(context, UWXBaseFragment.class.getName(), bundle);
        transaction.add(layoutId, instantiate, jsPath);
        transaction.commitAllowingStateLoss();
    }


}
