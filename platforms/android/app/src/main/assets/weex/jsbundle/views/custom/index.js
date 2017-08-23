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
/******/ 	return __webpack_require__(__webpack_require__.s = 208);
/******/ })
/************************************************************************/
/******/ ({

/***/ 131:
/***/ (function(module, exports) {

module.exports = {
  "body": {
    "backgroundColor": "#f3f3f3",
    "flex": 1
  },
  "row": {
    "flexDirection": "row",
    "width": 750,
    "height": 100,
    "borderBottomWidth": 2,
    "borderBottomColor": "#e3e3e3",
    "borderBottomStyle": "solid",
    "paddingTop": 25,
    "paddingBottom": 25,
    "paddingLeft": 30,
    "paddingRight": 30
  },
  "row-name": {
    "marginLeft": 20,
    "alignSelf": "center",
    "flex": 1,
    "fontSize": 40,
    "color": "#000000"
  }
}

/***/ }),

/***/ 14:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(19)
)

/* script */
__vue_exports__ = __webpack_require__(16)

/* template */
var __vue_template__ = __webpack_require__(21)
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
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/include/navbar.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-6529303a"
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

/***/ 15:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(18)
)

/* script */
__vue_exports__ = __webpack_require__(17)

/* template */
var __vue_template__ = __webpack_require__(20)
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
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/include/navpage.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-43235710"
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

/***/ 16:
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
    dataRole: { default: 'navbar' },
    //导航条背景色
    backgroundColor: { default: 'black' },
    //导航条高度
    height: { default: 88 },
    //导航条标题
    title: { default: '' },
    //导航条标题颜色
    titleColor: { default: 'black' },
    //title紧邻的图片
    centerTitleImageSrc: { default: '' },
    //右侧按钮图片
    rightItemSrc: { default: '' },
    //右侧第二个按钮图片
    rightItemSiblingSrc: { default: '' },
    //右侧按钮标题
    rightItemTitle: { default: '' },
    //右侧按钮标题颜色
    rightItemColor: { default: 'black' },
    //左侧按钮图片
    leftItemSrc: { default: '' },
    //左侧第二个按钮图片
    leftItemSiblingSrc: { default: '' },
    //左侧按钮标题
    leftItemTitle: { default: '' },
    //左侧按钮颜色
    leftItemColor: { default: 'black' }
  },
  methods: {
    onclickrightitem: function onclickrightitem(e) {
      this.$emit('naviBarRightItemClick');
    },
    onclickleftitem: function onclickleftitem(e) {
      this.$emit('naviBarLeftItemClick');
    },
    onclickrightsiblingitem: function onclickrightsiblingitem(e) {
      this.$emit('naviBarRightSiblingItemClick');
    },
    onclickleftsiblingitem: function onclickleftsiblingitem(e) {
      this.$emit('naviBarLeftSiblingItemClick');
    },
    onClickCenterTitleImage: function onClickCenterTitleImage(e) {
      this.$emit('onClickCenterTitleImage');
    }
  },
  events: {
    naviBarLeftItemClick: function naviBarLeftItemClick(e) {
      //navigator.pop(()=>{})
      console.log('pop');
    }
  }
};

/***/ }),

/***/ 17:
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
//

var config = __webpack_require__(8);

module.exports = {
  components: {
    navbar: __webpack_require__(14)
  },

  props: {
    dataRole: { default: 'none' },
    backgroundColor: { default: "#385198" },
    height: { default: weex.config.env.platform == 'android' ? 100 : 128 },
    title: { default: "" },
    titleColor: { default: "#ffffff" },
    rightItemSrc: { default: '' },
    rightItemTitle: { default: '' },
    rightItemColor: { default: "#ffffff" },
    rightItemSiblingSrc: { default: '' },
    leftItemSrc: { default: config.image('icon_arrow_back.png') },
    leftItemTitle: { default: '' },
    leftItemColor: { default: "#ffffff" },
    leftItemSiblingSrc: { default: '' },
    centerTitleImageSrc: { default: '' }
  },
  methods: {
    naviBarRightItemClick: function naviBarRightItemClick(e) {
      this.$emit('naviBarRightItemClick', e);
    },
    naviBarRightSiblingItemClick: function naviBarRightSiblingItemClick(e) {
      this.$emit('naviBarRightSiblingItemClick', e);
    },
    naviBarLeftItemClick: function naviBarLeftItemClick(e) {
      this.$emit('naviBarLeftItemClick', e);
    },
    naviBarLeftSiblingItemClick: function naviBarLeftSiblingItemClick(e) {
      this.$emit('naviBarLeftSiblingItemClick', e);
    },
    onClickCenterTitleImage: function onClickCenterTitleImage(e) {
      this.$emit('onClickCenterTitleImage', e);
    }
  }
};

