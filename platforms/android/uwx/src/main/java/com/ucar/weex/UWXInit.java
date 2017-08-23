package com.ucar.weex;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.common.WXException;
import com.ucar.weex.commons.adapter.DebugAdapter;
import com.ucar.weex.commons.adapter.FrescoImageAdapter;
import com.ucar.weex.commons.adapter.JSExceptionAdapter;
import com.ucar.weex.module.UWXNavigatorModule;
import com.ucar.weex.module.UWXGlobalEventModule;
import com.ucar.weex.module.UWXNavigatorModule2;
import com.ucar.weex.module.UWXSchemeModule;

/**
 * Created by chenxi.cui on 2017/7/14.
 */

public class UWXInit {
    public static void init(Application context) {
        UWXSDKManager.initialize(context);
        WXSDKEngine.addCustomOptions("scheme", "portal");
        WXSDKEngine.addCustomOptions(WXConfig.appGroup, "portal");
        WXSDKEngine.addCustomOptions(WXConfig.appName, "portal");
        WXSDKEngine.initialize(context,
                new InitConfig.Builder()
                        .setImgAdapter(new FrescoImageAdapter())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .setDebugAdapter(new DebugAdapter())
                        .build()
        );
        try {
            WXSDKEngine.registerModule("UNavigator", UWXNavigatorModule.class);
            WXSDKEngine.registerModule("UScheme", UWXSchemeModule.class);
            WXSDKEngine.registerModule("UGlobalEvent", UWXGlobalEventModule.class);
            WXSDKEngine.registerModule("navigator", UWXNavigatorModule2.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
        Fresco.initialize(context);
    }
}
