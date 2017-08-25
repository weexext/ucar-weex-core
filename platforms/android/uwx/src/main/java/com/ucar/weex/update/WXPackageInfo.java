package com.ucar.weex.update;

import java.io.Serializable;

/**
 * "appName": "ucar-weex",
 * "versionCode": 1,
 * "versionName": "1.0",
 * "versionDes": "新版说明",
 * "androidMinVersion": 1,
 * "iosMinVersion": 1,
 * "groupId": "vid_001",
 * "md5": "d60beb4217ab0de9c2492c850b5edd7e",
 * "length": 573582,
 * "time": "20170824151743"
 */

public class WXPackageInfo implements Serializable {
    public String appName;
    public int versionCode;
    public String versionName;
    public String versionDes;
    public String androidMinVersion;
    public String groupId;
    public String path;
    public String md5;
    public String time;
    public long length;

    public void setPath(String path) {
        String _path = path.trim();
        _path = _path.replace("-", "");
        _path = _path.replace("_", "");
        this.path = _path;
    }
}
