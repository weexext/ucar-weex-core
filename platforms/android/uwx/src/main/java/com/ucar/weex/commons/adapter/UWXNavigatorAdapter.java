package com.ucar.weex.commons.adapter;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chenxi.cui on 2017/9/21.
 */

public interface UWXNavigatorAdapter {
    void pushNative(Activity context, String pageName, JSONObject param);
}
