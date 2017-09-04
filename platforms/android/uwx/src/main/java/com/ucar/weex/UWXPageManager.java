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
import com.ucar.weex.init.activity.UWXMainActivity;
import com.ucar.weex.init.fragment.UWXBaseFragment;
import com.ucar.weex.init.model.UWXBundleInfo;
import com.ucar.weex.init.utils.ArgumentsUtil;

/**
 * Created by chenxi.cui on 2017/7/14.
 */

public class UWXPageManager {

    public static void openPage(Activity activity, String jsPath) {
        openPage(activity, jsPath, (JSONObject) null);
    }

    public static void openPage(Activity activity, String jsPath, Bundle bundle) {
        JSONObject param = null;
        if (bundle != null) {
            param = ArgumentsUtil.fromBundle(bundle);
        }
        openPage(activity, jsPath, param, null);
    }

    public static void openPage(Activity activity, String jsPath, JSONObject param) {
        openPage(activity, jsPath, param, null);
    }

    public static void openPage(Activity activity, String jsPath, UWXBundleInfo.NavBar navBar) {
        openPage(activity, jsPath, null, navBar);
    }
    public static void openPage(Activity activity, String jsPath, JSONObject param, UWXBundleInfo.NavBar navBar) {
        openPage(activity, jsPath, param, navBar, UWXBundleInfo.ANIMATION_DEFAULT);
    }

    public static void openPage(Activity activity, String jsPath, JSONObject param, UWXBundleInfo.NavBar navBar, String animation) {
        String url = UWXEnvManager.getJSBundleHost() + jsPath;
        openPageByUrl(activity, url, param, navBar, animation);
    }

    public static void openPageByUrl(Activity activity, String url, JSONObject param) {
        openPageByUrl(activity, url, param, null, UWXBundleInfo.ANIMATION_DEFAULT);
    }
    public static void openPageByUrl(Activity activity, String url, JSONObject param, UWXBundleInfo.NavBar navBar, String animation) {
        Bundle bundle = new Bundle();
        UWXBundleInfo info = new UWXBundleInfo();
        info.setUrl(url);
        info.setNavBar(navBar);
        info.setParam(param);
        info.setAnimation(animation);
        bundle.putSerializable(UWXBundleInfo.TAG, info);
        UWXNative.startWXActivity(activity, bundle);
    }
    public static void createWXPage(FragmentActivity context, int layoutId, String jsPath, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("_jsPath", UWXEnvManager.getJSBundleHost() + jsPath);
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment instantiate = Fragment.instantiate(context, UWXBaseFragment.class.getName(), bundle);
        transaction.add(layoutId, instantiate, jsPath);
        transaction.commitAllowingStateLoss();
    }


}
