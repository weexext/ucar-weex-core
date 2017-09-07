package com.ucar.weex.init;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chenxi.cui on 2017/9/7.
 */

public interface UWXPageDataCallback {
    void callBack(String  backTag, JSONObject jsonObject);
}
