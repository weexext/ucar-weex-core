package com.ucar.weex.okhttp.request;


import java.io.File;
import java.util.HashMap;


public class RequestParams {

    public HashMap<String, String> urlParams;
    public HashMap<String, Object> fileParams;

    public void put(String key, String value) {
        if (urlParams == null) {
            urlParams = new HashMap<>();
        }
        urlParams.put(key, value);
    }

    public void put(String fileName, File file) {
        if (fileParams == null) {
            fileParams = new HashMap<>();
        }
        fileParams.put(fileName, file);
    }


}
