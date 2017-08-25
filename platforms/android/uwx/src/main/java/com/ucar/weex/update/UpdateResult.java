package com.ucar.weex.update;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/8/25.
 */

public class UpdateResult implements Serializable {
    public BStatus bstatus = new BStatus();
    public UpdateData data;

    public static class UpdateData implements Serializable {
        public WXPackageInfo packageInfo;
        public String url;
    }

    public static class BStatus implements Serializable {

        public int code;
        public String des;
    }
}
