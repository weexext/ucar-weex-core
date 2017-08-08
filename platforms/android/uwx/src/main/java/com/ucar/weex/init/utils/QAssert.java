package com.ucar.weex.init.utils;

import android.text.TextUtils;

import javax.annotation.Nullable;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class QAssert {
    public static <T> T assertNotNull(@Nullable T object) {
        if (object == null) {
            throw new AssertionError();
        } else {
            return object;
        }
    }

    public static String assertStrNotEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new RuntimeException("str is null or empty");
        } else {
            return str;
        }
    }
}
