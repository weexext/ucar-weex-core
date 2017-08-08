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
/******/ 	return __webpack_require__(__webpack_require__.s = 24);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (immutable) */ __webpack_exports__["image"] = image;
// import env from './env'
// const _globalInfo = weex.requireModule('globalInfo')

function js(jsURL) {
    let bundleUrl = weex.config.bundleUrl;
    let baseURL = bundleUrl.substring(0, bundleUrl.lastIndexOf("/"));
    //是否在同级目录，若不在，则需要以下处理
    let flag = jsURL.indexOf('../') !== -1;
    if (flag) {
        if (weex.config.env.platform == 'android') {
            //android不需要单独处理
            let arr = jsURL.split('../');
            for (let index = 0; index < arr.length - 1; index++) {
                baseURL = baseURL.substring(0, baseURL.lastIndexOf('/'));
            }
            jsURL = arr[arr.length - 1];
        } else {
            //iOS分情况处理
            //iOS用于处理是本地还是远程
            // let jsLoadType = _globalInfo.jsLoadType() //仅iOS需要单独处理
            let jsLoadType = '0';
            if (jsLoadType == '1') {
                //远程
                let arr = jsURL.split('../');
                for (let index = 0; index < arr.length - 1; index++) {
                    baseURL = baseURL.substring(0, baseURL.lastIndexOf('/'));
                }
                jsURL = arr[arr.length - 1];
            } else {
                //本地
                //截取最后的文件名及参数即可
                jsURL = jsURL.substring(jsURL.lastIndexOf('/') + 1);
            }
        }
    }
    return baseURL + '/' + jsURL;
}

function image(imgURL) {
    // return "http://images-file.oss-cn-hangzhou.aliyuncs.com/weex/jandan/1.0.1/resources/" + imgURL;
    if (weex.config.env.platform == 'android') {
        return "file:///android_asset/weex/image/" + imgURL;
    } else {
        return "file:///local:" + imgURL;
    }
}

function params(key) {
    let bundleUrl = weex.config.bundleUrl;
    let reg = new RegExp('[?|&]' + key + '=([^&]+)');
    let match = bundleUrl.match(reg);
    return match && match[1];
}

function getParams() {
    let paramsJson = params('params');
    if (paramsJson) {
        return JSON.parse(paramsJson);
    }
    return '';
}
function toParams(obj) {
    let param = "";
    for (const name in obj) {
        if (typeof obj[name] != 'function') {
            param += "&" + name + "=" + encodeURI(obj[name]);
        }
    }
    return param.substring(1);
}

/* harmony default export */ __webpack_exports__["default"] = ({
    js,
    image,
    params,
    toParams,
    getParams
});

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(6)
)

/* script */
__vue_exports__ = __webpack_require__(3)

/* template */
var __vue_template__ = __webpack_require__(8)
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
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(5)
)

/* script */
__vue_exports__ = __webpack_require__(4)

/* template */
var __vue_template__ = __webpack_require__(7)
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
/* 3 */
/***/ (function(module, exports) {

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
    onclickrightitem: function (e) {
      this.$emit('naviBarRightItemClick');
    },
    onclickleftitem: function (e) {
      this.$emit('naviBarLeftItemClick');
    },
    onclickrightsiblingitem: function (e) {
      this.$emit('naviBarRightSiblingItemClick');
    },
    onclickleftsiblingitem: function (e) {
      this.$emit('naviBarLeftSiblingItemClick');
    },
    onClickCenterTitleImage: function (e) {
      this.$emit('onClickCenterTitleImage');
    }
  },
  events: {
    naviBarLeftItemClick(e) {
      //navigator.pop(()=>{})
      console.log('pop');
    }
  }
};

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

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

const config = __webpack_require__(0);
module.exports = {
  components: {
    navbar: __webpack_require__(1)
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
    naviBarRightItemClick: function (e) {
      this.$emit('naviBarRightItemClick', e);
    },
    naviBarRightSiblingItemClick: function (e) {
      this.$emit('naviBarRightSiblingItemClick', e);
    },
    naviBarLeftItemClick: function (e) {
      this.$emit('naviBarLeftItemClick', e);
    },
    naviBarLeftSiblingItemClick: function (e) {
      this.$emit('naviBarLeftSiblingItemClick', e);
    },
    onClickCenterTitleImage: function (e) {
      this.$emit('onClickCenterTitleImage', e);
    }
  }
};

/***/ }),
/* 5 */
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
/* 6 */
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
/* 7 */
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
/* 8 */
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
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["container"],
    on: {
      "onAndroidBack": _vm.onAndroidBack,
      "ready": _vm.ready,
      "actived": _vm.actived,
      "deactived": _vm.deactived
    }
  }, [_c('navpage', {
    attrs: {
      "backgroundColor": "#3e50b5",
      "title": "Image"
    },
    on: {
      "naviBarLeftItemClick": _vm.naviBarLeftItemClick
    }
  }), _c('image', {
    staticStyle: {
      width: "560px",
      height: "560px"
    },
    attrs: {
      "src": "https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg"
    }
  }), _c('image', {
    staticStyle: {
      width: "560px",
      height: "560px"
    },
    attrs: {
      "src": "file:///android_asset/weex/image/test.jpg"
    }
  })], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* script */
__vue_exports__ = __webpack_require__(28)

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
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/component/image.vue"
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
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

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
    components: {
        navpage: __webpack_require__(2)
    }

};

/***/ })
/******/ ]);