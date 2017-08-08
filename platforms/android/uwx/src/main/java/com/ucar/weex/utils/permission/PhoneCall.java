package com.ucar.weex.utils.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class PhoneCall {
    protected static PhoneCall singleInstance = null;

    public static PhoneCall getInstance() {
        if(singleInstance == null) {
            singleInstance = new PhoneCall();
        }

        return singleInstance;
    }

    private PhoneCall() {
    }

    public void processCall(Context context, String tel) {
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:"+tel)));
    }


}
