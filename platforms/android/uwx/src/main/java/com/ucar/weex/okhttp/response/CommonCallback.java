package com.ucar.weex.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.okhttp.exception.OkHttpException;
import com.ucar.weex.okhttp.listerner.DisposeDataHandle;
import com.ucar.weex.okhttp.listerner.DisposeDataListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonCallback implements Callback {

    private static final String TAG = "CommonCallback";

    /**
     * the logic layer exception, may alter in different app
     */
    protected final String RESULT_CODE = "ecode";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Handler mDeliverHandler;
    private String mFilePath;


    public CommonCallback(DisposeDataHandle handle) {
        mListener = handle.mListener;
        mClass = handle.mClass;
        mFilePath = handle.mFilePath;
        mDeliverHandler = new Handler(Looper.getMainLooper());

    }


    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliverHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {

//            final String result = response.body().string();
        if (mFilePath == null) {
          final String result = response.body().string();
            mDeliverHandler.post(new Runnable() {
                @Override
                public void run() {
                    handleJsonResponse(result);
                }
            });
        } else {
            handleFileResponse(response);
        }
    }

    private void handleJsonResponse(String result) {

        if (TextUtils.isEmpty(result)) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {

            //这里可以按自己和后台约定的逻辑解析JOSN.
//            JSONObject resultObj = JSON.parseObject(result);
//            if (resultObj.containsKey(RESULT_CODE)) {
//                if (resultObj.get(RESULT_CODE) == RESULT_CODE_VALUE) {
//                    if (mClass == null) {
//                        mListener.onSuccess(resultObj);
//                    } else {
//                        //自己创建一个JSON解析的类,或者用第三方的框架.
//                        Object obj = new Object();
//                        if (obj == null) {
//                            mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
//                        } else {
//                            //走到此处,我们的响应才真正正确的处理了,并且直接返回了实体对像
//                            mListener.onSuccess(obj);
//                        }
//                    }
//                } else {
//                    mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
//                }
//            }

            //只为测试显示用.正式应用,删除这句代码.
            mListener.onSuccess(result);

        } catch (Exception e) {
            mListener.onSuccess(new OkHttpException(OTHER_ERROR, e.getMessage()));
        }

    }

    private void handleFileResponse(Response response) {

        long total = response.body().contentLength();

        if (total == 0) {
            mListener.onSuccess(new OkHttpException(OTHER_ERROR, EMPTY_MSG));
            return;
        }

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {

            long current = 0;
            is = response.body().byteStream();
            fos = new FileOutputStream(mFilePath);
            while ((len = is.read(buf)) != -1) {
                current += len;
                fos.write(buf, 0, len);
                Log.d(TAG, "handleFileResponse: ----------->" + current + "/" + total);
            }
            fos.flush();

            mDeliverHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onSuccess(mFilePath);
                }
            });
        } catch (IOException e) {
            mListener.onSuccess(new OkHttpException(OTHER_ERROR, e.getMessage()));
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
