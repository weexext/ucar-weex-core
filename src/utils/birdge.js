const scheme = weex.requireModule('scheme');
const globalEvent = weex.requireModule('sendGlobalEvent');

/**
 * @param url
 * @param params
 */
function sendScheme(url, params = {}) {
    return new Promise((resolve)=> {
        scheme.sendScheme(url, params, ret=> {
            resolve(ret);
        });
    })
}

/**
 * @param eventName
 * @param params
 * 接收广播
 * var globalEvent = weex.requireModule('globalEvent');
 globalEvent.addEventListener("geolocation", function (e) {
       console.log("get geolocation")
    });
 *  globalEvent.removeEventListener(String eventName)
 */
function sendGlobalEvent(eventName, params = {}) {
    globalEvent.sendGlobalEvent(eventName, params);
}

export default {
    sendScheme: sendScheme,
    sendGlobalEvent: sendGlobalEvent,
}
