package com.ucar.weex.init.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.ucar.weex.WXNative;
import com.ucar.weex.init.UWXConstant;
import com.ucar.weex.init.utils.ArgumentsUtil;
import com.ucar.weex.init.utils.UWLog;

/**
 * Created by chenxi.cui on 2017/7/12.
 */

public class WXMainActivity extends Activity  {
    private static final String OPEN = "/open";
    private static final String BIZ = "/biz";
    private static final String DEBUG = "/debug";
    private ProgressDialog progressDialog;
    private boolean isFinished;

    public WXMainActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            this.finish();
        } else {
            this.initView();
            Intent intent = this.getIntent();
            Uri uri = intent.getData();
            if(uri == null) {
                this.finish();
            } else {
//ucar://weex/open?hybridId=portal&moduleName=portal&
// url="http://10.99.44.64:12580/dist/native/views/personnel-application/personnel-application.js"
// &initProps={"param”:”xxxx",}
                String scheme = uri.getScheme();
                String host = uri.getHost();
                UWLog.v("url=" + uri.toString());
                if(!TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(host)) {
                    String path = uri.getPath();
                    String hybridId;
                    if(path.equals("/open")) {
                        hybridId = uri.getQueryParameter("hybridId");
                        String reactInstanceManager1 = uri.getQueryParameter("moduleName");
                        String viewName = uri.getQueryParameter("url");
                        String initialProps = uri.getQueryParameter("initProps");
                        if(TextUtils.isEmpty(hybridId)) {
                            this.finish();
                        } else {
                            if(TextUtils.isEmpty(reactInstanceManager1)) {
                                reactInstanceManager1 = UWXConstant.MODULE_NAME;
                            }

                            Bundle initialPropsBundle = null;

                            try {
                                JSONObject e = JSONObject.parseObject(initialProps);
                                initialPropsBundle = ArgumentsUtil.fromJsonToBundle(e);
                            } catch (Exception var13) {
                                UWLog.v(var13.getMessage());
                            }

                            if(initialPropsBundle == null) {
                                initialPropsBundle = new Bundle();
                            }

                            initialPropsBundle.putString("url", viewName);
                            WXNative.startWXActivity(this,reactInstanceManager1, initialPropsBundle);
                            this.finish();
                        }
                    } else {
//                        if(path.equals("/biz")) {
//                            hybridId = uri.getQueryParameter("hybridId");
//                            if(TextUtils.isEmpty(hybridId)) {
//                                this.setResult(0);
//                                this.finish();
//                                return;
//                            }
//
//                            WXInstanceManager reactInstanceManager = ReactContextManager.getInstance().getReactInstanceManager(hybridId);
//                            if(reactInstanceManager == null) {
//                                this.setResult(0);
//                                this.finish();
//                                return;
//                            }
//
//                            this.sendSchema(uri, reactInstanceManager.getCurrentReactContext(), intent.getExtras());
//                        } else if(path.equals("/debug")) {
//                            DbUtil.log("schema:open>debugactivity");
//                            this.startActivity(new Intent(this, RnDebugActivity.class));
//                            this.finish();
//                            return;
//                        }
//
//                        DbUtil.log("QReactMainActivity:" + uri.toString() + " host:::" + host + " path::" + path);
                    }
                } else {
                    this.finish();
                }
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.isFinished = true;
        if(this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }

        UWLog.v("WXMainActivity:onDestroy()");
    }

    private void initView() {
        this.progressDialog = new ProgressDialog(this);
    }

//    private void sendSchema(Uri uri, ReactContext reactContext, Bundle bundle) {
//        String adrToken = UUID.randomUUID().toString();
//        String callBackId = UUID.randomUUID().toString();
//        WritableMap writableMap = Arguments.createMap();
//        writableMap.putString("url", uri.toString());
//        writableMap.putString("adrToken", adrToken);
//        writableMap.putString("callbackId", callBackId);
//        if(bundle != null) {
//            writableMap.putMap("data", Arguments.fromBundle(bundle));
//        }
//
//        QNativeEventManager.getInstance().addNativeEventListener(callBackId, new OnNativeEventListener() {
//            public void onNativeEventReceived(ReadableMap readableMap) {
//                Intent intent = new Intent();
//                intent.putExtras(Arguments.toBundle(readableMap));
//                if(readableMap != null) {
//                    DbUtil.log("Main:onNativeEventReceived:::" + readableMap.toString());
//                }
//
//                QReactMainActivity.this.setResult(-1, intent);
//            }
//        });
//        QMainActivityManager.getInstance().addMainAcitity(adrToken, this);
//        ((RCTDeviceEventEmitter)reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("receiveSchema", writableMap);
//    }
}