/***/ }),

/***/ 18:
/***/ (function(module, exports) {

module.exports = {
  "wrapper": {
    "top": 0,
    "left": 0,
    "right": 0,
    "bottom": 0,
    "width": 750
  }
}

/***/ }),

/***/ 188:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["container"]
  }, [_c('navpage', {
    attrs: {
      "title": "UCAR-WEEX",
      "leftItemTitle": "返回"
    },
    on: {
      "naviBarLeftItemClick": _vm.naviBarLeftItemClick
    }
  }), _c('div', {
    staticClass: ["body"]
  }, [_c('list', _vm._l((_vm.items), function(item) {
    return _c('cell', {
      appendAsTree: true,
      attrs: {
        "append": "tree"
      },
      on: {
        "click": function($event) {
          _vm.onItemClick(item)
        }
      }
    }, [_c('div', {
      staticClass: ["row"]
    }, [_c('text', {
      staticClass: ["row-name"]
    }, [_vm._v(_vm._s(item.name))])])])
  }))])], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 19:
/***/ (function(module, exports) {

module.exports = {
  "container": {
    "flexDirection": "row",
    "position": "fixed",
    "top": 0,
    "left": 0,
    "right": 0,
    "width": 750
  },
  "right-text-outer": {
    "position": "absolute",
    "bottom": 0,
    "right": 0,
    "minWidth": 88,
    "minHeight": 88,
    "flexDirection": "row",
    "justifyContent": "flex-end",
    "alignItems": "center"
  },
  "right-text": {
    "lineHeight": 88,
    "fontSize": 32,
    "marginRight": 20
  },
  "left-text": {
    "position": "absolute",
    "bottom": 5,
    "left": 10,
    "minWidth": 80,
    "lineHeight": 80,
    "textAlign": "center",
    "fontSize": 32,
    "marginLeft": 50
  },
  "center-title": {
    "position": "absolute",
    "bottom": 25,
    "left": 172,
    "right": 172,
    "display": "flex",
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "center-text": {
    "textAlign": "center",
    "fontSize": 36,
    "fontWeight": "bold"
  },
  "center-title-image": {
    "width": 50,
    "height": 50,
    "marginLeft": 10
  },
  "left-image": {
    "position": "absolute",
    "bottom": 20,
    "left": 28,
    "width": 50,
    "height": 50
  },
  "right-image-outer": {
    "position": "absolute",
    "bottom": 0,
    "right": 0,
    "height": 88,
    "minWidth": 88,
    "flexDirection": "row",
    "alignItems": "center",
    "justifyContent": "center"
  },
  "right-image": {
    "width": 50,
    "height": 50
  },
  "right-image-sibling": {
    "position": "absolute",
    "bottom": 20,
    "right": 100,
    "width": 50,
    "height": 50
  },
  "left-image-sibling": {
    "position": "absolute",
    "bottom": 20,
    "left": 100,
    "width": 50,
    "height": 50
  }
}

/***/ }),

/***/ 20:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["wrapper"]
  }, [_c('navbar', {
    attrs: {
      "dataRole": _vm.dataRole,
      "height": _vm.height,
      "backgroundColor": _vm.backgroundColor,
      "title": _vm.title,
      "titleColor": _vm.titleColor,
      "leftItemSrc": _vm.leftItemSrc,
      "leftItemTitle": _vm.leftItemTitle,
      "leftItemColor": _vm.leftItemColor,
      "leftItemSiblingSrc": _vm.leftItemSiblingSrc,
      "rightItemSrc": _vm.rightItemSrc,
      "rightItemTitle": _vm.rightItemTitle,
      "rightItemColor": _vm.rightItemColor,
      "rightItemSiblingSrc": _vm.rightItemSiblingSrc,
      "centerTitleImageSrc": _vm.centerTitleImageSrc
    },
    on: {
      "naviBarRightItemClick": _vm.naviBarRightItemClick,
      "naviBarRightSiblingItemClick": _vm.naviBarRightSiblingItemClick,
      "naviBarLeftItemClick": _vm.naviBarLeftItemClick,
      "naviBarLeftSiblingItemClick": _vm.naviBarLeftSiblingItemClick,
      "onClickCenterTitleImage": _vm.onClickCenterTitleImage
    }
  }), _c('div', {
    staticClass: ["wrapper"],
    style: {
      marginTop: _vm.height
    }
  })], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 208:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(131)
)

/* script */
__vue_exports__ = __webpack_require__(43)

