package com.ucar.wxd;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ucar.weex.WXPageHelper;
import com.ucar.weex.devsup.DebugUtil;
import com.ucar.wex.R;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        WXPageHelper.openPageByPath(this, "index.js");
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DebugUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        DebugUtil.onPause(this);
//        super.onPause();
    }
}
