package com.ucar.weex.appfram.navigator;

import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.ucar.weex.init.model.WXInfo;
import com.ucar.weex.init.utils.UWLog;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class ActivityNavBarSetterImpl implements UActivityNavBarSetter {
    @Override
    public boolean push(String url, String params) {
        return false;
    }

    @Override
    public boolean pop(int index, String backTag, String params) {
        return false;
    }

    @Override
    public boolean home(String params) {
        return false;
    }

    @Override
    public boolean quit() {
        return false;
    }

    @Override
    public boolean setNavBar(FrameLayout navBarView, WXInfo.NavBar navBar) {
        if (navBar == null) {
            return false;
        }
        UWLog.v("ActivityNavBarSetterImpl",JSON.toJSONString(navBar));
        UNavBar uNavBar = new UNavBar(navBarView.getContext());
        uNavBar.setData(navBar);
        navBarView.removeAllViews();
        navBarView.addView(uNavBar);
        return true;
    }
}
