package com.ucar.wxd;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.ucar.weex.UWXPageManager;
import com.ucar.weex.init.model.UWXBundleInfo;
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
                startWeexPage();
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void startWeexPage() {
        UWXPageManager.openPage(this, "index.js", new UWXBundleInfo.NavBar("#ff99cc00", "#ffff4444"));
//        Bundle bundle = new Bundle();
//        bundle.putString("key", "value");
//        bundle.putString("name", "zhangsan");
//        UWXPageManager.createWXPage(this, R.id.container, "index.js", bundle );
//        finish();
    }
}
