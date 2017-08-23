package com.ucar.weex.init.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXRenderErrorCode;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.ui.component.WXComponent;
import com.ucar.weex.R;
import com.ucar.weex.UWXSDKManager;
import com.ucar.weex.commons.util.ShakeDetector;
import com.ucar.weex.constants.UConstants;
import com.ucar.weex.devsup.WXDebugActivity;
import com.ucar.weex.init.model.UWXBundleInfo;
import com.ucar.weex.init.utils.UWLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxi.cui on 2017/7/12.
 */
public class UWXFrameBaseActivity extends UWXBaseActivity {
    private UWXBundleInfo wxInfo;
    private RelativeLayout rlErr;
    private TextView btnReload;
    private TextView tvErr;
    private FrameLayout flNavBar;
    private boolean isHasNavBar;
    private boolean mIsDevSupportEnabled = WXEnvironment.isApkDebugable();
    private ShakeDetector mShakeDetector;
    private boolean mIsShakeDetectorStarted = false;

    public UWXFrameBaseActivity() {

    }

    public void onCreate(Bundle savedInstanceState) {
        this.initData(savedInstanceState);
        super.onCreate(savedInstanceState);
        this.initView();
        this.setData();
        if (mIsDevSupportEnabled) {
            mShakeDetector = new ShakeDetector(new ShakeDetector.ShakeListener() {
                @Override
                public void onShake() {
                    showDevOptionsDialog();
                }
            });
        }
    }


    public FrameLayout getFlNavBar() {
        return flNavBar;
    }

    private void initView() {
        this.setContentView(R.layout.wx_base_activity);
        mContainer = (ViewGroup) findViewById(R.id.wx_root);
        flNavBar = (FrameLayout) findViewById(R.id.fl_nav_bar);
        if (rlErr != null) {
            rlErr.setVisibility(View.GONE);
        }
    }

    private void initData(Bundle saveInstanceState) {
        this.wxInfo = new UWXBundleInfo();
        Bundle bundle;
        if (saveInstanceState != null) {
            bundle = saveInstanceState;
        } else {
            bundle = this.getIntent().getExtras();
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.wxInfo.setModule(bundle.getString("module"));
        this.wxInfo.setUrl(bundle.getString("url"));
        this.wxInfo.setParams(bundle.getString("params"));
        isHasNavBar = UWXSDKManager.getActivityNavBarSetter() != null && this.wxInfo.getNavBar() != null;
        setTheme(R.style.wx_theme_app);
//        setTheme(isHasNavBar ? R.style.wx_theme_app : R.style.wx_theme_translucent);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent == null) {
            return;
        }
        if (intent.getStringExtra("params") != null) {
            String params = intent.getStringExtra("params");
            String backTag = intent.getStringExtra("backTag");
            if (!TextUtils.isEmpty(params)) {
                WXComponent comp = mInstance.getRootComponent();
                if (comp != null) {
                    WXEvent events = comp.getDomObject().getEvents();
                    boolean hasActive = events.contains(UConstants.Event.ACTIVED);
                    if (hasActive) {
//                        JSONObject jsonObject = JSON.parseObject(params);
//                        Object param = null;
//                        if (jsonObject.containsKey("param")) {
//                            param = jsonObject.get("param");
                            Map<String, Object> data = new HashMap<>();
                            data.put("param",params);
                            data.put("tagCode", backTag);
                            WXBridgeManager.getInstance().fireEvent(mInstance.getInstanceId(), comp.getRef(), UConstants.Event.ACTIVED, data, null);
//                        }
                    }
                }
            }
        }
    }