/* template */
var __vue_template__ = __webpack_require__(188)
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
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/custom/index.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-b72d99d0"
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

/***/ 21:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["container"],
    style: {
      height: _vm.height,
      backgroundColor: _vm.backgroundColor
    },
    attrs: {
      "dataRole": _vm.dataRole
    }
  }, [(_vm.rightItemTitle) ? _c('div', {
    staticClass: ["right-text-outer"],
    on: {
      "click": _vm.onclickrightitem
    }
  }, [_c('text', {
    staticClass: ["right-text"],
    style: {
      color: _vm.rightItemColor
    },
    attrs: {
      "naviItemPosition": "right"
    }
  }, [_vm._v(_vm._s(_vm.rightItemTitle))])]) : _vm._e(), (_vm.rightItemSrc) ? _c('div', {
    staticClass: ["right-image-outer"],
    on: {
      "click": _vm.onclickrightitem
    }
  }, [_c('image', {
    staticClass: ["right-image"],
    attrs: {
      "naviItemPosition": "right",
      "src": _vm.rightItemSrc
    }
  })]) : _vm._e(), (_vm.rightItemSiblingSrc) ? _c('image', {
    staticClass: ["right-image-sibling"],
    attrs: {
      "naviItemPosition": "right",
      "src": _vm.rightItemSiblingSrc
    },
    on: {
      "click": _vm.onclickrightsiblingitem
    }
  }) : _vm._e(), (_vm.leftItemTitle) ? _c('text', {
    staticClass: ["left-text"],
    style: {
      color: _vm.leftItemColor
    },
    attrs: {
      "naviItemPosition": "left"
    },
    on: {
      "click": _vm.onclickleftitem
    }
  }, [_vm._v(_vm._s(_vm.leftItemTitle))]) : _vm._e(), (_vm.leftItemSrc) ? _c('image', {
    staticClass: ["left-image"],
    attrs: {
      "naviItemPosition": "left",
      "src": _vm.leftItemSrc
    },
    on: {
      "click": _vm.onclickleftitem
    }
  }) : _vm._e(), (_vm.leftItemSiblingSrc) ? _c('image', {
    staticClass: ["left-image-sibling"],
    attrs: {
      "naviItemPosition": "left",
      "src": _vm.leftItemSiblingSrc
    },
    on: {
      "click": _vm.onclickleftsiblingitem
    }
  }) : _vm._e(), _c('div', {
    staticClass: ["center-title"]
  }, [_c('text', {
    staticClass: ["center-text"],
    style: {
      color: _vm.titleColor
    },
    attrs: {
      "naviItemPosition": "center"
    }
  }, [_vm._v(_vm._s(_vm.title))]), (_vm.centerTitleImageSrc) ? _c('image', {
    staticClass: ["center-title-image"],
    attrs: {
      "naviItemPosition": "center",
      "src": _vm.centerTitleImageSrc
    },
    on: {
      "click": _vm.onClickCenterTitleImage
    }
  }) : _vm._e()])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 25:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__src_uweex__ = __webpack_require__(30);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__lib_utils__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__plugins_router__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__plugins_router_bridge__ = __webpack_require__(27);





__WEBPACK_IMPORTED_MODULE_0__src_uweex__["a" /* default */].router = __WEBPACK_IMPORTED_MODULE_2__plugins_router__["a" /* default */];
__WEBPACK_IMPORTED_MODULE_0__src_uweex__["a" /* default */].bridge = __WEBPACK_IMPORTED_MODULE_3__plugins_router_bridge__["a" /* default */];
__WEBPACK_IMPORTED_MODULE_0__src_uweex__["a" /* default */].utils = __WEBPACK_IMPORTED_MODULE_1__lib_utils__["a" /* default */];
/* harmony default export */ __webpack_exports__["default"] = (__WEBPACK_IMPORTED_MODULE_0__src_uweex__["a" /* default */]);


/***/ }),

/***/ 26:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export image */
function js(jsURL) {
    let bundleUrl = weex.config.bundleUrl
    let baseURL = bundleUrl.substring(0, bundleUrl.lastIndexOf("/"))
    //是否在同级目录，若不在，则需要以下处理
    let flag = jsURL.indexOf('../') !== -1
    if (flag) {
      let arr = jsURL.split('../')
      for (let index = 0; index < arr.length - 1; index++) {
          baseURL = baseURL.substring(0, baseURL.lastIndexOf('/'))
      }
      jsURL = arr[arr.length - 1]
    }
    return baseURL + '/' + jsURL
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

/**
 *
 * */
function image(imgURL) {
    // if (weex.config.env.platform == 'android') {
    //     return "assets:///image/" + imgURL;
    // } else {
    //     return "assets:///image/" + imgURL
    // }
    return "assets:///image/" + imgURL
}

/* harmony default export */ __webpack_exports__["a"] = ({
    js,
    image,
    params,
    toParams,
    getParams
});


/***/ }),

