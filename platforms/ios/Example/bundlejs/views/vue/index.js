// { "framework": "Vue"} 

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 238);
/******/ })
/************************************************************************/
/******/ ({

/***/ 190:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('example-list', {
    attrs: {
      "items": _vm.items,
      "dir": "examples"
    }
  })
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 227:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(321)
)

/* script */
__vue_exports__ = __webpack_require__(292)

/* template */
var __vue_template__ = __webpack_require__(345)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/vue/include/example-list-item.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-372fdfca"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),

/***/ 228:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* script */
__vue_exports__ = __webpack_require__(293)

/* template */
var __vue_template__ = __webpack_require__(336)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/vue/include/example-list.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),

/***/ 230:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(333)
)

/* script */
__vue_exports__ = __webpack_require__(295)

/* template */
var __vue_template__ = __webpack_require__(357)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/vue/include/list-item.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-bbc95984"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),

/***/ 238:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* script */
__vue_exports__ = __webpack_require__(60)

/* template */
var __vue_template__ = __webpack_require__(190)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/vue/index.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__
module.exports.el = 'true'
new Vue(module.exports)


/***/ }),

/***/ 27:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
exports.getBaseURL = function (vm) {
  var bundleUrl = weex.config.bundleUrl;
  var nativeBase;
  var isAndroidAssets = bundleUrl.indexOf('your_current_IP') >= 0 || bundleUrl.indexOf('file://assets/') >= 0;
  var isiOSAssets = bundleUrl.indexOf('file:///') >= 0 && bundleUrl.indexOf('WeexDemo.app') > 0;
  if (isAndroidAssets) {
    nativeBase = 'file://assets/';
  } else if (isiOSAssets) {
    // file:///var/mobile/Containers/Bundle/Application/{id}/WeexDemo.app/
    // file:///Users/{user}/Library/Developer/CoreSimulator/Devices/{id}/data/Containers/Bundle/Application/{id}/WeexDemo.app/
    nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf('/') + 1);
  } else {
    var host = 'localhost:12580';
    var matches = /\/\/([^\/]+?)\//.exec(weex.config.bundleUrl);
    if (matches && matches.length >= 2) {
      host = matches[1];
    }
    nativeBase = 'http://' + host + '/' + vm.dir + '/build/';
  }
  var h5Base = './vue.html?page=./' + vm.dir + '/build/';
  // in Native
  var base = nativeBase;
  if ((typeof window === 'undefined' ? 'undefined' : _typeof(window)) === 'object') {
    // in Browser or WebView
    base = h5Base;
  }
  return base;
};

/***/ }),

/***/ 292:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


//
//
//
//
//
//
//
//
//
//
//
//
//

//  var event = weex.requireModule('event')
var navigator = weex.requireModule('navigator');
//    import uNavigator from '../../../utils/UNavigator'

module.exports = {
  props: {
    title: { default: '456' },
    url: { default: '' }
  },
  components: {
    listItem: __webpack_require__(230)
  },
  methods: {
    redirect: function redirect() {
      //        event.openURL(this.url)
      var bundleUrl = weex.config.bundleUrl;
      var baseURL = bundleUrl.substring(0, bundleUrl.lastIndexOf("/"));
      baseURL = baseURL.substring(0, baseURL.lastIndexOf("/"));
      var url = baseURL + '/' + this.url;
      navigator.push({ url: url }, function () {});
    }
  }
};

/***/ }),

/***/ 293:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


//
//
//
//
//
//
//
//

var getBaseURL = __webpack_require__(27).getBaseURL;
module.exports = {
  props: {
    dir: {
      default: 'examples'
    }, // examples, test ...
    items: {
      default: [{ name: 'hello', title: 'Hello World', url: '' }]
    }
  },
  components: {
    exampleListItem: __webpack_require__(227)
  },
  created: function created() {
    //      var base = getBaseURL(this)
    //      for (var i in this.items) {
    //        var item = this.items[i];
    //        if (!item.url) {
    //          item.url = base + item.name + '.js';
    //        }
    //      }
    for (var i in this.items) {
      var item = this.items[i];
      if (!item.url) {
        item.url = item.name + '.js';
      }
    }
  }
};

/***/ }),

/***/ 295:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


//
//
//
//
//
//
//
//
//
//
//

