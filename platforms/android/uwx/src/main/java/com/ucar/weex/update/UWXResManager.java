package com.ucar.weex.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ucar.weex.UWXApplication;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.okhttp.CommonOkHttpClient;
import com.ucar.weex.okhttp.listerner.DisposeDataHandle;
import com.ucar.weex.okhttp.listerner.DisposeDataListener;
import com.ucar.weex.okhttp.request.CommonRequest;
import com.ucar.weex.okhttp.request.RequestParams;
import com.ucar.weex.utils.AppUtil;
import com.ucar.weex.utils.ArrayUtils;
import com.ucar.weex.utils.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by zhoucheng on 2017/6/14.
 */

public class UWXResManager {
    public static final String TAG = UWXResManager.class.getSimpleName();
    private String WX_DATA_PATH = "";
    private static UWXResManager instance;
    private WXPackageInfo lastPackageInfo;
    private SharedPreferences preferences;
    private WXPackageInfo newPackageinfo;
    private String server_url = "http://fcardownloadtest.10101111.com/fcarapp/upgrade/getUpgradeInfo";
    private List<WXPackageInfo> packageInfos;

    public void setServerUrl(String server_url) {
        this.server_url = server_url;
    }

    public String getServerUrl() {
        return server_url;
    }

    public static UWXResManager getInstance() {
        if (instance == null) {
            instance = new UWXResManager();
        }
        return instance;
    }

