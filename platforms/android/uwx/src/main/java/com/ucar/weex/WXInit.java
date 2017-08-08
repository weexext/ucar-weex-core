package com.ucar.weex;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.ucar.weex.appfram.navigator.ActivityNavBarSetterImpl;
import com.ucar.weex.commons.adapter.DebugAdapter;
import com.ucar.weex.commons.adapter.JSExceptionAdapter;
import com.ucar.weex.devsup.WXEnvManager;
import com.ucar.weex.commons.adapter.ImageAdapter;
import com.ucar.weex.init.manager.WXActivityManagerHelper;
import com.ucar.weex.module.UNavigatorModule;
import com.ucar.weex.module.UWXGlobalEventModule;
import com.ucar.weex.module.UWXSchemeModule;

/**
 * Created by chenxi.cui on 2017/7/14.
 */

public class WXInit {
    public static void init(Application context) {
        UEnvironment.init(context);
        ActivityListenerInit.init(context);
        WXActivityManagerHelper.init(context);
        WXEnvManager.initDebugEnvironment(context);
        WXSDKEngine.initialize(context,
                new InitConfig.Builder()
                        .setImgAdapter(new ImageAdapter())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .setDebugAdapter(new DebugAdapter())
                        .build()
        );

        try {
            WXSDKEngine.registerModule("Ext", UNavigatorModule.class);
            WXSDKEngine.registerModule("scheme", UWXSchemeModule.class);
            WXSDKEngine.registerModule("sendGlobalEvent", UWXGlobalEventModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
        Fresco.initialize(context);
        USDKManager.setActivityNavBarSetter(new ActivityNavBarSetterImpl());
    }
}
