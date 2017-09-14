package com.ucar.wxd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.UWXJumpUtil;
import com.ucar.weex.init.UWXPageDataCallback;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.update.FileUtils;
import com.ucar.weex.update.UWXResManager;
import com.ucar.weex.update.WXPackageInfo;
import com.ucar.wex.R;

public class NativeActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.native_activity);
        String param = bundle.getString("param");

//        String param = savedInstanceState.getString("param");
        TextView textView = (TextView) findViewById(R.id.tv_param);
//        textView.setText(param);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWeexPage();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void startWeexPage() {
        //  UWXTheme theme = new UWXTheme(new UWXTheme.NavBar("#ff99cc00", "#ffff4444"));
        Bundle bundle = new Bundle();
        bundle.putString("mainkey", "mainValue");
        UWXJumpUtil.openPage(this, "custom/wxtonavtive.js", bundle, null, new UWXPageDataCallback() {
            @Override
            public void callBack(String backTag, JSONObject jsonObject) {
                UWLog.v("MainActivity", "backTag=" + backTag);
                UWLog.v("MainActivity", "jsonObject=" + JSON.toJSON(jsonObject));
            }
        });
//        Bundle bundle = new Bundle();
//        bundle.putString("key", "value");
//        bundle.putString("name", "zhangsan");
//        UWXJumpUtil.createWXPage(this, R.id.container, "index.js", bundle );
        finish();
    }
}
