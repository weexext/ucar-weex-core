package com.ucar.weex.update;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/8/25.
 */

public class UpdateResult implements Serializable {
    public UpdateData content;
    public int code;
    public String msg;

    public static class UpdateData implements Serializable {
        public boolean upgrade;
        public boolean forceUpgrade;
        public boolean totalUpgrade;
        public String downloadUrl;
        public String upgradeMsg;
        public String versionName;
        public String upgradeDate;
        public int versionCode;
    }

}
