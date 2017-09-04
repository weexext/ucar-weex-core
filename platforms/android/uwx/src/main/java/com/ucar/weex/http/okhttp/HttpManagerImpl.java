package com.ucar.weex.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.ucar.weex.http.HttpCallback;
import com.ucar.weex.http.HttpManager;
import com.ucar.weex.http.HttpMultiRequest;
import com.ucar.weex.http.HttpRequest;
import com.ucar.weex.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by david on 16/8/9.
 */
public class HttpManagerImpl implements HttpManager {

    static final Handler sHandler = new Handler(Looper.getMainLooper());

    OkHttpClient mClient = null;
    private static final Object lock = new Object();
    private static HttpManager instance;

    private HttpManagerImpl(){
        mClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse;
                        }
                    })
                    .build();

    }
    public static HttpManager registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new HttpManagerImpl();
                }
            }
        }
        return instance;
    }
    /**
     * @param request  HttpRequest 网络请求对象,封装请求参数,支持MApi
     * @param callback HttpCallback 请求回调
     * @return
     */
    @Override
    public void send(HttpRequest request, HttpCallback<? extends HttpResponse> callback) {
        Request req = RequestBuilder.build(request);
        mClient.newCall(req).enqueue(convertCallback(callback));
    }



    /**
     * @param url      请求的url
     * @param callback HttpCallback 请求回调
     * @return
     */
    @Override
    public void send(String url, HttpCallback<? extends HttpResponse> callback) {
        Request req = RequestBuilder.build(url,null);
        mClient.newCall(req).enqueue(convertCallback(callback));
    }

    /**
     * @param request  HttpRequest 网络请求对象,封装请求参数,支持MApi的Post请求和文件上传
     * @param callback HttpCallback 请求回调
     */
    @Override
    public void send(HttpMultiRequest request, HttpCallback<? extends HttpResponse> callback) {
        Request req = RequestBuilder.build(request);
        mClient.newCall(req).enqueue(convertCallback(callback));
    }

    /**
     * @param url      请求的url
     * @param headers  Headers请求头参数
     * @param params   POST请求参数
     * @param callback HttpCallback 请求回调
     */
    @Override
    public void send(String url, Map<String, String> headers, Map<String, String> params, HttpCallback<? extends HttpResponse> callback) {
        Request req = RequestBuilder.build(url,headers,params);
        mClient.newCall(req).enqueue(convertCallback(callback));
    }

    /**
     * @param url          请求的url
     * @param headers      Headers请求头参数
     * @param params       POST请求参数,支持Multi File
     * @param filePartName 文件名称
     * @param file         文件对象
     * @param callback     HttpCallback 请求回调
     */
    @Override
    public void send(String url, Map<String, String> headers, Map<String, String> params, String filePartName, File file, HttpCallback<? extends HttpResponse> callback) {
        Request req = RequestBuilder.build(url,headers,params);
        mClient.newCall(req).enqueue(convertCallback(callback));
    }

    private boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


    private Callback convertCallback(final HttpCallback callback) {
        return new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                final IOException exception = e;
                if (callback != null) {
                    if (isOnMainThread()){
                        callback.onError(exception);
                    }else{
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(exception);
                            }
                        });
                    }

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    String data = response.body().string();
                    data = callback.decodeResponse(data);
                    try{
                        String rst = null;
                        if (data!=null && data.startsWith("{") && data.endsWith("}") ){
                            rst = data;
                        }else{
                            rst = "{}";
                        }
                        if (callback.getType() != null ) {
                            final HttpResponse httpResponse = JSON.parseObject(rst, callback.getType());
                            httpResponse.putRawData(data);
                            Headers headers = response.headers();
                            if (headers != null) {
                                Map<String, String> headerMap = new HashMap<>();
                                Iterator<String> names = headers.names().iterator();
                                String name;
                                while (names.hasNext()) {
                                    name = names.next();
                                    headerMap.put(name, headers.get(name));
                                }
                                httpResponse.setHeaders(headerMap);
                            }
                            if (isOnMainThread()){
                                callback.onSuccess(httpResponse);
                            }else{
                                sHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onSuccess(httpResponse);
                                    }
                                });
                            }
                        }
                    }catch (Exception e){
                        final Throwable ex = e;
                        if (isOnMainThread()){
                            callback.onError(e);
                        }else{
                            sHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onError(ex);
                                }
                            });
                        }
                    }

                }
            }
        };
    }
}
