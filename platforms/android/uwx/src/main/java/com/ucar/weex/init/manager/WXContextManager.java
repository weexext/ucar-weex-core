package com.ucar.weex.init.manager;

import android.app.Application;
import android.text.TextUtils;
import android.util.SparseArray;

import com.ucar.weex.init.activity.WXFrameBaseActivity;
import com.ucar.weex.init.utils.Assertions;
import com.ucar.weex.init.view.WXRootView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class WXContextManager {
    private static WXContextManager ourInstance = new WXContextManager();
    private Application mApplication;
    private static AtomicInteger sInstanceId = new AtomicInteger(0);
    private Map<String, ContextInfo> mContextMap;
    private String hybridID = "def";

    public static WXContextManager getInstance() {
        return ourInstance;
    }

    private WXContextManager() {
    }

    void init(Application application) {
        Assertions.assertNotNull(application);
        this.mContextMap = new HashMap(15);
        this.mApplication = application;
        WXActivityManager.getInstance().addOnActivityDestroyListener(new WXBaseActivityManager.OnActivityLifeListener() {
            public boolean onDestroy(Class activityClass) {
                WXContextManager.this.removeContext(activityClass);
                return false;
            }

            @Override
            public boolean onCreate(Class var1) {
                WXContextManager.this.recordContextInfoWhenApplicationRun(sInstanceId.incrementAndGet(), var1);
                return false;
            }
        });
        this.mContextMap.put(hybridID, new ContextInfo());
    }


    int resolveContextTag(Class restoreActivityClass) {
        Assertions.assertNotNull(restoreActivityClass);
        if (!TextUtils.isEmpty(hybridID)) {
            SparseArray tagToActivityClass = ((ContextInfo) this.mContextMap.get(hybridID)).tagToActivityClass;
            int info = tagToActivityClass.indexOfValue(restoreActivityClass);
            if (info != -1) {
                return tagToActivityClass.keyAt(info);
            }
        } else {
            Iterator iterator = this.mContextMap.values().iterator();

            while (iterator.hasNext()) {
                ContextInfo info1 = (ContextInfo) iterator.next();
                int index;
                if ((index = info1.tagToActivityClass.indexOfValue(restoreActivityClass)) != -1) {
                    return info1.tagToActivityClass.keyAt(index);
                }
            }
        }

        return -1;
    }

    public int getIndexByClass(Class activityClass) {
        return activityClass != null && !TextUtils.isEmpty(hybridID) ? ((ContextInfo) this.mContextMap.get(hybridID)).tagToActivityClass.indexOfValue(activityClass) : -1;
    }


    void recordContextInfoWhenApplicationRun(int rootViewTag, Class activityClass) {
        Assertions.assertNotNull(activityClass);
        ContextInfo contextInfo = (ContextInfo) this.mContextMap.get(hybridID);
        if (WXFrameBaseActivity.class.isAssignableFrom(activityClass)) {
            contextInfo.tagToActivityClass.put(rootViewTag, activityClass);
        }
    }

    /**
     * @param index 偏移量-2...暂时使用
     * @return
     */
    Class solveReactHybridIDAndIndex(int index) {
        ContextInfo contextInfo = (ContextInfo) this.mContextMap.get(hybridID);
        int size = contextInfo.tagToActivityClass.size();
        //// TODO: 2017/7/18  目前js传的是偏移量
        index = size - Math.abs(index) - 1;
        return contextInfo != null && index >= 0 ? (Class) contextInfo.tagToActivityClass.valueAt(index) : null;
    }


    private void removeContext(Class activityClass) {
        Iterator iterator = this.mContextMap.values().iterator();
        SparseArray tagToActivityClass;
        int i;
        do {
            if (!iterator.hasNext()) {
                return;
            }
            ContextInfo contextInfo = (ContextInfo) iterator.next();
            tagToActivityClass = contextInfo.tagToActivityClass;
            i = tagToActivityClass.indexOfValue(activityClass);
        } while (i == -1);
        tagToActivityClass.removeAt(i);
    }

    protected static class ContextInfo {
        private SparseArray<Class> tagToActivityClass;

        public ContextInfo() {
            this.tagToActivityClass = new SparseArray(25);
        }
    }
}

