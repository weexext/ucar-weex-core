package com.ucar.weex.appfram.navigator;

import android.widget.FrameLayout;

import com.ucar.weex.init.model.WXInfo;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public interface UActivityNavBarSetter {

    boolean push(String url, String params);

    boolean pop(int index, String backTag, String params);

    boolean home(String params);

    boolean quit();

    boolean setNavBar(FrameLayout navBarView, WXInfo.NavBar navBar);

}
