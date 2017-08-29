package com.ucar.weex.init.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.UWXPageManager;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.utils.ArrayUtils;

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
                    String[] split = uriQuery.split("&");
                    Map<Object, Object> queryParam = ArrayUtils.toMap(split);
                    if (queryParam != null) {
                        jsonParam = (JSONObject) JSON.toJSON(queryParam);
                    }
                }
                UWLog.v("url=" + uri.toString());
                if (!TextUtils.isEmpty(scheme)) {
                    UWXPageManager.openPageByUrl(this, uri.toString(), jsonParam);
                } else {
                    Toast.makeText(this, "scheme err!", Toast.LENGTH_SHORT).show();
                }
                this.finish();
            }
        }
    }
}
