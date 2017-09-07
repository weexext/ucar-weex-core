package com.ucar.weex.init.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ucar.weex.init.activity.UWXFrameBaseActivity;
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
    private Stack<ContentInfo> mUsedStack;
    private Stack<ContentInfo> mFreeStack;
    private List<OnActivityLifeListener> listenerList;

    public WXBaseActivityManager() {
        this.initStack();
    }

    private void initStack() {
        this.mFreeStack = new Stack();
        this.mUsedStack = new Stack();

        try {
            for (int e = 20; e >= 1; --e) {
                this.mFreeStack.push(new ContentInfo(Class.forName("com.ucar.weex.init.mactivity.UWXNativityActivity" + e)));
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
        ContentInfo baseInfo = this.mFreeStack.pop();
        this.mUsedStack.push(baseInfo);
        Intent intent = new Intent(activity, baseInfo.mClass);
        if (extraBundle != null) {
            intent.putExtras(extraBundle);
        }
        activity.startActivityForResult(intent, requestCode);
        return baseInfo.mClass;
    }

    protected void backToActivity(Activity activity, Class backToClass, @Nullable Bundle bundle) {
        Assertions.assertNotNull(activity);
        Assertions.assertNotNull(backToClass);
        Intent intent = new Intent(activity, backToClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public int getFreeActivitySize() {
        return this.mFreeStack.size();
    }



    public void removeContext(ContentInfo contentInfo) {
        if (!UWXFrameBaseActivity.class.isAssignableFrom(contentInfo.getActivityClass())) {
            return;
        }
        while (!this.mUsedStack.isEmpty()) {
            ContentInfo popInfo = this.mUsedStack.pop();
            if (popInfo.getActivityStr() == contentInfo.getActivityStr()) {
                this.mFreeStack.push(popInfo);
                return;
            }
            this.mUsedStack.push(popInfo);
        }
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


    public interface OnActivityLifeListener {
        boolean onDestroy(Class var1);

        boolean onCreate(Class var1);
    }
}
