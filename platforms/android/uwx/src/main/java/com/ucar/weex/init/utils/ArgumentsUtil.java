package com.ucar.weex.init.utils;

/**
 * Created by chenxi.cui on 2017/7/12.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ArgumentsUtil {
    public ArgumentsUtil() {
    }


    public static Bundle fromJsonToBundle(JSONObject jsonObject) {
        Bundle budle = new Bundle();
        if (jsonObject == null) {
            return budle;
        } else {
            Iterator iterator = jsonObject.keySet().iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Object value = jsonObject.get(key);
                if (value != null) {
                    if (value instanceof String) {
                        budle.putString(key, (String) value);
                    } else if (value instanceof Byte) {
                        budle.putByte(key, ((Byte) value).byteValue());
                    } else if (value instanceof Short) {
                        budle.putShort(key, ((Short) value).shortValue());
                    } else if (value instanceof Integer) {
                        budle.putInt(key, ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        budle.putLong(key, ((Long) value).longValue());
                    } else if (value instanceof Float) {
                        budle.putFloat(key, ((Float) value).floatValue());
                    } else if (value instanceof Double) {
                        budle.putDouble(key, ((Double) value).doubleValue());
                    } else if (value instanceof Boolean) {
                        budle.putBoolean(key, ((Boolean) value).booleanValue());
                    } else if (value instanceof Character) {
                        budle.putChar(key, ((Character) value).charValue());
                    } else if (value instanceof JSONObject) {
                        budle.putBundle(key, fromJsonToBundle((JSONObject) value));
                    } else {
                        if (!value.getClass().isArray()) {
                            throw new IllegalArgumentException("Could not convert " + value.getClass());
                        }

                        fromArrayToBundle(budle, key, value);
                    }
                }
            }

            return budle;
        }
    }

    public static void fromArrayToBundle(Bundle bundle, String key, Object array) {
        if (bundle != null && !TextUtils.isEmpty(key) && array != null) {
            if (array instanceof String[]) {
                bundle.putStringArray(key, (String[]) ((String[]) array));
            } else if (array instanceof byte[]) {
                bundle.putByteArray(key, (byte[]) ((byte[]) array));
            } else if (array instanceof short[]) {
                bundle.putShortArray(key, (short[]) ((short[]) array));
            } else if (array instanceof int[]) {
                bundle.putIntArray(key, (int[]) ((int[]) array));
            } else if (array instanceof long[]) {
                bundle.putLongArray(key, (long[]) ((long[]) array));
            } else if (array instanceof float[]) {
                bundle.putFloatArray(key, (float[]) ((float[]) array));
            } else if (array instanceof double[]) {
                bundle.putDoubleArray(key, (double[]) ((double[]) array));
            } else if (array instanceof boolean[]) {
                bundle.putBooleanArray(key, (boolean[]) ((boolean[]) array));
            } else if (array instanceof char[]) {
                bundle.putCharArray(key, (char[]) ((char[]) array));
            } else {
                if (!(array instanceof JSONArray)) {
                    throw new IllegalArgumentException("Unknown array type " + array.getClass());
                }

                ArrayList arraylist = new ArrayList();
                JSONArray jsonArray = (JSONArray) array;
                Iterator it = jsonArray.iterator();

                while (it.hasNext()) {
                    JSONObject object = (JSONObject) it.next();
                    arraylist.add(fromJsonToBundle(object));
                }

                bundle.putParcelableArrayList(key, arraylist);
            }

        }
    }
    public static ArrayList fromArray(Object array) {
        ArrayList list = new ArrayList();

        int len$;
        int i$;
        if(array instanceof String[]) {
            String[] arr$ = (String[])((String[])array);
            len$ = arr$.length;

            for(i$ = 0; i$ < len$; ++i$) {
                String v = arr$[i$];
                list.add(v);
            }
        } else if(array instanceof Bundle[]) {
            Bundle[] var7 = (Bundle[])((Bundle[])array);
            len$ = var7.length;

            for(i$ = 0; i$ < len$; ++i$) {
                Bundle var12 = var7[i$];
                list.add(fromBundle(var12));
            }
        } else if(array instanceof int[]) {
            int[] var8 = (int[])((int[])array);
            len$ = var8.length;

            for(i$ = 0; i$ < len$; ++i$) {
                int var13 = var8[i$];
                list.add(var13);
            }
        } else if(array instanceof float[]) {
            float[] var9 = (float[])((float[])array);
            len$ = var9.length;

            for(i$ = 0; i$ < len$; ++i$) {
                float var14 = var9[i$];
                list.add((double)var14);
            }
        } else if(array instanceof double[]) {
            double[] var10 = (double[])((double[])array);
            len$ = var10.length;

            for(i$ = 0; i$ < len$; ++i$) {
                double var15 = var10[i$];
                list.add(var15);
            }
        } else {
            if(!(array instanceof boolean[])) {
                throw new IllegalArgumentException("Unknown array type " + array.getClass());
            }

            boolean[] var11 = (boolean[])((boolean[])array);
            len$ = var11.length;

            for(i$ = 0; i$ < len$; ++i$) {
                boolean var16 = var11[i$];
                list.add(var16);
            }
        }

        return list;
    }
    public static JSONObject fromBundle(Bundle bundle) {
        JSONObject map = new JSONObject();
        Iterator iterator = bundle.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = bundle.get(key);
            if (value == null) {
                map.put(key, null);
            } else if (value.getClass().isArray()) {
                map.put(key, fromArray(value));
            } else if (value instanceof String) {
                map.put(key, value);
            } else if (value instanceof Number) {
                if (value instanceof Integer) {
                    map.put(key, ((Integer) value).intValue());
                } else {
                    map.put(key, ((Number) value).doubleValue());
                }
            } else if (value instanceof Boolean) {
                map.put(key, ((Boolean) value).booleanValue());
            } else {
                if (!(value instanceof Bundle)) {
                    throw new IllegalArgumentException("Could not convert " + value.getClass());
                }

                map.put(key, fromBundle((Bundle) value));
            }
        }

        return map;
    }


}
