package com.ucar.weex.commons.adapter;

import com.taobao.weex.adapter.IWXJSExceptionAdapter;
import com.taobao.weex.common.WXJSExceptionInfo;

/**
 * Created by chenxi.cui on 2017/7/27.
 */

public class JSExceptionAdapter implements IWXJSExceptionAdapter {
    @Override
    public void onJSException(WXJSExceptionInfo exception) {
//        UWLog.v(exception.getException());
    }
}
