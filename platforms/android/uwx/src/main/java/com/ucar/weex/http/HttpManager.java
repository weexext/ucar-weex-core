package com.ucar.weex.http;


import com.ucar.weex.http.okhttp.HttpManagerImpl;

import java.io.File;
import java.util.Map;

/**
 * Created by david on 15/6/17.
 * http请求接口
 */
public interface HttpManager {

    HttpManager instance = HttpManagerImpl.registerInstance();

    /**
     * @param request HttpRequest 网络请求对象,封装请求参数,支持MApi
     * @param callback HttpCallback 请求回调
     * @return
     */
    void send(HttpRequest request, HttpCallback<? extends HttpResponse> callback);

    /**
     * @param url 请求的url
     * @param callback HttpCallback 请求回调
     * @return
     */
    void send(String url, HttpCallback<? extends HttpResponse> callback);

    /**
     *
     * @param request HttpRequest 网络请求对象,封装请求参数,支持MApi的Post请求和文件上传
     * @param callback HttpCallback 请求回调
     */
    void send(HttpMultiRequest request, HttpCallback<? extends HttpResponse> callback);
    /**
     * @param url 请求的url
     * @param headers Headers请求头参数
     * @param params POST请求参数
     * @param callback HttpCallback 请求回调
     * @return
     */
    void send(String url, Map<String, String> headers, Map<String, String> params, HttpCallback<? extends HttpResponse> callback);

    /**
     *
     * @param url 请求的url
     * @param headers Headers请求头参数
     * @param params POST请求参数,支持Multi File
     * @param filePartName 文件名称
     * @param file  文件对象
     * @param callback HttpCallback 请求回调
     */
    void send(String url, Map<String, String> headers, Map<String, String> params, String filePartName, File file, HttpCallback<? extends HttpResponse> callback);


}
