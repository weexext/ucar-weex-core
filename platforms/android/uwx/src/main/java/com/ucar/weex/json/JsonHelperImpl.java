package com.ucar.weex.json;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.json.exception.JsonException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by david on 16/3/30.
 */
final class JsonHelperImpl implements JsonHelper {

    private static final Object lock = new Object();
    private static JsonHelperImpl instance;

    private JsonHelperImpl() {
    }

    public static JsonHelper registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new JsonHelperImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public String encode(Object obj) {
        if (obj instanceof Bundle) {
            return bundle2Json((Bundle) obj);
        } else {
            return JSON.toJSONString(obj);
        }
    }

    @Override
    public JSONObject decode(String json) throws JsonException {
        try {
            return JSON.parseObject(json);
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    @Override
    public JSONArray decodeArray(String json) throws JsonException {
        try {
            return JSON.parseArray(json);
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    @Override
    public <T> T decode(String json, Class<T> clazz) throws JsonException {
        try {
            if (Bundle.class.equals(clazz)) {
                return (T) json2Bundle(json);
            } else {
                return JSON.parseObject(json, clazz);
            }
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    @Override
    public <T> List<T> decodeList(String json, Class<T> clazz) throws JsonException {
        try {
            return JSON.parseArray(json, clazz);
        } catch (Throwable ex) {
            throw new JsonException(ex);
        }
    }

    private Bundle json2Bundle(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Bundle bundle = new Bundle();
        JSONObject jo = JSON.parseObject(json);

        Iterator<Map.Entry<String,Object>> iterator = jo.entrySet().iterator();
        Map.Entry<String,Object> entry;
        Object value;
        while (iterator.hasNext()){
            entry = iterator.next();
            value = entry.getValue();
            if (value != null) {
                if (value instanceof JSONArray) {
                    JSONArray array = (JSONArray) value;
                    ArrayList<String> strList = new ArrayList<String>(array.size());
                    for (Object item : array) {
                        if (item != null) {
                            strList.add(String.valueOf(item));
                        }
                    }
                    bundle.putStringArrayList(entry.getKey(), strList);
                } else if (value instanceof Serializable) {
                    bundle.putSerializable(entry.getKey(), (Serializable) value);
                } else if (value instanceof Parcelable) {
                    bundle.putParcelable(entry.getKey(), (Parcelable) value);
                } else {
                    bundle.putString(entry.getKey(), value.toString());
                }
            }

        }
        return bundle;
    }

    private String bundle2Json(Bundle args) {
        HashMap<String, Object> map = new HashMap<String, Object>(5);
        if (args != null) {
            Set<String> keys = args.keySet();
            if (keys != null) {
                for (String key : keys) {
                    try {
                        Object value = args.get(key);
                        if (value != null) {
                            map.put(key, value);
                        }
                    } catch (Throwable e) {
                        throw new RuntimeException("loadJs error", e);
                    }

                }// end for
            }
        }

        return JSON.toJSONString(map);
    }
}
