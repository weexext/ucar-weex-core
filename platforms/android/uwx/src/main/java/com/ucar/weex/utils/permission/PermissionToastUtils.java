package com.ucar.weex.utils.permission;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ironman.li flight_on 2015/10/30.
 */
public class PermissionToastUtils {

    public static void showPhoneCallDenied(Context context) {
        showToast(context, "电话权限获取失败！");
    }
    public static void showExternalStorageDenied(Context context) {
        showToast(context, "读取内存卡权限获取失败！");
    }

    public static void showReadContactsDenied(Context context) {
        showToast(context, "读取通讯录权限获取失败");
    }

    public static void showReadPhoneStateDenied(Context context) {
        showToast(context, "读手机状态权限失败");
    }

    public static void  showAccessCalendarDenied(Context context){
        showToast(context,"访问日历权限获取失败");
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context , message, Toast.LENGTH_SHORT).show();
    }
}
