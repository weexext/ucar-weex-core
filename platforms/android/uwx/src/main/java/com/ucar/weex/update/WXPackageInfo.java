package com.ucar.weex.update;

import java.io.Serializable;

/**
 * {
 * "appName": "ucar-weex",
 * "appIdAndroid": "c25dbe61-3d56-481a-931a-114a19f2e7f9",
 * "appType": 3,
 * "versionCodeAndroid": 2,
 * "versionNameAndroid": "2.0",
 * "versionDes": "新版说明",
 * "androidMinVersion": 1,
 * "iosMinVersion": 1
 * }
 */

public class WXPackageInfo implements Serializable {
    public String appName;
    public String appIdAndroid;
    public int versionCodeAndroid;
    public String versionNameAndroid;
    public String versionDes;
    public String path;
//    public String md5;
    public String time;
//    public long length;

    public void setPath(String path) {
        String _path = path.trim();
        _path = _path.replace("-", "");
        _path = _path.replace("_", "");
        this.path = _path;
    }
}
