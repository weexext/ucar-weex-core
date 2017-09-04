package com.ucar.weex.http.okhttp;

import android.text.TextUtils;


import com.ucar.weex.http.HttpMultiRequest;
import com.ucar.weex.http.HttpRequest;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by david on 16/8/9.
 */
public class RequestBuilder {

    public static Request build(HttpRequest request){
        return build(request.buildUrl(),request.headers());
    }
    public static Request build(HttpMultiRequest request){
        Request.Builder builder = new Request.Builder();
        builder.url(request.buildUrl());
        builder.headers(convertHeaders(request.headers()));
        builder.post(convertFileRequestBody(request.getParams(),request.getFilePart(),request.getFile()));
        return  builder.build();
    }


    public static Request build(String url, Map<String, String> headers){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.headers(convertHeaders(headers));
        return builder.build();
    }

    public static Request build(String url, Map<String, String> headers, Map<String, String> params){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.headers(convertHeaders(headers));
        builder.post(convertRequestBody(params));
        return  builder.build();
    }

    /**
     * 设置请求头
     * @param headersParams
     * @return
     */
    private static Headers convertHeaders(Map<String, String> headersParams){
        Headers headers=null;
        Headers.Builder headersbuilder=new Headers.Builder();

        if(headersParams != null)
        {
            Iterator<Map.Entry<String,String>> iterator = headersParams.entrySet().iterator();
            Map.Entry<String,String> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                headersbuilder.add(entry.getKey(), entry.getValue());
            }
        }
        headers=headersbuilder.build();

        return headers;
    }
    /**
     * post请求参数
     * @param bodyParams
     * @return
     */
    private static RequestBody convertRequestBody(Map<String, String> bodyParams){
        FormBody.Builder formEncodingBuilder=new FormBody.Builder();
        if (bodyParams != null){
            Iterator<Map.Entry<String,String>> iterator = bodyParams.entrySet().iterator();
            Map.Entry<String,String> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                formEncodingBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body=formEncodingBuilder.build();
        return body;
    }

    /**
     * Post上传图片的参数
     * @param bodyParams
     * @param partName
     * @param file
     * @return
     */
    private static RequestBody convertFileRequestBody(Map<String, String> bodyParams, String partName, File file){
        //带文件的Post参数
        MultipartBody.Builder multipartBodyBuilder=new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);

        if (bodyParams != null){
            Iterator<Map.Entry<String,String>> iterator = bodyParams.entrySet().iterator();
            Map.Entry<String,String> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                multipartBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        if (!TextUtils.isEmpty(partName) && file!=null){
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            multipartBodyBuilder.addFormDataPart(partName, file.getName(), fileBody);
        }

        RequestBody body=multipartBodyBuilder.build();
        return body;

    }


}
