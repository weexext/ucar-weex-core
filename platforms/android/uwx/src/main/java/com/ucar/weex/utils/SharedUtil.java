package com.ucar.weex.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.Serializable;
import java.lang.reflect.Type;


/**
 * SharedPreferences 工具类
 *
 * @author Liang
 */
public class SharedUtil {
    private static SharedPreferences preferences;
    private static SharedUtil sharedUtil = null;

    private SharedUtil(Context context) {
        if (preferences == null)
            preferences = context.getSharedPreferences(AppUtil.appName(context), Context.MODE_PRIVATE);
    }

    public static SharedUtil instance(Context context) {
        if (sharedUtil == null)
            sharedUtil = new SharedUtil(context);

        return sharedUtil;
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void saveString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    public void saveFloat(String key, float value) {
        preferences.edit().putFloat(key, value).commit();
    }

    public void saveLong(String key, long value) {
        preferences.edit().putLong(key, value);
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public void saveObject(String key, Object obj) {
        saveString(key, JSON.toJSONString(obj));
    }

    public <T> T getObject(String cacheKey, Class<T> classOfT) {
        String string = getString(cacheKey);
        if (!TextUtils.isEmpty(string)) {
            return  JSON.parseObject(getString(cacheKey), classOfT);
        }
        return null;
    }

    /**
     * 取泛型(List/Map 等...)用这个
     * get(key, new TypeReference<ArrayList<Object>>() {});
     */
    public static <T extends Serializable> T get(String key, TypeReference<T> type) {
        try {
            return JSON.parseObject(getString(key, null), type);
        } catch (Exception e) {
            return null;
        }
    }


    public <T> T getObject(String cacheKey, Type type){
        return  JSON.parseObject(cacheKey,type);
    }

    public void remove(String key) {
        preferences.edit().remove(key).commit();
    }

}
