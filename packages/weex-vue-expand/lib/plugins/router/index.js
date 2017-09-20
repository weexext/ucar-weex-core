'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _lib = require('../../lib');

var _lib2 = _interopRequireDefault(_lib);

var _utils = require('../../lib/utils');

var _utils2 = _interopRequireDefault(_utils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _uNavigator = weex.requireModule('UNavigator');
// import defaultNavBar from './navBar';


var Router = {};
/**
 * 进入下级页面
 * */
Router.push = function (options, callback) {
    //参数添加自定义默认数值
  var to = !options.to? 'weex':options.to;
  var url = options.url
  if(to==='weex') {
    url = _lib2.default.js(options.url);
  }
  var param = !options.param ? {} : options.param;
  // let navBar = !options.navBar ? defaultNavBar : utils.extend({},defaultNavBar ,options.navBar);
  var animated = !options.animated ? 'true' : options.animated;
  var _options = {
    to:to,
    url: url,
    param: param,
    navBar: options.navBar,
    animated: animated
  };
  _uNavigator.push(_options, callback);
};

/**
 * 回到上级页面
 */
Router.popTo = function (options, callback) {
    //参数添加自定义默认值
    var index = !options.index ? -1 : options.index;
    var tagCode = !options.tagCode ? '' : options.tagCode;
    var param = !options.param ? {} : options.param;
    var animated = !options.animated ? 'true' : options.animated;
    var _options = {
        index: index,
        tagCode: tagCode,
        param: param,
        animated: animated
    };
    _uNavigator.pop(_options, callback);
};
/**
 * 回到上级页面
 */
Router.pop = function () {

    //参数添加自定义默认值
    var _options = {
        index: -1,
        animated: 'true'
    };
    _uNavigator.pop(_options, function () {});
};

/**
 *  回到当前堆栈根节点
 * */
Router.home = function (options, callback) {
    var _options = !options ? {} : options;
    _uNavigator.home(_options, callback);
};

exports.default = Router;