package com.ucar.weex.devsup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.ucar.weex.WXPageHelper;
import com.ucar.weex.utils.AppExitUtil;
import com.ucar.weex.R;

/**
 * Created by chenxi.cui on 2017/7/26.
 */
public class WXDebugActivity extends Activity implements View.OnClickListener {
    private TextView textBack;
    private ImageView textScan;
    private EditText editHost;
    private EditText editPort;
    private CheckBox cbSRemote;
    private CheckBox cbDebugChrome;
    private Button btnSave;
    private Button btnReset;
    private CheckBox cbLocal;
    private WXEnvDetail wxEnvDetail;
    private EditText editOpen;
    private Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_debug_activity);
        textBack = (TextView) findViewById(R.id.text_back);
        textScan = (ImageView) findViewById(R.id.text_scan);
        editHost = (EditText) findViewById(R.id.edit_host);
        editPort = (EditText) findViewById(R.id.edit_port);
        editOpen = (EditText) findViewById(R.id.edit_open);
        cbLocal = (CheckBox) findViewById(R.id.cb_local);
        cbDebugChrome = (CheckBox) findViewById(R.id.cb_debug_chrome);
        cbSRemote = (CheckBox) findViewById(R.id.cb_sRemoteDebugMode);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnReset = (Button) findViewById(R.id.btn_save_reset);
        btnOpen = (Button) findViewById(R.id.btn_open);

        textBack.setOnClickListener(this);
        textScan.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        setData();
        hideSoftInput();
    }

    /**
     * 关掉软键盘
     */
    public void hideSoftInput() {
        try {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    private void setData() {
        wxEnvDetail = WXEnvDetailHelper.getInstance().getWXEnvDetail();
        if (wxEnvDetail != null) {
            editHost.setText(wxEnvDetail.host);
            editPort.setText(wxEnvDetail.port);
            editOpen.setText(wxEnvDetail.testUrl);
            cbLocal.setChecked(!wxEnvDetail.isLaunchLocally);
            cbDebugChrome.setChecked(wxEnvDetail.debugInChrome);
            cbSRemote.setChecked(wxEnvDetail.debugRemote);
        }
    }

    private void save() {
        String host = this.editHost.getText().toString().trim();
        String port = this.editPort.getText().toString().trim();
        String testUrl = this.editOpen.getText().toString().trim();
        if (wxEnvDetail != null) {
            wxEnvDetail.host = host;
            wxEnvDetail.port = port;
            wxEnvDetail.testUrl = testUrl;
            wxEnvDetail.isLaunchLocally = !cbLocal.isChecked();
            wxEnvDetail.debugInChrome = cbDebugChrome.isChecked();
            wxEnvDetail.debugRemote = cbSRemote.isChecked();
            WXEnvDetailHelper.getInstance().saveWXEnvDetail(wxEnvDetail);
        }
    }

    @Override
    public void onClick(View v) {
        if (textBack.equals(v)) {
            finish();
        } else if (textScan.equals(v)) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            //integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(true);
            integrator.setOrientationLocked(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.setPrompt("请将条码置于取景框内扫描");
            integrator.initiateScan();
        } else if (btnSave.equals(v)) {
            save();
        } else if (btnReset.equals(v)) {
            save();
            AppExitUtil.restart(this);
        } else if (btnOpen.equals(v)) {
            String path = "";
            String page = editOpen.getText().toString().trim();
            WXPageHelper.openPageByPath(this, path + page);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                handleDecodeInternally(result.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Put up our own UI for how to handle the decoded contents.
    private void handleDecodeInternally(String code) {

        if (!TextUtils.isEmpty(code)) {
            Uri uri = Uri.parse(code);
            if (uri.getQueryParameterNames().contains("bundle")) {
                WXEnvironment.sDynamicMode = uri.getBooleanQueryParameter("debug", false);
                WXEnvironment.sDynamicUrl = uri.getQueryParameter("bundle");
                String tip = WXEnvironment.sDynamicMode ? "Has switched to Dynamic Mode" : "Has switched to Normal Mode";
                Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
                finish();
                return;
            } else if (uri.getQueryParameterNames().contains("_wx_devtool")) {
                WXEnvironment.sRemoteDebugProxyUrl = uri.getQueryParameter("_wx_devtool");
                WXEnvironment.sDebugServerConnectable = true;
                WXSDKEngine.reload();
                Toast.makeText(this, "devtool", Toast.LENGTH_SHORT).show();
                return;
            } else if (code.contains("_wx_debug")) {
                uri = Uri.parse(code);
                String debug_url = uri.getQueryParameter("_wx_debug");
                WXSDKEngine.switchDebugModel(true, debug_url);
                finish();
            } else {
                Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
//                intent.setPackage(getPackageName());
                intent.setData(Uri.parse(code));
                startActivity(intent);
            }
        }
    }
}
