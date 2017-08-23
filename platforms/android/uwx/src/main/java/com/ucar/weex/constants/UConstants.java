package com.ucar.weex.constants;

public class UConstants {

    //  public static final String BUNDLE_URL = "http://t.cn?_wx_tpl=http://h5.waptest.taobao.com/app/weextc031/build/TC__Home.js";
    public static final String BUNDLE_URL = "http://t.cn?_wx_tpl=http://g.tbcdn.cn/weex/weex-tc/0.1.0/build/TC__Home.js";


    //hot refresh
    public static final int HOT_REFRESH_CONNECT = 0x111;
    public static final int HOT_REFRESH_DISCONNECT = HOT_REFRESH_CONNECT + 1;

    public interface Event {
        String ACTIVED = "actived";
        String READY = "ready";
        String ONANDROIDBACK = "onAndroidBack";
        String DEACTIVED = "deactived";
    }
}
