package com.ucar.weex.init.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ucar.weex.R;
import com.ucar.weex.init.activity.WXFrameBaseActivity;
import com.ucar.weex.init.utils.Assertions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.annotation.Nullable;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class WXBaseActivityManager {
    private Stack<BaseInfo> mUsedStack;
    private Stack<BaseInfo> mFreeStack;
    private List<OnActivityLifeListener> listenerList;

    public WXBaseActivityManager() {
        this.initStack();
    }

    private void initStack() {
        this.mFreeStack = new Stack();
        this.mUsedStack = new Stack();

        try {
            for (int e = 20; e >= 1; --e) {
                this.mFreeStack.push(new BaseInfo(Class.forName("com.ucar.weex.init.mactivity.UWXNativityActivity" + e)));
            }
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
        }

    }

    public Class startActivity(Activity activity) {
        return this.startActivity(activity, (Bundle) null);
    }

    public Class startActivity(Activity activity, @Nullable Bundle extraBundle) {
        return this.startActivityForResult(activity, extraBundle, -1);
    }

    public Class startActivityForResult(Activity activity, int requestCode) {
        return this.startActivityForResult(activity, (Bundle) null, requestCode);
    }

    public Class startActivityForResult(Activity activity, @Nullable Bundle extraBundle, int requestCode) {
        Assertions.assertNotNull(activity);
        if (this.mFreeStack.empty()) {
            this.handleNoAvailableActivity();
        }

        BaseInfo baseInfo = this.mFreeStack.pop();
        Intent intent = new Intent(activity, baseInfo.mClass);
        if (extraBundle != null) {
            intent.putExtras(extraBundle);
        }

        activity.startActivityForResult(intent, requestCode);
//        activity.overridePendingTransition(0, 0);
        return baseInfo.mClass;
    }

    protected void backToActivity(Activity activity, Class backToClass, @Nullable Bundle bundle) {
        Assertions.assertNotNull(activity);
        Assertions.assertNotNull(backToClass);
        Assertions.assumeCondition(!this.mUsedStack.empty(), "There is no available  Activity");
        Iterator iterator = this.mUsedStack.iterator();

        BaseInfo baseInfo;
        do {
            if (!iterator.hasNext()) {
                throw new IllegalStateException("There is no such a activity to return");
            }

            baseInfo = (BaseInfo) iterator.next();
        } while (!baseInfo.equals(backToClass));

        Intent intent = new Intent(activity, backToClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.wx_back_left_in_show, R.anim.wx_back_right_out_dismiss);
    }

    public int getFreeActivitySize() {
        return this.mFreeStack.size();
    }

    void onActivityDestroy(Activity activity) {
    }

    void onStartNewActivity(Activity activity) {
        Assertions.assertNotNull(activity);
        this.mUsedStack.push(new BaseInfo(activity.getClass(), activity.toString()));
        dispatchOnActivityCreate(activity.getClass());
    }

    void onBackToExistActivity(Activity activity, boolean isDestroyed) {
        Assertions.assertNotNull(activity);
        while (!this.mUsedStack.isEmpty()) {
            BaseInfo popInfo = (BaseInfo) this.mUsedStack.pop();
            if (isDestroyed && popInfo.mClass == activity.getClass()) {
                this.mUsedStack.push(popInfo);
                return;
            }

            if (!isDestroyed && popInfo.mActivityStr.contentEquals(activity.toString())) {
                this.mUsedStack.push(popInfo);
                return;
            }

            this.dispatchOnActivityDestroy(popInfo.mClass);
            if (WXFrameBaseActivity.class.isAssignableFrom(popInfo.mClass)) {
                popInfo.mActivityStr = null;
                this.mFreeStack.push(popInfo);
            }
        }
    }

    private BaseInfo getInfo(Stack<BaseInfo> stack, String activityStr) {
        return this.getInfo(stack, (Class) null, activityStr);
    }

    private BaseInfo getInfo(Stack<BaseInfo> stack, Class activityCLass) {
        return this.getInfo(stack, activityCLass, (String) null);
    }

    private BaseInfo getInfo(Stack<BaseInfo> stack, @Nullable Class activityClass, @Nullable String activityStr) {
        Assertions.assertCondition(activityClass == null && activityStr != null || activityClass != null && activityStr == null);
        boolean searchClass = activityClass != null;
        Iterator iterator = stack.iterator();

        while (iterator.hasNext()) {
            BaseInfo info = (BaseInfo) iterator.next();
            if (searchClass) {
                if (info.mClass == activityClass) {
                    return info;
                }
            } else if (activityStr.contentEquals(info.mActivityStr)) {
                return info;
            }
        }

        return null;
    }

    public void addOnActivityDestroyListener(OnActivityLifeListener listener) {
        if (this.listenerList == null) {
            this.listenerList = new ArrayList();
        }
        this.listenerList.add(listener);
    }

    private void dispatchOnActivityDestroy(Class destroyedActivityClass) {
        if (this.listenerList != null) {
            Iterator iterator = this.listenerList.iterator();
            while (iterator.hasNext()) {
                OnActivityLifeListener listener = (OnActivityLifeListener) iterator.next();
                if (listener.onDestroy(destroyedActivityClass)) {
                    this.listenerList.remove(listener);
                }
            }
        }
    }
    private void dispatchOnActivityCreate(Class activityClass) {
        if (this.listenerList != null) {
            Iterator iterator = this.listenerList.iterator();
            while (iterator.hasNext()) {
                OnActivityLifeListener listener = (OnActivityLifeListener) iterator.next();
                if (listener.onCreate(activityClass)) {
                    this.listenerList.remove(listener);
                }
            }

        }
    }


    protected void handleNoAvailableActivity() {
        throw new IllegalStateException("weex Activities already used up T^T");
    }

    static class BaseInfo {
        private Class mClass;
        private String mActivityStr;

        public BaseInfo(Class activityClass) {
            this(activityClass, (String) null);
        }

        public BaseInfo(Class activityClass, String activityString) {
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
    }
    public interface OnActivityLifeListener {
        boolean onDestroy(Class var1);
        boolean onCreate(Class var1);
    }
}
