package com.ucar.weex.init.model;

import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.init.activity.UWXTheme;

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

    private String module;
    private String url;

    private UWXTheme theme;
    private JSONObject param;
    private String animation;
    public UWXTheme getTheme() {
        return theme;
    }

    public void setTheme(UWXTheme theme) {
        this.theme = theme;
    }
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


    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
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

}
