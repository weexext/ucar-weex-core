'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
var scheme = weex.requireModule('UScheme');
var _uGlobalEvent = weex.requireModule('UGlobalEvent');
var _globalEvent = weex.requireModule('globalEvent');
var Bridge = {
    // 发送广播
    postGlobalEvent: function postGlobalEvent(broadName, options) {
        options = !options ? {} : options;
        _uGlobalEvent.postGlobalEvent(broadName, options);
    },

    //注册广播
    addEventListener: function addEventListener(broadName, callback) {
        _globalEvent.addEventListener(broadName, callback);
    },

    //注销广播
    removeEventListener: function removeEventListener(broadName) {
        _globalEvent.removeEventListener(broadName);
    },

    // 发送 scheme
    sendScheme: function sendScheme(url, params, callback) {
        scheme.sendScheme(url, params, callback);
    }
};
exports.default = Bridge;