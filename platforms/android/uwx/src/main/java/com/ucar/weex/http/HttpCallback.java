package com.ucar.weex.http;

import com.alibaba.fastjson.TypeReference;


/**
 * Created by david on 16/4/6.
 */
public class HttpCallback<HttpResponseType extends HttpResponse> extends TypeReference<HttpResponseType> {

    /**
     * 对返回数据进行解码
     * @param response
     * @return
     */
    public String decodeResponse(String response){
        return response;
    }

    public void onSuccess(HttpResponseType result){

    }

    public void onError(Throwable ex){

    }

    public void onCancelled(){

    }

    public void onStarted(){

    }
}
