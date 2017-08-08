package com.ucar.weex.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import java.util.Iterator;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/7/26.
 */

public class AppExitUtil {
    public AppExitUtil() {
    }

    public static void restart(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(268468224);
        PendingIntent restartIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 268435456);
        AlarmManager mgr = (AlarmManager) context.getSystemService("alarm");
        mgr.set(1, System.currentTimeMillis() + 1000L, restartIntent);
        quitApp(context);
    }

    public static void quitApp(final Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            ActivityManager am = (ActivityManager) context.getSystemService("activity");
            List appTasks = am.getAppTasks();
            Iterator i$ = appTasks.iterator();

            while (i$.hasNext()) {
                ActivityManager.AppTask appTask = (ActivityManager.AppTask) i$.next();
                appTask.finishAndRemoveTask();
            }

            Process.killProcess(Process.myPid());
            am.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } else if (Build.VERSION.SDK_INT >= 11) {
            Intent activityManager = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            activityManager.addFlags('喜');
            activityManager.addFlags(268435456);
            activityManager.addFlags(67108864);
            activityManager.addFlags(65536);
            activityManager.addFlags(8388608);
            context.startActivity(activityManager);
            (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                public void run() {
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                }
            }, 300L);
        } else {
            Process.killProcess(Process.myPid());
            ActivityManager activityManager1 = (ActivityManager) context.getSystemService("activity");
            activityManager1.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        }

    }
}