/***/ 27:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
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
/* harmony default export */ __webpack_exports__["a"] = (Bridge);


/***/ }),

/***/ 28:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__lib__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__navBar__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__lib_utils__ = __webpack_require__(9);



const _uNavigator = weex.requireModule('UNavigator')

let Router = {};
/**
 * 进入下级页面
 * */
Router.push = (options, callback) => {
    //参数添加自定义默认数值
    let url = __WEBPACK_IMPORTED_MODULE_0__lib__["a" /* default */].js(options.url)
    let param = !options.param ? {} : options.param
    let navBar = !options.navBar ? __WEBPACK_IMPORTED_MODULE_1__navBar__["a" /* default */] : __WEBPACK_IMPORTED_MODULE_2__lib_utils__["a" /* default */].extend({},__WEBPACK_IMPORTED_MODULE_1__navBar__["a" /* default */] ,options.navBar);

    let animated = !options.animated ? 'true' : options.animated
    let _options = {
        url: url,
        param: param,
        navBar: navBar,
        animated: animated
    }
    _uNavigator.push(_options, callback)
}

/**
 * 回到上级页面
 */
Router.popTo = (options, callback) => {
    //参数添加自定义默认值
    let index = !options.index ? -1 : options.index
    let tagCode = !options.tagCode ? '' : options.tagCode
    let param = !options.param ? {} : options.param
    let animated = !options.animated ? 'true' : options.animated
    let _options = {
        index: index,
        tagCode: tagCode,
        param: param,
        animated: animated
    }
    _uNavigator.pop(_options, callback)
}
/**
 * 回到上级页面
 */
Router.pop = ()=> {

    //参数添加自定义默认值
    let _options = {
        index: -1,
        animated: 'true'
    }
    _uNavigator.pop(_options, ()=> {
    })
}

/**
 *  回到当前堆栈根节点
 * */
Router.home = (options, callback)=> {
    let _options = !options ? {} : options
    _uNavigator.home(_options, callback)
}


/* harmony default export */ __webpack_exports__["a"] = (Router);


/***/ }),

/***/ 29:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
// 默认值
const DEFAULTS = {
    // [全局/页面]是否显示 navBar，默认显示
    isShow: true,
    // [页面]标题，默认空字符串
    title: '',
    // [全局/页面]颜色，默认白色
    color: '#fff',
    // [全局/页面]背景色，默认 蓝
    backgroundColor: '#3e50b5',
    // [全局/页面]左侧按钮文字，默认 '返回'
    leftButtonText: '返回',
    // [页面]右侧按钮默认文字
    rightButtonText: '',
    // [全局]点击默认不透明度
    activeOpacity: 0.6,
    // [全局]默认动画时间
    animatedDuration: 400,
    height: weex.config.env.platform == 'android' ? 100.0 : 64.0
};
/* harmony default export */ __webpack_exports__["a"] = (DEFAULTS);


/***/ }),

/***/ 30:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
let uweex = {
    appName: '闹',
};

/* harmony default export */ __webpack_exports__["a"] = (uweex);


/***/ }),

/***/ 43:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _ucarWeex = __webpack_require__(25);

var _ucarWeex2 = _interopRequireDefault(_ucarWeex);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
    components: {
        navpage: __webpack_require__(15)
    },
    data: function data() {
        return {
            items: [
            // common
            { name: 'uNavigator', page: 'pageA.js' },
            // component
            { name: 'Text', page: 'component/text.js' }, { name: 'Image', page: 'component/image.js' },
            // module
            { name: 'module/instance-api', page: 'Instance API' },
            // showcase
            { name: 'showcase/new-fashion/index', page: 'Activity' },
            // market
            { name: 'market/gcanvas', page: 'Gcanvas' }]
        };
    },

    created: function created() {
        console.log('index.vue created');
        console.log('uweex=' + _ucarWeex2.default.utils.appName);
        console.log('uweex=' + _ucarWeex2.default.utils.isString(5));
        _ucarWeex2.default.bridge.addEventListener('PAGE_INDEX', function (event) {});
    },
    methods: {
        onItemClick: function onItemClick(item) {
            _ucarWeex2.default.bridge.postGlobalEvent('test', { key: '你好' });
            var options = {
                url: item.page,
                param: {
                    'KEY_INDEX': 'VALUE_INDEX'
                },
                navBar: {
                    backgroundColor: '#000000'
                }
            };
            _ucarWeex2.default.router.push(options, function () {
                console.log(_ucarWeex2.default.appName);
            });
        }
    }

}; //
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

