package com.ucar.weex.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.json.exception.JsonException;

import java.util.List;

/**
 * Created by david on 16/7/27.
 */
public interface JsonHelper {

    JsonHelper instance = JsonHelperImpl.registerInstance();

    String encode(Object obj);

    JSONObject decode(String json) throws JsonException;

    JSONArray decodeArray(String json) throws JsonException;

    <T> T decode(String json, Class<T> clazz) throws JsonException;

    <T> List<T> decodeList(String json, Class<T> clazz) throws JsonException;
}
