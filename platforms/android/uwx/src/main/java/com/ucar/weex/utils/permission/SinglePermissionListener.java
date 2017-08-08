package com.ucar.weex.utils.permission;

/**
 */
public interface SinglePermissionListener {
    void onPermissionGranted(int requestCode, String[] permissions);
    void onPermissionDenied(int requestCode, String[] permissions);
}
