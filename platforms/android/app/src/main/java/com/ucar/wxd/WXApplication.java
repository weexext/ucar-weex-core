package com.ucar.wxd;

import android.app.Application;
import android.widget.Toast;

import com.ucar.weex.UWXInit;
import com.ucar.weex.init.activity.UWXTheme;
import com.ucar.weex.init.activity.UWXThemeManager;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.update.FileUtils;
import com.ucar.weex.update.UWXResManager;
import com.ucar.weex.update.WXPackageInfo;

/**
 * weex 初始化
 */
public class WXApplication extends Application {
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        UWXInit.init(this);
        //设置主题 过场动画 statusBar natBar 默认背景 是否有返回 ..
        UWXThemeManager.getInstance().setPageTheme(new UWXTheme(new UWXTheme.NavBar("#ffffff", "#000000"), com.ucar.weex.R.style.wx_theme_app));
        /**
         * assets/weex/ucar-weex_3_20170828123442
         */
        UWXResManager.getInstance().addWXResFromAssert(this, FileUtils.getWXPackageFileName(this,"weex"));
//        UWXResManager.getInstance().setServerUrl("");
        UWXResManager.getInstance().checkUpdate(new UWXResManager.CheckUpdateCallback() {
            @Override
            public void callback(int code, String msg, WXPackageInfo info) {
                Toast.makeText(WXApplication.this, msg, Toast.LENGTH_LONG).show();
                UWLog.d("WXApp", msg);
                //重启 提示
            }
        });
    }

}
