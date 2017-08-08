package com.ucar.weex.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.ucar.weex.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 */
public class SinglePermission {

    private SinglePermissionListener singlePermissionListener;
    private int currentRequestCode = -1;
    //    private String currentPermission = "";
    private static HashMap<String, String> permissionName = new HashMap<String, String>(8);

    static {
        permissionName.put(Manifest.permission.READ_CONTACTS, "读取联系人");
        permissionName.put(Manifest.permission.READ_PHONE_STATE, "读取手机状态");
        permissionName.put(Manifest.permission.CALL_PHONE, "拨打电话");
        permissionName.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写sd卡");
        permissionName.put(Manifest.permission.WRITE_CALENDAR, "访问日历");
    }

    private String[] notHasPermission;

//    public void requestPermission(Activity activity, String permission, int requestCode, SinglePermissionListener listener) {
//        if (TextUtils.isEmpty(permission) || listener == null) {
//            return;
//        }
//        this.currentRequestCode = requestCode;
//        this.singlePermissionListener = listener;
////        this.currentPermission = permission;
//        //check if permission is granted
//        if (!hasPermission(activity, permission)) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//                showExplanation(activity, permission, null, false);
//            } else {
//                //没有权限就去申请，华为手机默认拒绝一次权限，但是还是会弹窗获取权限
//                ActivityCompat.requestPermissions(activity, new String[]{permission}, currentRequestCode);
//            }
//        } else {
//            singlePermissionListener.onPermissionGranted(currentRequestCode, permission);
//        }
//    }

    public void requestPermission(Activity activity, int requestCode, SinglePermissionListener listener, String... permissions) {
        if (ArrayUtils.isEmpty(permissions) || listener == null) {
            return;
        }
        this.currentRequestCode = requestCode;
        this.singlePermissionListener = listener;
//        this.currentPermission = permission;
        //check if permission is granted
        notHasPermission = notHasPermission(activity, permissions);
        if (!ArrayUtils.isEmpty(notHasPermission)) {

            String[] showRequestPermission = showRequestPermission(activity, permissions);
            if (!ArrayUtils.isEmpty(showRequestPermission)) {
                showExplanation(activity, showRequestPermission, null, false);
            } else {
                ActivityCompat.requestPermissions(activity, notHasPermission, currentRequestCode);
            }
        } else {
            singlePermissionListener.onPermissionGranted(currentRequestCode, permissions);
        }
    }

    public void requestPermission(Fragment fragment, int requestCode, SinglePermissionListener listener, String... permissions) {
        if (ArrayUtils.isEmpty(permissions) || listener == null || fragment.getActivity() == null) {
            return;
        }
        this.currentRequestCode = requestCode;
        this.singlePermissionListener = listener;
        notHasPermission = notHasPermission(fragment.getActivity(), permissions);
        //check if permission is granted
        if (!ArrayUtils.isEmpty(notHasPermission)) {
            String[] showRequestPermission = showRequestPermission(fragment.getActivity(), permissions);
            if (!ArrayUtils.isEmpty(showRequestPermission)) {
                showExplanation(fragment.getActivity(), permissions, fragment, true);
            } else {
                fragment.requestPermissions(permissions, currentRequestCode);
            }
        } else {
            singlePermissionListener.onPermissionGranted(currentRequestCode, permissions);
        }
    }

    /**
     * you can overwrite this method if you want
     *
     * @param activity
     * @param permissions
     * @param fragment
     * @param isFragment
     */
    protected void showExplanation(final Activity activity, final String[] permissions, final Fragment fragment, final boolean isFragment) {
        if (!activity.isFinishing()) {

            StringBuilder stringBuilder = new StringBuilder().append("你所做的操作需要以下权限\n");
            for (String permission : permissions) {
                stringBuilder.append(permissionName.containsKey(permission) ? permissionName.get(permission) : permission).toString();
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(stringBuilder.toString());
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tryAgain(activity, fragment, isFragment);
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    }


    public void tryAgain(Activity activity, Fragment fragment, boolean isFragment) {
        if (isFragment) {
            if (fragment != null) {
                fragment.requestPermissions(notHasPermission, currentRequestCode);
            }
        } else {
            if (activity != null) {
                ActivityCompat.requestPermissions(activity, notHasPermission, currentRequestCode);
            }
        }

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == currentRequestCode && !ArrayUtils.isEmpty(permissions)) {
//            if (notHasPermission[0].equals(permissions[0])) {
            if (!ArrayUtils.isEmpty(grantResults) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                singlePermissionListener.onPermissionGranted(requestCode, notHasPermission);
            } else {
                singlePermissionListener.onPermissionDenied(requestCode, notHasPermission);
            }
//            }
        }
    }

//    public boolean hasPermission(Context context, String permission) {
//        try {
//            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
//        } catch (Throwable t) {
//            QLog.e(String.format("Failure checking permission %s", permission), t);
//            return false;
//        }
//    }

    private String[] notHasPermission(Context context, String[] permissions) {
        ArrayList<String> notPermissions = new ArrayList<>();
        for (String permission : permissions) {
            try {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    notPermissions.add(permission);
                }
            } catch (Throwable t) {
                Log.e(String.format("Failure checking permission %s", permission), t.getMessage());
            }
        }
        return notPermissions.toArray(new String[]{});
    }

    private String[] showRequestPermission(Activity activity, String[] permissions) {

        ArrayList<String> showRequestPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showRequestPermission.add(permission);
            }
        }
        return showRequestPermission.toArray(new String[]{});


    }

}
