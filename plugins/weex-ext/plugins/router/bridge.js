
let Bridge = {
    // 发送广播
    sendBroadcast(opts) {
        callNativeAPI('QRCTBroadCastManager', 'sendBroadcast', [opts.name, opts.data, opts.hybridId || '']);
    },
    // 发送 schema
    sendSchema(opts, cb = () => {}) {
        console.warn('sendSchema 已纠正为 sendScheme，下个版本将不支持，请升级 QRN');
        callNativeAPI('QRCTJumpHandleManager', 'sendSchema', [opts.url, opts.data, opts.adrToken || '', cb]);
    },
    // 发送 scheme
    sendScheme(opts, cb = () => {}) {
        callNativeAPI('QRCTJumpHandleManager', 'sendScheme', [opts.url, opts.data, opts.adrToken || '', cb]);
    },
    // 关闭指定 RN VC
    backToReactVC(opts) {
        callNativeAPI('QRCTVCManager', 'backToReactVC', [opts.hybridId || Ext.defaults.hybridId || '', opts.index, opts.adrToken || '', {}]);
    },
    // 关闭当前 RN VC
    closeCurrentVC() {
        callNativeAPI('QRCTVCManager', 'closeCurrentVC', [{}]);
    },
    // 之行 Native 函数
    sendNativeEvents(opts) {
        callNativeAPI('QRCTNativeCallbackManager', 'sendNativeEvents', [opts.id, opts.data || {}]);
    },
    // 打开新的 VC
    openNewVC(opts) {
        callNativeAPI('QRCTVCManager', 'openNewVC', [opts.hybridId || Ext.defaults.hybridId || '', opts.moduleName || Ext.defaults.appName, opts.data || {}, opts.adrToken || '', {}]);
    },
    // 关闭安卓透明 Activity
    closeActivityAndroid(adrToken) {
        callNativeAPI('QRCTVCManager', 'closeActivity', [adrToken]);
    },
    // 右滑手势开关
    setSwipeBackEnabled(isEnabled, vcIndex, cb = () => {}) {
        callNativeAPI('QRCTVCManager', 'setSwipeBackEnabled', [isEnabled, vcIndex, cb]);
    },
};

let NativeModules = React.NativeModules;

function callNativeAPI(namespace, APIName, args) {
    let NativeModule = NativeModules[namespace];
    if (NativeModule) {
        let API = NativeModule[APIName];
        API && API.apply(NativeModule, args);
    }
}

export default Bridge;