    private void setData() {
        setNavBar(this.wxInfo);
        if (!TextUtils.isEmpty(this.wxInfo.getUrlParam())) {
            renderPageByURL(this.wxInfo.getUrlParam(), "");
        } else {
            this.finish();
        }
    }


    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        super.onException(instance, errCode, msg);
        if (!WXEnvironment.isApkDebugable()) {
            Toast.makeText(this, "页面加载失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        ViewStub stub = null;
        if (stub == null) {
            stub = (ViewStub) findViewById(R.id.view_stub_err);
            stub.inflate();
            rlErr = (RelativeLayout) findViewById(R.id.rl_err);
            tvErr = (TextView) findViewById(R.id.tv_err);
            btnReload = (TextView) findViewById(R.id.btn_reload);
        }
        rlErr.setVisibility(View.VISIBLE);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWeexInstance();
                renderPage();
            }
        });
        if (TextUtils.equals(errCode, WXRenderErrorCode.WX_NETWORK_ERROR)) {
            tvErr.setText("Network Error!\n1.Make sure you use the command \"npm run serve\"\n" +
                    "        launched local service\n2.Make sure you modify \"your_current_ip\" to your local IP in\n" +
                    "        \"UWXMainActivity\"");
        } else {
            tvErr.setText("render error:\n" + msg);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        super.onRenderSuccess(instance, width, height);
        if (rlErr != null) {
            rlErr.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {
        super.onViewCreated(wxsdkInstance, view);
        WXComponent comp = mInstance.getRootComponent();
        if (comp != null) {
            WXEvent events = comp.getDomObject().getEvents();
            boolean hasReady = events.contains(UConstants.Event.READY);
            if (hasReady) {
                Map<String, Object> data = new HashMap<>();
                data.put("param", wxInfo.getParam());
                WXBridgeManager.getInstance().fireEvent(mInstance.getInstanceId(), comp.getRef(), UConstants.Event.READY, data, null);
            }
        }
//        if (!isHasNavBar) {
//            setTranslateAnimation(getContainer());
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WXComponent comp = mInstance.getRootComponent();
        if (comp != null) {
            WXEvent events = comp.getDomObject().getEvents();
            boolean hasDeactived = events.contains(UConstants.Event.DEACTIVED);
            if (hasDeactived) {
                WXBridgeManager.getInstance().fireEvent(mInstance.getInstanceId(), comp.getRef(), UConstants.Event.DEACTIVED, null, null);
            }
        }
        if (mIsShakeDetectorStarted && mShakeDetector != null) {
            mShakeDetector.stop();
            mIsShakeDetectorStarted = false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!mIsShakeDetectorStarted && mShakeDetector != null) {
            mShakeDetector.start((SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE));
            mIsShakeDetectorStarted = true;
        }
    }



    private void setTranslateAnimation(View view) {
        UWLog.v("FrameBaseActivity>start Animation():" + System.currentTimeMillis());
        if (view != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation((float) this.getResources().getDisplayMetrics().widthPixels, 0.0F, 0.0F, 0.0F);
            translateAnimation.setDuration(200L);
            view.startAnimation(translateAnimation);
            view.setVisibility(View.VISIBLE);
            this.mContainer.setBackgroundColor(-1);
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    UWXFrameBaseActivity.this.mContainer.setBackgroundColor(-1);
                }
            }, 220L);
        }
    }

    @Override
    public void finish() {
        super.finish();
//        if (!isHasNavBar) {
//            overridePendingTransition(R.anim.wx_back_left_in_show, R.anim.wx_back_right_out_dismiss);
//        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("module", this.wxInfo.getModule());
        outState.putString("params", this.wxInfo.getParams());
        outState.putString("url", this.wxInfo.getUrl());
//        outState.putBundle("initProps", this.wxInfo.initProps);
        super.onSaveInstanceState(outState);
    }


    public void onBackPressed() {
        WXComponent comp = mInstance.getRootComponent();
        if (comp != null) {
            WXEvent events = comp.getDomObject().getEvents();
            boolean hasBack = events.contains(UConstants.Event.ONANDROIDBACK);
            if (hasBack) {
                WXBridgeManager.getInstance().fireEvent(mInstance.getInstanceId(), comp.getRef(), UConstants.Event.ONANDROIDBACK, null, null);
                return;
            }else {
                super.onBackPressed();
            }
        }
        super.onBackPressed();
    }


    public void setNavBar(UWXBundleInfo wxInfo) {
        if (isHasNavBar) {
            flNavBar.setVisibility(View.VISIBLE);
            UWXSDKManager.getActivityNavBarSetter().setNavBar(flNavBar, wxInfo.getNavBar());
        } else {
            flNavBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mShakeDetector != null) {
            mShakeDetector.stop();
        }
    }
    private void showDevOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UCAR-WEEX");
//        builder.setIcon(R.drawable.ic_launcher);
//        builder.setTitle("调试");
        //    指定下拉列表的显示数据
        final String[] cities = {"设置调试","页面刷新"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which){
                    case 0:
                        startActivity(new Intent(UWXFrameBaseActivity.this, WXDebugActivity.class));
                        break;
                    case 1:
                        createWeexInstance();
                        renderPage();
                        break;
                    default:
                        startActivity(new Intent(UWXFrameBaseActivity.this, WXDebugActivity.class));
                        break;
                }
            }
        });
        builder.show();
    }

}
