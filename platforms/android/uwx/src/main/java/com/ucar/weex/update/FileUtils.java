package com.ucar.weex.update;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.utils.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by chenxi.cui on 2017/8/23.
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    public static String getAssetFileToStr(AssetManager assetManager, String path) {
        BufferedReader bufferedReader = null;
        String result = "";

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(path), "UTF-8"));

            for(String ignored = null; (ignored = bufferedReader.readLine()) != null; result = result + ignored) {
                ;
            }

            result = result.trim();
        } catch (IOException var13) {
        } finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var12) {
                    ;
                }
            }

        }

        return result;
    }
    public static String readSDFile(String fileName) throws IOException {

        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte [] buffer = new byte[length];
        fis.read(buffer);

        String res = new String(buffer, "UTF-8");
        fis.close();

        return res;
    }
    private static void deleteFileAndDir(File file) {
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                deleteFileAndDir(subFile);
            }
        }
        file.delete();
    }

    public static boolean unpackZip(InputStream in, File outputDir) {
        if (outputDir.exists()) {
            deleteFileAndDir(outputDir);
        }
        outputDir.mkdirs();
        ZipInputStream zis;
        try {
            String filename;
            zis = new ZipInputStream(new BufferedInputStream(in));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                // zapis do souboru
                filename = ze.getName();
                if (ze.isDirectory()) {
                    //zipEntry是目录,则创建目录
                    filename = filename.substring(0, filename.length() - 1);
                    File folder = new File(outputDir, filename);
                    folder.mkdirs();
                    continue;
                }
                //否则创建文件,并输出文件的内容
                if (!TextUtils.isEmpty(filename) && filename.contains("/")) {
                    String tepFileName = filename.substring(0, filename.lastIndexOf('/'));
                    File folder = new File(outputDir, tepFileName);
                    folder.mkdirs();
                }
                File file = new File(outputDir + File.separator + filename);
                file.createNewFile();
                FileOutputStream fout = new FileOutputStream(file);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            UWLog.e(TAG, "解压文件失败", e);
            return false;
        }
        UWLog.d(TAG, "解压文件成功:" + outputDir.getAbsolutePath());
        return true;
    }
    public static String getWXPackageFileName(Context context , String weexRoot) {
        try {
            String[] assets = context.getResources().getAssets().list(weexRoot);
            if (!ArrayUtils.isEmpty(assets)) {
                String asset = assets[0];
                int i = asset.indexOf(".");
                if (i > 0) {
                    String rnName = asset.substring(0, i);
                    weexRoot = weexRoot + "/" + rnName;
                }else{
                    UWLog.e("weex文件不对");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            UWLog.e("weex文件不对");
        }
        return weexRoot;
    }
}