    private void initManager() {
        if (TextUtils.isEmpty(this.WX_DATA_PATH)) {
            this.WX_DATA_PATH = Storage.getAppFileDir(UWXApplication.getsApplication()) + "/weexfile/";
            File f = new File(this.WX_DATA_PATH);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
        this.preferences = UWXApplication.getsApplication().getSharedPreferences("ucar_wx_res", 0);
        this.iniWXInfo();
    }

    private void iniWXInfo() {
        String packageJson = preferences.getString("wx_res_info", "");
        try {
            packageInfos = JSON.parseArray(packageJson, WXPackageInfo.class);
            this.lastPackageInfo = !ArrayUtils.isEmpty(packageInfos) ? packageInfos.get(0) : null;
        } catch (JSONException e) {
            packageInfos = null;
            UWLog.e(TAG, e.getMessage());
        }
    }

    public List<WXPackageInfo> getPackageInfos() {
        return packageInfos;
    }

    private void saveWXInfo(WXPackageInfo info) {
        if (packageInfos == null) {
            packageInfos = new ArrayList<>();
        }
        packageInfos.add(0, info);
        SharedPreferences.Editor e1 = this.preferences.edit();
        e1.putString("wx_res_info", JSON.toJSONString(packageInfos));
        e1.apply();
    }

    public String getWXResPath() {
        if (lastPackageInfo != null) {
            return WX_DATA_PATH + lastPackageInfo.path + "/";
        }
        return WX_DATA_PATH;
    }

    private UWXResManager() {
        this.initManager();
    }

    public interface addWXResFromAssertListener {
        void resFromAssertListener(boolean success, String des, WXPackageInfo wxPackageInfo);
    }

    /**
     * @param context
     * @param assetFileName
     */
    public boolean addWXResFromAssert(final Context context, final String assetFileName) {
        return checkAndCopy(context, assetFileName, null);
    }

    public void asyAddWXResFromAssert(final Context context, final String assetFileName, final addWXResFromAssertListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkAndCopy(context, assetFileName, listener);
            }
        }).start();
    }

    private boolean checkAndCopy(Context context, String assetFileName, addWXResFromAssertListener listener) {
        boolean success;
        String des;
        if (!TextUtils.isEmpty(assetFileName)) {
            UWLog.d(TAG, "assetFileName" + assetFileName);
            if (check(context, assetFileName)) {
                if (newPackageinfo == null) {
                    String assetFileToStr = FileUtils.getAssetFileToStr(context.getAssets(), assetFileName + ".json");
                    if (TextUtils.isEmpty(assetFileToStr)) {
                        UWLog.e(TAG, "up包解析有问题");
                    }
                    newPackageinfo = JSON.parseObject(assetFileToStr, WXPackageInfo.class);
                }
                if (this.unzipAssetsZip(context, assetFileName)) {
                    UWLog.d(TAG, "解压Assets文件成功");
                    success = true;
                    des = "成功";
                    this.lastPackageInfo = newPackageinfo;
                    saveWXInfo(newPackageinfo);
                } else {
                    success = false;
                    des = "解压Assets失败";
                    UWLog.e(TAG, "解压Assets失败");
                }
            } else {
                success = true;
                des = "资源已经最新";
                UWLog.d(TAG, "资源已经最新");
            }
        } else {
            success = false;
            des = "assets文件找不到";
        }
        clearFile(newPackageinfo.path);
        if (listener != null) {
            listener.resFromAssertListener(success, des, newPackageinfo);
        }
        return success;
    }

    private void clearFile(String extName) {
        File file = new File(WX_DATA_PATH);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (!ArrayUtils.isEmpty(files)) {
                    for (File file1 : files) {
                        if (file1.exists() && file1.isDirectory() && extName != null && extName.equals(file1.getName())) {
                            continue;
                        } else {
                            deleteFile(file1);
                        }
                    }
                }
            }
        }
    }

    /**
     * 保留最近两个
     */
    private void delOldPackage() {
        if (!ArrayUtils.isEmpty(packageInfos) && packageInfos.size() > 2) {
            int size = packageInfos.size();
            for (int i = 2; i < size; i++) {
                WXPackageInfo info = packageInfos.get(i);
                delPackageByName(info.path);
            }
        }
    }

    private void delPackageByName(String path) {
        File dir = new File(WX_DATA_PATH + path);
        if (dir.exists()) {
            deleteFile(dir);
            UWLog.d(TAG, "del old assets");
        }
    }

    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

    /**
     * name  id_version_time.zip
     * ucar_11_20170401161448.zip
     *
     * @param weexFileName
     * @return
     */
    private boolean check(Context context, String weexFileName) {

        //首次拷贝
        if (this.lastPackageInfo == null || this.lastPackageInfo.path == null) {
            UWLog.e(TAG, "第一次copy asset2sdcard");
            return true;
        }
        String assetFileToStr = FileUtils.getAssetFileToStr(context.getAssets(), weexFileName + ".json");
        if (TextUtils.isEmpty(assetFileToStr)) {
            UWLog.e(TAG, "up包解析有问题");
            return false;
        }
        newPackageinfo = JSON.parseObject(assetFileToStr, WXPackageInfo.class);

        if (newPackageinfo == null) {
            UWLog.e(TAG, "up包解析有问题");
            return false;
        }

        if (newPackageinfo.versionCodeAndroid > this.lastPackageInfo.versionCodeAndroid) {
            UWLog.e(TAG, "asset ver >sdcard ver");
            return true;
        }
//        if (Long.parseLong(newPackageinfo.time) > Long.parseLong(this.lastPackageInfo.time) &&
//                !TextUtils.equals(newPackageinfo.md5, this.lastPackageInfo.md5)
//                ) {
//            UWLog.e(TAG, "asset文件有变化且时间最新");
//            return true;
//        }
        File file = new File(WX_DATA_PATH, newPackageinfo.path);
        if (!file.exists()) {
            UWLog.e(TAG, "sdcard文件已经损坏");
            return true;
        }
        return false;
    }


    /**
     * @param context
     * @param assertName
     * @return
     */
    private boolean unzipAssetsZip(Context context, String assertName) {
        File file = new File(WX_DATA_PATH, newPackageinfo.path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream in = context.getAssets().open(assertName + ".zip");
            boolean success = FileUtils.unpackZip(in, file);
            return success;
        } catch (Exception e) {
            UWLog.e(TAG, e.getMessage());
        }
        return false;
    }

    /**
     * @param inFile
     * @return
     */
    private boolean unzipSdcardZip(String inFile, String path) {
        File outfile = new File(WX_DATA_PATH, path);
        if (!outfile.exists()) {
            try {
                outfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File infile = new File(inFile);
        if (!infile.exists()) {
            return false;
        }
        try {
            InputStream in = new FileInputStream(infile);
            boolean success = FileUtils.unpackZip(in, outfile);
            return success;
        } catch (Exception e) {
            UWLog.e(TAG, e.getMessage());
        }
        return false;
    }

    public void checkUpdate(final CheckUpdateCallback callBack) {
        if (this.lastPackageInfo != null) {
            RequestParams params = new RequestParams();
            params.put("appId", lastPackageInfo.appIdAndroid);
            params.put("appType", "1");
            params.put("versionName", lastPackageInfo.versionNameAndroid);
            params.put("versionCode", lastPackageInfo.versionCodeAndroid + "");
            CommonOkHttpClient.get(CommonRequest.createGetRequest(server_url, params),
                    new DisposeDataHandle(new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            //检查
                            UpdateResult result = null;
                            try {
                                result = JSON.parseObject((String) responseObj, UpdateResult.class);
                            } catch (JSONException e) {
                                callBack.callback(1, e.getMessage(), null);
                                return;
                            }
                            if (result == null || result.code != 1) {
                                callBack.callback(1, "网络请求失败", null);
                                return;
                            }
                            if (result.code == 1 && result.content.upgrade && !result.content.totalUpgrade) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                String time = formatter.format(curDate);
                                final WXPackageInfo _updateInfo = new WXPackageInfo();
                                _updateInfo.versionDes = result.content.upgradeMsg;
                                _updateInfo.versionNameAndroid = result.content.versionName;
                                _updateInfo.versionCodeAndroid = result.content.versionCode;
                                _updateInfo.time = time;
                                _updateInfo.path = lastPackageInfo.appName + _updateInfo.versionNameAndroid + _updateInfo.time;
                                _updateInfo.path = _updateInfo.path.replace(".", "");
                                //更新
                                final String updateFile = WX_DATA_PATH + _updateInfo.path + ".zip";
//                            UWLog.d(TAG, "updateInfo=" + JSON.toJSONString(result.content.packageInfo));
                                UWLog.d(TAG, "updateFile=" + updateFile);
                                UWLog.d(TAG, "updateUrl =" + result.content.downloadUrl);

                                CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(result.content.downloadUrl, null), new DisposeDataHandle(new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        //解压
                                        boolean b = unzipSdcardZip(updateFile, _updateInfo.path);
                                        if (b) {
                                            saveWXInfo(_updateInfo);
                                            if (callBack != null) {
                                                callBack.callback(0, "更新成功", _updateInfo);
                                            }
//                                            clearFile(_updateInfo.path);
                                        }else {
                                            if (callBack != null) {
                                                callBack.callback(0, "更新失败", _updateInfo);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Object reasonObj) {
                                        callBack.callback(1, "网络请求失败", null);
                                        UWLog.d(TAG, "onFailure: 下载文件,网络请求失败");
                                    }
                                }, updateFile));
                            } else {
                                callBack.callback(1, "不需要升级", null);
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            callBack.callback(1, "网络请求失败", null);
                            UWLog.d(TAG, "onFailure: post请求,网络请求失败");
                        }
                    }, UpdateResult.class));
        }
    }

    public interface CheckUpdateCallback {
        void callback(int code, String msg, WXPackageInfo info);
    }
}