/***/ }),

/***/ 8:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


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

function getUrlParam() {
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
    getUrlParam: getUrlParam
};

/***/ }),

/***/ 9:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";


const TRUE = true,
    FALSE = false,
    NULL = null,
    UNDEFINED = void 0;

const __object__ = Object.prototype,
    __array__ = Array.prototype,
    toString = __object__.toString,
    slice = __array__.slice;

// Util
const class2type = {
    '[object HTMLDocument]': 'Document',
    '[object HTMLCollection]': 'NodeList',
    '[object StaticNodeList]': 'NodeList',
    '[object IXMLDOMNodeList]': 'NodeList',
    '[object DOMWindow]': 'Window',
    '[object global]': 'Window',
    'null': 'Null',
    'NaN': 'NaN',
    'undefined': 'Undefined'
};

'Boolean,Number,String,Function,Array,Date,RegExp,Window,Document,Arguments,NodeList,Null,Undefined'
    .replace(/\w+/ig, (value) => class2type[`[object ${value}]`] = value);

let getType = (obj, match) => {
    let rs = class2type[(obj === NULL || obj !== obj) ? obj :
            toString.call(obj)] ||
        (obj && obj.nodeName) || '#';
    if (obj === UNDEFINED) {
        rs = 'Undefined';
    } else if (rs.charAt(0) === '#') {
        if (obj == obj.document && obj.document != obj) {
            rs = 'Window';
        } else if (obj.nodeType === 9) {
            rs = 'Document';
        } else if (obj.callee) {
            rs = 'Arguments';
        } else if (isFinite(obj.length) && obj.item) {
            rs = 'NodeList';
        } else {
            rs = toString.call(obj).slice(8, -1);
        }
    }
    if (match) {
        return match === rs;
    }
    return rs;
}

let _isObject = (source) => getType(source, 'Object');
let _isArray = (source) => getType(source, 'Array');
let _isString = (source) => getType(source, 'String');
let _isFunction = (source) => getType(source, 'Function');
let _isNumber = (source) => getType(source, 'Number');
let _isPlainObject = (source) => getType(source, 'Object') && Object.getPrototypeOf(source) === __object__;
let _isEmptyObject = (source) => {
    try {
        return JSON.stringify(source) === "{}";
    } catch (e) {
        return FALSE;
    }
};
let _noop = () => {
};

let _sliceFn = (obj, attr, fn) => {
    let originFn = obj[attr];
    obj[attr] = function () {
        fn.apply(this, arguments);
        if (_isFunction(originFn)) {
            return originFn.apply(this, arguments);
        }
    }
};

let extend = (target, source, deep) => {
    for (let key in source) {
        if (deep && (_isPlainObject(source[key]) || _isArray(source[key]))) {
            if (_isPlainObject(source[key]) && !_isPlainObject(target[key])) {
                target[key] = {};
            }
            if (_isArray(source[key]) && !_isArray(target[key])) {
                target[key] = [];
            }
            extend(target[key], source[key], deep);
        } else if (source[key] !== UNDEFINED) {
            target[key] = source[key];
        }
    }
};

let _extend = function () {
    let deep,
        args = _makeArray(arguments),
        target = args.shift();
    if (typeof target == 'boolean') {
        deep = target;
        target = args.shift();
    }
    args.forEach((arg) => extend(target, arg, deep));
    return target;
};

let _startWith = (str, sch) => str.indexOf(sch) == 0;

let _endWith = (str, sch) => str.indexOf(sch) > -1 && str.indexOf(sch) == str.length - sch.length;


let _makeArray = (iterable) => {
    if (!iterable)
        return FALSE;
    var n = iterable.length;
    if (n === (n >>> 0)) {
        try {
            return slice.call(iterable);
        } catch (e) {
        }
    }
    return FALSE;
}

let utils = {
    appName: '闹',
    getType: getType,
    isObject: _isObject,
    isArray: _isArray,
    isString: _isString,
    isFunction: _isFunction,
    isNumber: _isNumber,
    isPlainObject: _isPlainObject,
    isEmptyObject: _isEmptyObject,
    noop: _noop,
    extend: _extend,
    startWith: _startWith,
    endWith: _endWith,
    sliceFn: _sliceFn
};

/* harmony default export */ __webpack_exports__["a"] = (utils);


/***/ })

/******/ });