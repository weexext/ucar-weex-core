package com.ucar.weex;

import android.app.Application;

import com.ucar.weex.devsup.UWXEnvManager;
import com.ucar.weex.init.model.UWXBundleInfo;
import com.ucar.weex.init.activity.UWXTheme.NavBar;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class UWXSDKManager {
    private static UWXSDKManager sManager;
    private NavBar navBar;
    private String animation;
    private int theme;

    public static UWXSDKManager getInstance() {
        if (sManager == null) {
            synchronized (UWXSDKManager.class) {
                if (sManager == null) {
                    sManager = new UWXSDKManager();
                }
            }
        }
        return sManager;
    }

    private UWXSDKManager() {
        animation = UWXBundleInfo.ANIMATION_DEFAULT;
        theme = R.style.wx_theme_app;
        navBar = new NavBar();
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getTheme() {
        return theme;
    }

    private void setNavBar(NavBar navBar) {
        this.navBar = navBar;
    }

    public NavBar getNavBar() {
        return navBar;
    }

    public static void initialize(Application context) {
        UWXApplication.init(context);
        ActivityListenerInit.init(context);
        UWXEnvManager.initDebugEnvironment(context);
    }

    public String getAnimation() {
        return animation;
    }
}
