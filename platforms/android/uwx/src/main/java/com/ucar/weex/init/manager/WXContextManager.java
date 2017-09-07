package com.ucar.weex.init.manager;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.init.UWXPageDataCallback;
import com.ucar.weex.init.utils.Assertions;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class WXContextManager {
    private static WXContextManager instance = null;
    private LinkedList<ContentInfo> mStack;
    private UWXPageDataCallback callback;

    public void addPageDataCallback(UWXPageDataCallback callback) {
        this.callback = callback;
    }


    public static WXContextManager getInstance() {
        if (instance == null) {
            synchronized (WXContextManager.class) {
                if (instance == null) {
                    instance = new WXContextManager();
                }
            }
        }
        return instance;
    }

    private WXContextManager() {
        mStack = new LinkedList<>();
    }

    public void recordContext(Class activityClass) {
        Assertions.assertNotNull(activityClass);
        ContentInfo contentInfo = new ContentInfo(activityClass);
        this.mStack.add(contentInfo);
    }

    public void removeContext(Class activityClass) {
        Assertions.assertNotNull(activityClass);
        ContentInfo contentInfo = getContentInfoByClass(activityClass);
        this.mStack.remove(contentInfo);
        WXActivityManager.getInstance().removeContext(contentInfo);
    }

    public ContentInfo getContentInfoByClass(Class activityClass) {
        if (!ArrayUtils.isEmpty(mStack)) {
            Iterator<ContentInfo> iterator = mStack.iterator();
            while (iterator.hasNext()) {
                ContentInfo next = iterator.next();
                if (next.getActivityStr().equals(activityClass.getName())) {
                    return next;
                }
            }
        }
        return null;
    }

    public int getIndexByClass(Class activityClass) {
        Assertions.assertNotNull(activityClass);
        return mStack.indexOf(activityClass);
    }

    public int getStackCount() {
        return mStack.size();
    }

    public ContentInfo getContextByIndex(int index) {
        index = getStackCount() - Math.abs(index) - 1;
        return getStackCount() > index && index >= 0 ? mStack.get(index) : mStack.get(0);
    }


    public void receiveBack(Activity activity) {
        if (activity != null) {
            if (callback != null) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    String params = intent.getStringExtra("params");
                    JSONObject jsonObject = null;
                    if (!TextUtils.isEmpty(params)) {
                        try {
                            jsonObject = JSON.parseObject(params);
                        } catch (Exception e) {
                            UWLog.e("WXC", e.getMessage());
                        }
                    }
                    String backTag = intent.getStringExtra("backTag");
                    callback.callBack(backTag, jsonObject);
                }
            }
        }
    }
}

