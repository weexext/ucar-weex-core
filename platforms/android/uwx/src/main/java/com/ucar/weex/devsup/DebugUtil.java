package com.ucar.weex.devsup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.ucar.weex.UEnvironment;
import com.ucar.weex.devsup.view.FloatView;
import com.ucar.weex.utils.permission.PermissionHelper;


/**
 * Created by chenxi.cui on 2017/3/29.
 */

public class DebugUtil {
    private static FloatView floatView;
    private static PermissionHelper permissionHelper;

    public static void onPause(Activity activity) {
        if (UEnvironment.isDebug()) {
            if (floatView != null) {
                floatView.hide();
            }
        }
    }

    public static void onResume(final Activity activity) {
        if (UEnvironment.isDebug()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionHelper == null) {
                    permissionHelper = new PermissionHelper();
                }
                permissionHelper.applyPermission(activity, new PermissionHelper.IPermission() {
                    @Override
                    public void onConfirm() {
                        if (floatView == null) {
                            floatView = new FloatView(activity);
                            floatView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activity.startActivity(new Intent(activity, WXDebugActivity.class));
                                }
                            });
                        }
                        floatView.show();
                    }

                    @Override
                    public void onCancel() {

                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                if (floatView == null) {
                    floatView = new FloatView(activity);
                    floatView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.startActivity(new Intent(activity, WXDebugActivity.class));
                        }
                    });
                }
                floatView.show();
            }

        }
    }

}
