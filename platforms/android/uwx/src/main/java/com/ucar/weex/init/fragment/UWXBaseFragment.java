package com.ucar.weex.init.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.ucar.weex.R;
import com.ucar.weex.commons.util.CommonUtils;
import com.ucar.weex.commons.util.ShakeDetector;
import com.ucar.weex.devsup.UWXEnvManager;
import com.ucar.weex.devsup.WXDebugActivity;
import com.ucar.weex.init.utils.ArgumentsUtil;

/**
 * Created by chenxi.cui on 2017/8/7.
 */

public class UWXBaseFragment extends Fragment implements IWXRenderListener {
    private FrameLayout rootView;
    public Bundle myBundle;
    private WXSDKInstance mWXSDKInstance;
    private ShakeDetector mShakeDetector;
    private boolean mIsDevSupportEnabled = WXEnvironment.isApkDebugable();
    private boolean mIsShakeDetectorStarted;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (myBundle == null) {
            myBundle = new Bundle();
        }
    }

    public void setUrl(String url) {
        myBundle.putString("_jsBundle", UWXEnvManager.getJSBundleHost() + url);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.wx_base_fragment, null);
        return rootView;
    }

    String tempUrl;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWXSDKInstance = new WXSDKInstance(getContext());
        mWXSDKInstance.registerRenderListener(this);
        String jsBundle = myBundle.getString("_jsBundle");
        JSONObject jsonObject = ArgumentsUtil.fromBundle(myBundle);

        if (jsonObject != null) {
            String param = JSON.toJSONString(jsonObject);
            param = Uri.encode(param);
            if (jsBundle.contains("?")) {
                tempUrl = jsBundle + "&params=" + param;
            } else {
                tempUrl = jsBundle + "?params=" + param;
            }
        } else {
            tempUrl = jsBundle;
        }

        render(tempUrl);
        if (mIsDevSupportEnabled) {
            mShakeDetector = new ShakeDetector(new ShakeDetector.ShakeListener() {
                @Override
                public void onShake() {
                    showDevOptionsDialog();
                }
            });
        }
    }

    private void render(String tempUrl) {
        mWXSDKInstance.renderByUrl(
                "",
                tempUrl,
                null,
                null,
                CommonUtils.getDisplayWidth(getActivity()),
                CommonUtils.getDisplayHeight(getActivity()),
                WXRenderStrategy.APPEND_ASYNC);
    }

    private void showDevOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("UCAR-WEEX");
        //    指定下拉列表的显示数据
        final String[] cities = {"设置调试", "页面刷新"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        startActivity(new Intent(getActivity(), WXDebugActivity.class));
                        break;
                    case 1:
                        createWeexInstance();
                        render(tempUrl);
                        break;
                    default:
                        startActivity(new Intent(getActivity(), WXDebugActivity.class));
                        break;
                }
            }
        });
        builder.show();
    }

    protected void createWeexInstance() {
        destoryWeexInstance();
        mWXSDKInstance = new WXSDKInstance(getContext());
        mWXSDKInstance.registerRenderListener(this);
    }

    protected void destoryWeexInstance() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.registerRenderListener(null);
            mWXSDKInstance.destroy();
            mWXSDKInstance = null;
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        rootView.removeAllViews();
        rootView.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
        if (!mIsShakeDetectorStarted && mShakeDetector != null) {
            mShakeDetector.start((SensorManager) getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE));
            mIsShakeDetectorStarted = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
        if (mIsShakeDetectorStarted && mShakeDetector != null) {
            mShakeDetector.stop();
            mIsShakeDetectorStarted = false;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
        if (mShakeDetector != null) {
            mShakeDetector.stop();
        }
    }
}
