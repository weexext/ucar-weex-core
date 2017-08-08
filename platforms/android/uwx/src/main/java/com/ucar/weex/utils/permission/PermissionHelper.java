package com.ucar.weex.utils.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 */

public class PermissionHelper {

    private SinglePermission singlePermission;

    public PermissionHelper() {
        singlePermission = new SinglePermission();
    }

    public interface IPermission {
        void onConfirm();

        void onCancel();
    }

    public void applyPermission(final Activity activity, final IPermission iPermission, String... permissions) {
        singlePermission.requestPermission(activity, 16, new SinglePermissionListener() {
            @Override
            public void onPermissionGranted(int requestCode, String[] permissions) {
                if (iPermission != null) {
                    iPermission.onConfirm();
                }
            }

            @Override
            public void onPermissionDenied(int requestCode, String[] permissions) {
                PermissionToastUtils.showExternalStorageDenied(activity);
                if (iPermission != null) {
                    iPermission.onCancel();
                }
            }
        }, permissions);
    }

    public void applyPermission(final Fragment fragment, final IPermission iPermission, String... permissions) {
        singlePermission.requestPermission(fragment, 16, new SinglePermissionListener() {
            @Override
            public void onPermissionGranted(int requestCode, String[] permissions) {
                if (iPermission != null) {
                    iPermission.onConfirm();
                }
            }

            @Override
            public void onPermissionDenied(int requestCode, String[] permissions) {
                PermissionToastUtils.showExternalStorageDenied(fragment.getContext());
                if (iPermission != null) {
                    iPermission.onCancel();
                }
            }
        }, permissions);
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        singlePermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
