package com.ucar.weex.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by chenxi.cui on 2017/8/23.
 */

public class Storage {
    private static int canWriteFlag = 0;

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= 8;
    }

    @TargetApi(8)
    private static File getExternalFilesDir(Context context) {
        if (hasFroyo()) {
            File dir1 = context.getExternalFilesDir((String) null);
            return dir1 != null ? dir1 : context.getFilesDir();
        } else {
            String dir = "/Android/content/" + context.getPackageName() + "/files";
            return new File(Environment.getExternalStorageDirectory(), dir);
        }
    }

    public static File getAppFileDir(Context context) {
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                File e = getExternalFilesDir(context);
                if (canWriteFlag == 0) {
                    FileOutputStream fos = null;
                    File testFile = null;

                    try {
                        String e1 = UUID.randomUUID().toString();
                        File testDir = new File(e, e1);
                        if (!testDir.exists()) {
                            testDir.mkdirs();
                        }

                        testFile = new File(testDir, e1);
                        fos = new FileOutputStream(testFile);
                        fos.write(0);
                        fos.flush();
                        canWriteFlag = 1;
                    } catch (Throwable var15) {
                        canWriteFlag = 2;
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException var14) {
                                var14.printStackTrace();
                            }

                            testFile.delete();
                            testFile.getParentFile().delete();
                        }

                    }
                }

                if (canWriteFlag == 1) {
                    return e;
                }
            }
        } catch (Throwable var17) {
            var17.printStackTrace();
        }

        return context.getFilesDir();
    }

    public static File getAppDir(Context context) {
        return getAppFileDir(context).getParentFile();
    }

    public static File getFileDir(Context context, String mOwner) {
        File dir = new File(getAppFileDir(context), mOwner);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

}
