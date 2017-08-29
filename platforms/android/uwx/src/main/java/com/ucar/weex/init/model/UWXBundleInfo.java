package com.ucar.weex.init.model;

import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

public class UWXBundleInfo implements Serializable {
    public static final String TAG = UWXBundleInfo.class.getSimpleName();
    public static final String KEY_PARAM = "param";
    public static final String KEY_URL = "url";
    public static final String KEY_NAV_BAR = "navBar";
    public static final String KEY_SCENE_CONFIGS = "animated";
    public static final String ANIMATION_VERTICAL = "vertical";
    public static final String ANIMATION_HORIZONTAL = "horizontal";
    public static final String ANIMATION_DEFAULT = "horizontal";
    public static NavBar defaultNavBar = new NavBar();


    private String module;
    private String url;
    private NavBar navBar;
    private JSONObject param;
    private String animation;

    public JSONObject getParam() {
        return param;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UWXBundleInfo() {

    }

    public String getAnimation() {
        return animation;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public void setNavBar(NavBar navBar) {
        this.navBar = navBar;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public NavBar getNavBar() {
        return navBar;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlParam() {
        String tempUrl;
        if (param != null) {
            if (url.contains("?")) {
                tempUrl = url + "&params=" + Uri.encode(JSON.toJSONString(param));
            } else {
                tempUrl = url + "?params=" + Uri.encode(JSON.toJSONString(param));
            }
        } else {
            tempUrl = url;
        }
        return tempUrl;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    /**
     * //导航栏是否显示返回按钮
     * hasBack: true,
     * // 导航栏返回按钮颜色
     * backColor: '#ffffff',
     * // 导航栏背景
     * navBarColor: '3e50b5',
     * // [全局/页面]背景色，默认 蓝
     * backgroundColor: '#3e50b5',
     * // [全局/页面]左侧按钮文字，默认 '返回'
     * leftButtonText: '返回',
     * // 导航栏高度
     * height: weex.config.env.platform == 'android' ? 100.0 : 64.0
     */
    public static class NavBar implements Serializable {
        public boolean hasBack = true;
        public String backColor = "#ffffff";
        public String leftButtonText = "返回";
        public String navBarColor = "#3e50b5";
        public String backgroundColor = "#ffffff";
        public float height = 100;

        public NavBar(boolean hasBack, String backColor, String leftButtonText, String navBarColor, String backgroundColor, float height) {
            this.hasBack = hasBack;
            this.backColor = backColor;
            this.leftButtonText = leftButtonText;
            this.navBarColor = navBarColor;
            this.backgroundColor = backgroundColor;
            this.height = height;
        }

        public NavBar() {
        }

        public NavBar(String backColor, String navBarColor) {
            this(true, backColor, "返回", navBarColor, "#ffffff", 100);
        }
    }
}
