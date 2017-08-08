package com.ucar.weex.init.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

public class WXInfo implements Serializable {
    public static final String KEY_PARAM = "param";
    public static final String KEY_NAV_BAR = "navBar";
    public static final String KEY_SCENE_CONFIGS = "sceneConfigs";
    public static final String SCENE_CONFIGS_VERTICAL = "vertical";
    public static final String SCENE_CONFIGS_HORIZONTAL = "horizontal";
    public static final String SCENE_CONFIGS_DEFALT = "horizontal";

    private String module;
    private String url;
    private NavBar navBar;
    private String params;
    private String param;
    private String sceneConfigs;

    public String getParam() {
        return param;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WXInfo() {

    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public String getParams() {
        return params;
    }

    public String getSceneConfigs() {
        return sceneConfigs;
    }

    public NavBar getNavBar() {
        // TODO: 2017/7/31 需要删除
        if (this.navBar == null) {
            try {
                this.navBar = JSON.parseObject("{\"navBarBackground\":\"#385198\",\"hasBack\":true,\"height\":\"100\"}", NavBar.class);
            } catch (JSONException e) {
                return null;
            }
        }
        return navBar;
    }

    public void setParams(String params) {
        this.params = params;
        if (!TextUtils.isEmpty(params)) {
            JSONObject jsParams = JSON.parseObject(params);
            if (jsParams != null && jsParams.containsKey(KEY_NAV_BAR)) {
                try {
                    this.navBar = JSON.parseObject(JSON.toJSONString(jsParams.get(KEY_NAV_BAR)), NavBar.class);
                } catch (JSONException e) {

                }
            }
            if (jsParams != null && jsParams.containsKey(KEY_PARAM)) {
                try {
                    this.param = JSON.toJSONString(jsParams.get(KEY_PARAM));
                } catch (JSONException e) {

                }
            }
            if (jsParams != null && jsParams.containsKey(KEY_SCENE_CONFIGS)) {
                try {
                    this.sceneConfigs = JSON.toJSONString(jsParams.get(KEY_SCENE_CONFIGS));
                } catch (JSONException e) {

                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUrlParam() {
        String tempUrl;
        if (!TextUtils.isEmpty(param)) {
            if (url.contains("?")) {
                tempUrl = url + "&params=" + param;
            } else {
                tempUrl = url + "?params=" + param;
            }
        } else {
            tempUrl = url;
        }
        return tempUrl;
    }

    /**
     *
     */
    public static class NavBar implements Serializable {
        public String navBarBackground = "#ffffff";
        public boolean hasBack = true;
        public float height = 100;
    }
}
