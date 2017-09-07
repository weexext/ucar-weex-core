package com.ucar.wxd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.UWXJumpUtil;
import com.ucar.weex.init.UWXPageDataCallback;
import com.ucar.weex.init.activity.UWXTheme;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.update.FileUtils;
import com.ucar.weex.update.UWXResManager;
import com.ucar.weex.update.WXPackageInfo;
import com.ucar.wex.R;

public class MainActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                startWeexPage();
            }
        }, 1000);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UWXResManager.getInstance().checkUpdate(new UWXResManager.CheckUpdateCallback() {
                    @Override
                    public void callback(int code, String msg, WXPackageInfo info) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                        UWLog.d("WXApp", msg);
                        //重启 提示
                    }
                });
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UWXResManager.getInstance().addWXResFromAssert(MainActivity.this, FileUtils.getWXPackageFileName(MainActivity.this, "weex"));
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
        UWXTheme theme = new UWXTheme(new UWXTheme.NavBar("#ff99cc00", "#ffff4444"));
        Bundle bundle = new Bundle();
        bundle.putString("mainkey", "mainValue");
        UWXJumpUtil.openPage(this, "index.js", bundle, theme, new UWXPageDataCallback() {
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
//        finish();
    }
}
