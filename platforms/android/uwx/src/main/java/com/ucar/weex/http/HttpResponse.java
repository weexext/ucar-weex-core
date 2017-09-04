package com.ucar.weex.http;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by david on 16/4/7.
 */
public interface HttpResponse extends Serializable {

    String rawData();

    void putRawData(String data);

    Map<String,String> headers();

    void setHeaders(Map<String, String> headers);
}
