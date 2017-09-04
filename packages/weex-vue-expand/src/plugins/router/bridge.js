const scheme = weex.requireModule('UScheme');
const _uGlobalEvent = weex.requireModule('UGlobalEvent')
const _globalEvent = weex.requireModule('globalEvent')
let Bridge = {
    // 发送广播
    postGlobalEvent(broadName, options) {
        options = !options ? {} : options
        _uGlobalEvent.postGlobalEvent(broadName, options)
    },
    //注册广播
    addEventListener(broadName, callback){
        _globalEvent.addEventListener(broadName, callback);
    },
    //注销广播
    removeEventListener(broadName){
        _globalEvent.removeEventListener(broadName);
    },
    // 发送 scheme
    sendScheme(url, params, callback) {
        scheme.sendScheme(url, params, callback);
    },
};
export default Bridge;
