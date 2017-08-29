'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.image = image;
function js(jsURL) {
    var bundleUrl = weex.config.bundleUrl;
    var baseURL = bundleUrl.substring(0, bundleUrl.lastIndexOf("/"));
    //是否在同级目录，若不在，则需要以下处理
    var flag = jsURL.indexOf('../') !== -1;
    if (flag) {
        var arr = jsURL.split('../');
        for (var index = 0; index < arr.length - 1; index++) {
            baseURL = baseURL.substring(0, baseURL.lastIndexOf('/'));
        }
        jsURL = arr[arr.length - 1];
    }
    return baseURL + '/' + jsURL;
}

function params(key) {
    var bundleUrl = weex.config.bundleUrl;
    var reg = new RegExp('[?|&]' + key + '=([^&]+)');
    var match = bundleUrl.match(reg);
    return match && match[1];
}

function getParams() {
    var paramsJson = params('params');
    if (paramsJson) {
        return JSON.parse(paramsJson);
    }
    return '';
}
function toParams(obj) {
    var param = "";
    for (var name in obj) {
        if (typeof obj[name] != 'function') {
            param += "&" + name + "=" + encodeURI(obj[name]);
        }
    }
    return param.substring(1);
}

/**
 *
 * */
function image(imgURL) {
    // if (weex.config.env.platform == 'android') {
    //     return "assets:///image/" + imgURL;
    // } else {
    //     return "assets:///image/" + imgURL
    // }
    return "assets:///image/" + imgURL;
}

exports.default = {
    js: js,
    image: image,
    params: params,
    toParams: toParams,
    getParams: getParams
};