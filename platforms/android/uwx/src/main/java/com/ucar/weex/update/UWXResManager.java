package com.ucar.weex.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
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
import com.ucar.weex.utils.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by zhoucheng on 2017/6/14.
 */

public class UWXResManager {
    public static final String TAG = UWXResManager.class.getSimpleName();
    private static final String ZIP_FILE_CONTENT_LENGTH = "ZIP_FILE_CONTENT_LENGTH";
    private String WEEX_DATA_PATH = "";
    private static UWXResManager instance;
    private WXPackageInfo wxPackageInfo;
    private SharedPreferences preferences;
    private CheckUpdateCallback callBack;
    private WXPackageInfo _weexPackageinfo;
    private String server_url = "http://10.99.23.142:3000/ucarweex/";

    public static UWXResManager getInstance() {
        if (instance == null) {
            instance = new UWXResManager();
        }
        return instance;
    }

    private void initManager() {
        if (TextUtils.isEmpty(this.WEEX_DATA_PATH)) {
            this.WEEX_DATA_PATH = Storage.getAppFileDir(UWXEnvironment.getsApplication()) + "/weexfile/";
            File f = new File(this.WEEX_DATA_PATH);
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
            this.wxPackageInfo = JSON.parseObject(packageJson, WXPackageInfo.class);
        } catch (JSONException e) {
            UWLog.e(TAG, e.getMessage());
        }
    }

    private void saveWXInfo(WXPackageInfo info) {
        SharedPreferences.Editor e1 = this.preferences.edit();
        e1.putString("wx_res_info", JSON.toJSONString(info));
        e1.apply();
    }

    public String getWXResPath() {
        if (wxPackageInfo != null) {
            return WEEX_DATA_PATH + wxPackageInfo.path + "/";
        }
        return WEEX_DATA_PATH;
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
                if (_weexPackageinfo == null) {
                    String assetFileToStr = FileUtils.getAssetFileToStr(context.getAssets(), assetFileName + ".json");
                    if (TextUtils.isEmpty(assetFileToStr)) {
                        UWLog.e(TAG, "up包解析有问题");
                        return;
                    }
                    _weexPackageinfo = JSON.parseObject(assetFileToStr, WXPackageInfo.class);
                }
                if (this.unzipAssetsZip(context, assetFileName)) {
                    UWLog.d(TAG, "解压Assets文件成功");
                    this.wxPackageInfo = _weexPackageinfo;
                    saveWXInfo(_weexPackageinfo);
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
        if (this.wxPackageInfo == null || this.wxPackageInfo.path == null) {
            UWLog.e(TAG, "第一次copy asset2sdcard");
            return true;
        }
        String assetFileToStr = FileUtils.getAssetFileToStr(context.getAssets(), weexFileName + ".json");
        if (TextUtils.isEmpty(assetFileToStr)) {
            UWLog.e(TAG, "up包解析有问题");
            return false;
        }
        _weexPackageinfo = JSON.parseObject(assetFileToStr, WXPackageInfo.class);

        if (_weexPackageinfo == null) {
            UWLog.e(TAG, "up包解析有问题");
            return false;
        }

        if (_weexPackageinfo.versionCode > this.wxPackageInfo.versionCode) {
            UWLog.e(TAG, "asset ver >sdcard ver");
            return true;
        }
        if (Long.parseLong(_weexPackageinfo.time) > Long.parseLong(this.wxPackageInfo.time) &&
                !TextUtils.equals(_weexPackageinfo.md5, this.wxPackageInfo.md5)
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
        File file = new File(WEEX_DATA_PATH, _weexPackageinfo.path);
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
        File outfile = new File(WEEX_DATA_PATH, path);
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
        this.callBack = callBack;
        if (wxPackageInfo != null) {
            RequestParams params = new RequestParams();
            params.put("appName", wxPackageInfo.appName);
            params.put("versionCode", "" + wxPackageInfo.versionCode);
            params.put("groupId", wxPackageInfo.groupId);
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
                            final String updateFile = WEEX_DATA_PATH + _updateInfo.path + ".so";
                            UWLog.d(TAG, "updateInfo" + JSON.toJSONString(result.data.packageInfo));
                            UWLog.d(TAG, "updateFile=" + updateFile);
                            UWLog.d(TAG, "updateUrl=" + result.data.url);

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
