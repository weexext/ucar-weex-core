package com.ucar.weex.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ucar.weex.UWXEnvironment;
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
import java.util.ArrayList;
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
    private String server_url = "http://10.99.23.142:3000/ucarweex/";
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
            this.WX_DATA_PATH = Storage.getAppFileDir(UWXEnvironment.getsApplication()) + "/weexfile/";
            File f = new File(this.WX_DATA_PATH);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
        this.preferences = UWXEnvironment.getsApplication().getSharedPreferences("ucar_wx_res", 0);
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

    /**
     * @param context
     * @param assetFileName
     */
    public void addWXResFromAssert(Context context, String assetFileName) {
        if (!TextUtils.isEmpty(assetFileName)) {
            UWLog.d(TAG, "assetFileName" + assetFileName);
            if (check(context, assetFileName)) {
                if (newPackageinfo == null) {
                    String assetFileToStr = FileUtils.getAssetFileToStr(context.getAssets(), assetFileName + ".json");
                    if (TextUtils.isEmpty(assetFileToStr)) {
                        UWLog.e(TAG, "up包解析有问题");
                        return;
                    }
                    newPackageinfo = JSON.parseObject(assetFileToStr, WXPackageInfo.class);
                }
                if (this.unzipAssetsZip(context, assetFileName)) {
                    UWLog.d(TAG, "解压Assets文件成功");
                    this.lastPackageInfo = newPackageinfo;
                    saveWXInfo(newPackageinfo);
                } else {
                    UWLog.e(TAG, "解压Assets失败");
                }
            } else {
                UWLog.d(TAG, "资源已经最新");
            }
        }
        delOldPackage();
    }

    /**
     * 保留最近两个
     */
    private void delOldPackage() {
        if (!ArrayUtils.isEmpty(packageInfos) || packageInfos.size() > 2) {
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
            UWLog.d(TAG ,"del old assets");
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

        if (newPackageinfo.versionCode > this.lastPackageInfo.versionCode) {
            UWLog.e(TAG, "asset ver >sdcard ver");
            return true;
        }
        if (Long.parseLong(newPackageinfo.time) > Long.parseLong(this.lastPackageInfo.time) &&
                !TextUtils.equals(newPackageinfo.md5, this.lastPackageInfo.md5)
                ) {
            UWLog.e(TAG, "asset文件有变化且时间最新");
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
            InputStream in = context.getAssets().open(assertName + ".so");
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
            params.put("appName", lastPackageInfo.appName);
            params.put("versionCode", "" + lastPackageInfo.versionCode);
            params.put("groupId", lastPackageInfo.groupId);
            params.put("platform", "android");
            params.put("nativeVer", AppUtil.getAppVersionCode(UWXEnvironment.getsApplication()) + "");
            CommonOkHttpClient.post(CommonRequest.createPostRequest(server_url, params),
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
                            if (result == null || result.bstatus.code != 0) {
                                callBack.callback(1, "网络请求失败", null);
                                return;
                            }

                            final WXPackageInfo _updateInfo = result.data.packageInfo;

                            //更新
                            final String updateFile = WX_DATA_PATH + _updateInfo.path + ".so";
                            UWLog.d(TAG, "updateInfo=" + JSON.toJSONString(result.data.packageInfo));
                            UWLog.d(TAG, "updateFile=" + updateFile);
                            UWLog.d(TAG, "updateUrl =" + result.data.url);

                            CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(result.data.url, null), new DisposeDataHandle(new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    //解压
                                    boolean b = unzipSdcardZip(updateFile, _updateInfo.path);
                                    if (b) {
                                        saveWXInfo(_updateInfo);
                                    }
                                    if (callBack != null) {
                                        callBack.callback(0, "更新成功", _updateInfo);
                                    }
                                }

                                @Override
                                public void onFailure(Object reasonObj) {
                                    callBack.callback(1, "网络请求失败", null);
                                    UWLog.d(TAG, "onFailure: 下载文件,网络请求失败");
                                }
                            }, updateFile));

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
