package com.ucar.weex.okhttp.listerner;

/**
 * Created by quan on 2017/7/28.
 */

public interface DisposeDataListener {

    void onSuccess(Object responseObj);

    void onFailure(Object reasonObj);

}
