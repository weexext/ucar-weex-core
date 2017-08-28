package com.ucar.weex.init.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.ucar.weex.UWXPageManger;
import com.ucar.weex.init.utils.UWLog;

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
                UWLog.v("url=" + uri.toString());
                if (!TextUtils.isEmpty(scheme)) {
                    UWXPageManger.openPageByUrl(this, uri.toString(), null);
                } else {
                    Toast.makeText(this, "scheme err!", Toast.LENGTH_SHORT).show();
                }
                this.finish();
            }
        }
    }
}
