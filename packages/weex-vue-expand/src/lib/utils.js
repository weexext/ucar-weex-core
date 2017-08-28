

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

export default utils;
