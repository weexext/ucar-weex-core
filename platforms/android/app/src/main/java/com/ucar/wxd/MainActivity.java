package com.ucar.wxd;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.ucar.weex.UWXPageManger;
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
        },1000);

    }

    private void startWeexPage() {
        UWXPageManger.openPage(this, "index.js");
//        Bundle bundle = new Bundle();
//        bundle.putString("key", "value");
//        bundle.putString("name", "zhangsan");
//        UWXPageManger.createWXPage(this, R.id.container, "index.js", bundle );
        finish();
    }
}
