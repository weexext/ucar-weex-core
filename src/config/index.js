// import env from './env'
// const _globalInfo = weex.requireModule('globalInfo')

function js(jsURL) {
    let bundleUrl = weex.config.bundleUrl
    let baseURL = bundleUrl.substring(0, bundleUrl.lastIndexOf("/"))
    //是否在同级目录，若不在，则需要以下处理
    let flag = jsURL.indexOf('../') !== -1
    if (flag) {
        if (weex.config.env.platform == 'android') { //android不需要单独处理
            let arr = jsURL.split('../')
            for (let index = 0; index < arr.length - 1; index++) {
                baseURL = baseURL.substring(0, baseURL.lastIndexOf('/'))
            }
            jsURL = arr[arr.length - 1]
        } else { //iOS分情况处理
            //iOS用于处理是本地还是远程
            // let jsLoadType = _globalInfo.jsLoadType() //仅iOS需要单独处理
            let  jsLoadType ='0';
            if (jsLoadType == '1') { //远程
                let arr = jsURL.split('../')
                for (let index = 0; index < arr.length - 1; index++) {
                    baseURL = baseURL.substring(0, baseURL.lastIndexOf('/'))
                }
                jsURL = arr[arr.length - 1]
            } else {  //本地
                //截取最后的文件名及参数即可
                jsURL = jsURL.substring(jsURL.lastIndexOf('/') + 1)
            }
        }
    }
    return baseURL + '/' + jsURL
}

export function image(imgURL) {
    // return "http://images-file.oss-cn-hangzhou.aliyuncs.com/weex/jandan/1.0.1/resources/" + imgURL;
    if (weex.config.env.platform == 'android') {
        return "file:///android_asset/weex/image/" + imgURL;
    } else {
        return "file:///local:" + imgURL;
    }
}


function params(key) {
    let bundleUrl = weex.config.bundleUrl;
    let reg = new RegExp('[?|&]' + key + '=([^&]+)')
    let match = bundleUrl.match(reg)
    return match && match[1]
}

function getParams() {
    let paramsJson = params('params');
    if (paramsJson) {
        return JSON.parse(paramsJson);
    }
    return ''
}
function toParams(obj) {
    let param = ""
    for (const name in obj) {
        if (typeof obj[name] != 'function') {
            param += "&" + name + "=" + encodeURI(obj[name])
        }
    }
    return param.substring(1)
}


export default {
    js,
    image,
    params,
    toParams,
    getParams
}
