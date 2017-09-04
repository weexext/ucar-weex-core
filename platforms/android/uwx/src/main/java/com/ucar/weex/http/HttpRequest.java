package com.ucar.weex.http;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by david on 16/4/6.
 */
public interface HttpRequest extends Serializable {

    String buildUrl();

    Map<String,String> headers();

    void setHeader(String name, String value);

}
