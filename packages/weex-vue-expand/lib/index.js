'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _uweex = require('./uweex');

var _uweex2 = _interopRequireDefault(_uweex);

var _utils = require('./lib/utils');

var _utils2 = _interopRequireDefault(_utils);

var _router = require('./plugins/router');

var _router2 = _interopRequireDefault(_router);

var _bridge = require('./plugins/router/bridge');

var _bridge2 = _interopRequireDefault(_bridge);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_uweex2.default.router = _router2.default;
_uweex2.default.bridge = _bridge2.default;
_uweex2.default.utils = _utils2.default;
exports.default = _uweex2.default;