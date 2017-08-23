package com.ucar.weex.commons.adapter;

import android.content.Context;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.common.WXPerformance;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by chenxi.cui on 2017/7/27.
 */

public class UserTrackAdapter implements IWXUserTrackAdapter {
    @Override
    public void commit(Context context, String eventId, String type, WXPerformance perf, Map<String, Serializable> params) {

    }
}
