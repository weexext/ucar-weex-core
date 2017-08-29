'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});


var TRUE = true,
    FALSE = false,
    NULL = null,
    UNDEFINED = void 0;

var __object__ = Object.prototype,
    __array__ = Array.prototype,
    toString = __object__.toString,
    slice = __array__.slice;

// Util
var class2type = {
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

'Boolean,Number,String,Function,Array,Date,RegExp,Window,Document,Arguments,NodeList,Null,Undefined'.replace(/\w+/ig, function (value) {
    return class2type['[object ' + value + ']'] = value;
});

var getType = function getType(obj, match) {
    var rs = class2type[obj === NULL || obj !== obj ? obj : toString.call(obj)] || obj && obj.nodeName || '#';
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
};

var _isObject = function _isObject(source) {
    return getType(source, 'Object');
};
var _isArray = function _isArray(source) {
    return getType(source, 'Array');
};
var _isString = function _isString(source) {
    return getType(source, 'String');
};
var _isFunction = function _isFunction(source) {
    return getType(source, 'Function');
};
var _isNumber = function _isNumber(source) {
    return getType(source, 'Number');
};
var _isPlainObject = function _isPlainObject(source) {
    return getType(source, 'Object') && Object.getPrototypeOf(source) === __object__;
};
var _isEmptyObject = function _isEmptyObject(source) {
    try {
        return JSON.stringify(source) === "{}";
    } catch (e) {
        return FALSE;
    }
};
var _noop = function _noop() {};

var _sliceFn = function _sliceFn(obj, attr, fn) {
    var originFn = obj[attr];
    obj[attr] = function () {
        fn.apply(this, arguments);
        if (_isFunction(originFn)) {
            return originFn.apply(this, arguments);
        }
    };
};

var extend = function extend(target, source, deep) {
    for (var key in source) {
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

var _extend = function _extend() {
    var deep = void 0,
        args = _makeArray(arguments),
        target = args.shift();
    if (typeof target == 'boolean') {
        deep = target;
        target = args.shift();
    }
    args.forEach(function (arg) {
        return extend(target, arg, deep);
    });
    return target;
};

var _startWith = function _startWith(str, sch) {
    return str.indexOf(sch) == 0;
};

var _endWith = function _endWith(str, sch) {
    return str.indexOf(sch) > -1 && str.indexOf(sch) == str.length - sch.length;
};

var _makeArray = function _makeArray(iterable) {
    if (!iterable) return FALSE;
    var n = iterable.length;
    if (n === n >>> 0) {
        try {
            return slice.call(iterable);
        } catch (e) {}
    }
    return FALSE;
};

var utils = {
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

exports.default = utils;