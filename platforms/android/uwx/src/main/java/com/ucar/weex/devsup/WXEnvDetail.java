package com.ucar.weex.devsup;

import com.taobao.weex.utils.LogLevel;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/7/26.
 */

public class WXEnvDetail implements Serializable {
    // todo edit your iphost and port here
    public String host = "xx.xx.xx.xx";
    public String port = "xxxx";

    public boolean isLaunchLocally = true;
    public boolean debugInChrome = true;
    public boolean debugMode = false;
    public boolean debugRemote = false;
    public LogLevel logLevel = LogLevel.DEBUG;
    public String testUrl="test/pageA.js";
}
