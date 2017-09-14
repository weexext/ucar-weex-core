package com.ucar.weex.init.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.UWXJumpUtil;
import com.ucar.weex.init.utils.UWLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class UWXMainActivity extends Activity {

    public UWXMainActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.finish();
        } else {
            Intent intent = this.getIntent();
            Uri uri = intent.getData();
            if (uri == null) {
                this.finish();
            } else {
                String scheme = uri.getScheme();
                String host = uri.getHost();
                String uriQuery = uri.getQuery();
                JSONObject jsonParam = null;
                if (!TextUtils.isEmpty(uriQuery)) {
                    Map<String, Object> queryParam = getUrlParams(uriQuery);
                    if (queryParam != null) {
                        jsonParam = (JSONObject) JSON.toJSON(queryParam);
                    }
                }
                UWLog.v("downloadUrl=" + uri.toString());
                if (!TextUtils.isEmpty(scheme)) {
                    UWXJumpUtil.openPageByUrl(this, uri.toString(), jsonParam);
                } else {
                    Toast.makeText(this, "scheme err!", Toast.LENGTH_SHORT).show();
                }
                this.finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        if (TextUtils.isEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }
}