module.exports = {
  props: {
    bgColor: { default: '#ffffff' }
  },
  methods: {
    click: function click() {
      this.$emit('click');
    },
    touchstart: function touchstart() {
      // FIXME android touch
      // TODO adaptive opposite bgColor
      // this.bgColor = '#e6e6e6';
    },
    touchend: function touchend() {
      // FIXME android touchend not triggered
      // this.bgColor = '#ffffff';
    }
  }
};

/***/ }),

/***/ 321:
/***/ (function(module, exports) {

module.exports = {
  "item-txt": {
    "fontSize": 48,
    "color": "#555555"
  }
}

/***/ }),

/***/ 333:
/***/ (function(module, exports) {

module.exports = {
  "item": {
    "paddingTop": 25,
    "paddingBottom": 25,
    "paddingLeft": 35,
    "paddingRight": 35,
    "height": 160,
    "justifyContent": "center",
    "borderBottomWidth": 1,
    "borderColor": "#dddddd"
  }
}

/***/ }),

/***/ 336:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('list', _vm._l((_vm.items), function(item, i) {
    return _c('cell', {
      key: i,
      appendAsTree: true,
      attrs: {
        "append": "tree"
      }
    }, [_c('example-list-item', {
      attrs: {
        "title": item.title,
        "url": item.url
      }
    })], 1)
  }))
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 345:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('list-item', {
    on: {
      "click": _vm.redirect
    }
  }, [_c('text', {
    staticClass: ["item-txt"]
  }, [_vm._v(_vm._s(_vm.title))])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 357:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["item"],
    style: {
      backgroundColor: _vm.bgColor
    },
    on: {
      "click": _vm.click,
      "touchstart": _vm.touchstart,
      "touchend": _vm.touchend
    }
  }, [_vm._t("default")], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 60:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

//
//
//
//

module.exports = {
  data: function data() {
    var root = (typeof window === 'undefined' ? 'undefined' : _typeof(window)) === 'object' ? 'vue-web/vue' : 'vue';

    return {
      items: [
      // common
      { name: root + '/hello', title: 'Hello World' }, { name: root + '/style/index', title: 'Common Style' }, { name: root + '/animation', title: 'Animation' },

      // component
      { name: root + '/components/text', title: 'Text' }, { name: root + '/iconfont', title: 'iconfont' }, { name: root + '/components/image', title: 'Image' }, { name: root + '/components/input', title: 'Input' }, { name: root + '/components/scroller', title: 'Scroller' }, { name: root + '/components/list', title: 'List' }, { name: root + '/components/waterfall', title: 'Waterfall' }, { name: root + '/components/slider', title: 'Slider' }, { name: root + '/components/a', title: 'A' }, { name: root + '/components/video', title: 'Video' }, { name: root + '/components/countdown', title: 'Countdown' }, { name: root + '/components/marquee', title: 'Marquee' }, { name: root + '/components/web', title: 'Web' }, { name: root + '/components/navigator', title: 'Navigator' }, { name: root + '/components/tabbar', title: 'Tabbar' },

      // module
      { name: root + '/modules/instance-api', title: 'Instance API' }, { name: root + '/modules/modal', title: 'Modal' }, { name: root + '/modules/webSocket', title: 'WebSocket' }, { name: root + '/modules/stream', title: 'Stream' }, { name: root + '/modules/storage', title: 'Storage' },
      // {name: 'module/clipboard', title: 'Clipboard'}, // 0.8 , developing

      // showcase
      { name: root + '/showcase/boxshadow', title: 'boxshadow' }, { name: root + '/showcase/progress', title: 'Progress Bar' }, { name: root + '/showcase/itemlist', title: 'List (Advanced)' }, { name: root + '/showcase/calculator', title: 'Calculator' }]
    };
  },
  components: {
    exampleList: __webpack_require__(228)
  },
  created: function created() {
    var root = (typeof window === 'undefined' ? 'undefined' : _typeof(window)) === 'object' ? 'vue-web/vue' : 'vue';
    var platform = this.$getConfig().env.platform.toLowerCase();
    if (platform === 'ios') {
      this.items.push({ name: root + '/showcase/compositing', title: 'Compositing' });
    }
  }
};

/***/ })

/******/ });