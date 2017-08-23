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
/******/ 	return __webpack_require__(__webpack_require__.s = 252);
/******/ })
/************************************************************************/
/******/ ({

/***/ 115:
/***/ (function(module, exports) {

module.exports = {
  "row": {
    "flexDirection": "row"
  },
  "result": {
    "textAlign": "right",
    "backgroundColor": "#666666",
    "fontSize": 40,
    "color": "#FFFFFF",
    "height": 100,
    "padding": 30,
    "margin": 5
  },
  "btn": {
    "flex": 1,
    "textAlign": "center",
    "backgroundColor": "#eeeeee",
    "fontSize": 36,
    "height": 100,
    "padding": 30,
    "margin": 5
  },
  "btn-operator": {
    "backgroundColor": "#666699",
    "fontSize": 40,
    "color": "#FFFFFF"
  }
}

/***/ }),

/***/ 169:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticStyle: {
      padding: "5px"
    }
  }, [_c('text', {
    staticClass: ["result"]
  }, [_vm._v(_vm._s(_vm.result))]), _c('div', {
    staticClass: ["row"]
  }, [_c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("1")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("2")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("3")]), _c('text', {
    staticClass: ["btn", "btn-operator"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("+")])]), _c('div', {
    staticClass: ["row"]
  }, [_c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("4")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("5")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("6")]), _c('text', {
    staticClass: ["btn", "btn-operator"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("-")])]), _c('div', {
    staticClass: ["row"]
  }, [_c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("7")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("8")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("9")]), _c('text', {
    staticClass: ["btn", "btn-operator"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("*")])]), _c('div', {
    staticClass: ["row"]
  }, [_c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v("0")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.input
    }
  }, [_vm._v(".")]), _c('text', {
    staticClass: ["btn"],
    on: {
      "click": _vm.clear
    }
  }, [_vm._v("AC")]), _c('text', {
    staticClass: ["btn", "btn-operator"],
    on: {
      "click": _vm.calculate
    }
  }, [_vm._v("=")])])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 252:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(115)
)

/* script */
__vue_exports__ = __webpack_require__(74)

/* template */
var __vue_template__ = __webpack_require__(169)
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
__vue_options__.__file = "/Users/dzc/que/ucar/ucar-weex/src/views/vue/showcase/calculator.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-5ef49f32"
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

/***/ 74:
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

var OP = ['+', '-', '*', '/'];
var modal = weex.requireModule('modal');
module.exports = {
  data: function data() {
    return {
      result: '',
      inputs: []
    };
  },
  methods: {
    input: function input(e) {
      modal.toast({ message: 'input: ' + e.target.attr.value, duration: 1 });
      var value = e.target.attr['value'];
      var inputs = this.inputs;
      var lastOne = inputs.length ? inputs[inputs.length - 1] : '';
      if (OP.indexOf(lastOne) > -1 && OP.indexOf(value) > -1) {
        return;
      }
      inputs.push(value);
      var buf = [],
          char;
      for (var i = 0; i < inputs.length; i++) {
        char = inputs[i];
        if (OP.indexOf(char) > -1) {
          char = ' ' + char + ' ';
        }
        buf.push(char);
      }
      this.result = buf.join('');
    },
    calculate: function calculate() {
      var result = eval(this.result);
      this.inputs = [result];
      this.result = result;
    },
    clear: function clear() {
      this.inputs = [];
      this.result = '';
    }
  }
};

/***/ })

/******/ });