package com.ucar.weex.devsup;

import com.ucar.weex.UEnvironment;
import com.ucar.weex.utils.SharedUtil;

/**
 * Created by chenxi.cui on 2017/7/26.
 */

public class WXEnvDetailHelper {
    private static WXEnvDetailHelper instance = null;
    private static WXEnvDetail wxEnvDetail;

    public static WXEnvDetailHelper getInstance() {
        if (instance == null) {
            synchronized (WXEnvDetailHelper.class) {
                if (instance == null) {
                    instance = new WXEnvDetailHelper();
                }
            }
        }
        return instance;
    }

    public WXEnvDetail getWXEnvDetail() {
        if (wxEnvDetail == null) {
            wxEnvDetail = SharedUtil.instance(UEnvironment.getsApplication()).getObject("key_wx_env_detail", WXEnvDetail.class);
        }
        if (wxEnvDetail == null) {
            wxEnvDetail = new WXEnvDetail();
        }
        return wxEnvDetail;
    }

    public void saveWXEnvDetail(WXEnvDetail wxEnvDetail) {
        this.wxEnvDetail = wxEnvDetail;
        SharedUtil.instance(UEnvironment.getsApplication()).saveObject("key_wx_env_detail", wxEnvDetail);
    }

    public String getPort() {
        WXEnvDetail wxEnvDetail = getWXEnvDetail();
        return wxEnvDetail == null ? "" : wxEnvDetail.port;
    }

    public boolean isLocalServer() {
        WXEnvDetail wxEnvDetail = getWXEnvDetail();
        return wxEnvDetail == null ? false : wxEnvDetail.isLaunchLocally;
    }

    public boolean debugRemote() {
        WXEnvDetail wxEnvDetail = getWXEnvDetail();
        return wxEnvDetail == null ? false : wxEnvDetail.debugRemote;
    }

    public boolean debugInChrome() {
        WXEnvDetail wxEnvDetail = getWXEnvDetail();
        return wxEnvDetail == null ? false : wxEnvDetail.debugInChrome;
    }


    public String getHost() {
        WXEnvDetail wxEnvDetail = getWXEnvDetail();
        return wxEnvDetail == null ? "" : wxEnvDetail.host;
    }
}
