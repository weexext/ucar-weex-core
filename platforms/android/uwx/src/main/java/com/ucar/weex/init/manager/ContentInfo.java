package com.ucar.weex.init.manager;

import android.app.Activity;
import android.text.TextUtils;

import com.ucar.weex.init.utils.Assertions;

/**
 * Created by chenxi.cui on 2017/9/6.
 */
public class ContentInfo {
    public Class mClass;
    public String mActivityStr;

    public ContentInfo(Class activityClass) {
        this(activityClass, activityClass != null ? activityClass.getName() : null);
    }

    public ContentInfo(Class activityClass, String activityString) {
        Assertions.assumeCondition(Activity.class.isAssignableFrom(activityClass), "This class is not a Activity class");
        this.mClass = activityClass;
        if (activityString != null) {
            this.mActivityStr = activityString;
        }

    }

    public void setActivityStr(String activityStr) {
        Assertions.assertCondition(!TextUtils.isEmpty(activityStr));
        this.mActivityStr = activityStr;
    }

    public Class getActivityClass() {
        return this.mClass;
    }

    public String getActivityStr() {
        return this.mActivityStr;
    }

    public boolean equals(Class activityClass) {
        return activityClass == this.mClass;
    }

    public boolean equals(String activityStr) {
        return this.mActivityStr == activityStr;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ContentInfo) {
            return ((ContentInfo) o).getActivityStr().equals(getActivityStr());
        }
        return super.equals(o);
    }
}
