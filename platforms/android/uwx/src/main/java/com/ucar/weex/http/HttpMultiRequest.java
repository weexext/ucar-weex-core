package com.ucar.weex.http;

import java.io.File;
import java.util.Map;

/**
 * Created by david on 16/4/29.
 */
public interface HttpMultiRequest extends HttpRequest {

    String getFilePart();

    File getFile();

    Map<String,String> getParams();

}
