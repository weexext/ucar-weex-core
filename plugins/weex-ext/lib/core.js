import React from 'react-native'

global.React = React;

// Const

const TRUE = true,
    FALSE = false,
    NULL = null,
    UNDEFINED = void 0;

const __object__ = Object.prototype,
    __array__ = Array.prototype,
    toString = __object__.toString,
    slice = __array__.slice;

// Variable

let defaults = {
    appName: 'naive',
    globalPlugins: []
};

let plugins = {};
let viewMap = {};
let initFns = [];
let registerCallbacks = [];

let Ext = {defaults};

let cache = {}

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
let _noop = () => {};

let _sliceFn = (obj, attr, fn) => {
    let originFn = obj[attr];
    obj[attr] = function() {
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

// CustEvent

let _once = (func) => {
    let ran = FALSE,
        memo;
    return function() {
        if (ran) return memo;
        ran = TRUE;
        memo = func.apply(this, arguments);
        func = NULL;
        return memo;
    };
}

let triggerEvents = (events, args) => {
    let ev,
        i = -1,
        l = events.length,
        ret = 1;
    while (++i < l && ret) {
        ev = events[i];
        ret &= (ev.callback.apply(ev.ctx, args) !== FALSE);
    }
    return !!ret;
};

let CustEvent = {
    on(name, callback, context) {
        this._events = this._events || {};
        this._events[name] = this._events[name] || [];
        let events = this._events[name];
        events.push({
            callback: callback,
            context: context,
            ctx: context || this
        });
        return this;
    },
    once(name, callback, context) {
        let self = this;
        let once = _once(function (){
            self.off(name, once);
            callback.apply(self, arguments);
        });
        once._callback = callback;
        return this.on(name, once, context);
    },
    off(name, callback, context) {
        var retain, ev, events, names, i, l, j, k;
        if (!name && !callback && !context) {
            this._events = UNDEFINED;
            return this;
        }
        names = name ? [name] : keys(this._events);
        for (i = 0, l = names.length; i < l; i++) {
            name = names[i];
            events = this._events[name];
            if (events) {
                this._events[name] = retain = [];
                if (callback || context) {
                    for (j = 0, k = events.length; j < k; j++) {
                        ev = events[j];
                        if ((callback && callback !== ev.callback && callback !== ev.callback._callback) ||
                            (context && context !== ev.context)) {
                            retain.push(ev);
                        }
                    }
                }
                if (!retain.length) delete this._events[name];
            }
        }
        return this;
    },
    trigger(name) {
        if (!this._events) return this;
        let args = slice.call(arguments, 1),
            events = this._events[name],
            allEvents = this._events.all,
            ret = 1;
        if (events) {
            ret &= triggerEvents(events, args);
        }
        if (allEvents && ret) {
            ret &= triggerEvents(allEvents, args);
        }
        return !!ret;
    }
};

let _createEventManager = () => {
    let EM = function() {};
    _extend(EM.prototype, CustEvent);
    return new EM();
}

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

// createClass

let LifeCycles = ["componentDidMount", "componentWillUnmount", "componentWillReceiveProps", "shouldComponentUpdate", "componentWillUpdate", "componentDidUpdate"],
    EventsFns = ['on', 'off', 'once', 'trigger'];

let fixedKey = (prefix, key) => prefix + key.replace(/\w/, (a) => a.toUpperCase());

let mergeEventFn = (em, obj) => {
    EventsFns.forEach((key) => {
        obj[key] = em[key].bind(em);
    });
    return obj;
}

let fixedLogic = (context, em, React, isView) => {
    defaults.globalPlugins.concat(context.plugins || []).concat(context.constructor.plugins).filter(
        (key, index, source) => key && key[0] != '-' && source.indexOf(`-${key}`) == -1 && source.indexOf(key) == index
    ).forEach((name) => {
        if (!_isFunction(plugins[name])) return;
        plugins[name].call(em, context, context.constructor[`${name}Plugin`] || {}, React, isView);
    });

    LifeCycles.forEach((key) => {
        var originFn = context[key];
        context[key] = function () {
            let rs = key.indexOf('should') == 0 ? TRUE : NULL;
            em.trigger.apply(em, [fixedKey('before', key), this].concat(_makeArray(arguments)));
            if (_isFunction(originFn)) {
                try {
                    rs = originFn.apply(this, arguments);
                } catch(e) {
                    console.log('[QReact-ext Error]', e);
                    throw e;
                }
            }
            em.trigger.apply(em, [fixedKey('after', key), this, rs].concat(_makeArray(arguments)));
            return rs;
        }
    });

    let bindEvents = context.bindEvents || {};

    for (let key in bindEvents) {
        if (_isFunction(bindEvents[key])) {
            em.on(key, bindEvents[key].bind(context));
        }
    }

    let renderFn = context.render;

    context.render = function() {
        let rs = NULL;
        em.trigger.apply(em, ['beforeRender', this].concat(_makeArray(arguments)));
        if (_isFunction(renderFn)) {
            try {
                rs = renderFn.apply(this, arguments);
            } catch(e) {
                console.log('[QReact-ext Error]', e);
                throw e;
            }
        }
        em.trigger.apply(em, ['afterRender', this, rs].concat(_makeArray(arguments)));
        return rs;
    };
}

let createComponent = (React, isView) => {
    class ExtComp extends React.Component {
        constructor(props, context) {
            let em = _createEventManager();
            super(props, context);
            let originFn = this.componentWillMount;
            this.componentWillMount = function() {
                // this.name = isView ? this.constructor.name : NULL;
                fixedLogic(this, em, React, isView);
                let rs = NULL;
                em.trigger.apply(em, ['beforeComponentWillMount', this].concat(_makeArray(arguments)));
                if (_isFunction(originFn)) {
                    try {
                        rs = originFn.apply(this, arguments);
                    } catch(e) {
                        console.log('[QReact-ext Error]', e);
                        throw e;
                    }
                }
                em.trigger.apply(em, ['afterComponentWillMount', this, rs].concat(_makeArray(arguments)));
            }
        }
    }
    ExtComp.type = isView ? 'View' : 'Component';
    return ExtComp;
}

let register = (ExtClass, ExtClassName) => {
    let finalClass = registerCallbacks.reduce((Class, fn) => {
        return fn(
            Class,
            Class.type == 'View',
            defaults.globalPlugins.concat(Class.plugins || []).filter(
                (key, index, source) => key && key[0] != '-' && source.indexOf(`-${key}`) == -1 && source.indexOf(key) == index
            ),
            ExtClassName
        ) || Class;
    }, ExtClass);
    viewMap[ExtClassName] = finalClass;
    return finalClass;
};

let init = (rn) => {
    let react = rn || React;
    if (react) {
        cache.View = createComponent(react, TRUE);
        cache.Component = createComponent(react, FALSE);
        initFns.forEach((fn) => fn(react));
    } else {
        console.error('[QReact-Ext Error]', 'Not Found React');
    }
};

let getCache = (name) => {
    if (!cache[name]) {
        init();
    }
    return cache[name];
};

let defineStateProperty = (obj, attr, fn) => {
    Object.defineProperty(obj, attr, {
        get: fn,
        set: _noop
    });
};

defineStateProperty(Ext, 'View', () => getCache('View'));
defineStateProperty(Ext, 'Component', () => getCache('Component'));
defineStateProperty(React, 'QView', () => getCache('View'));
defineStateProperty(React, 'QComponent', () => getCache('Component'));
defineStateProperty(global, 'QView', () => getCache('View'));
defineStateProperty(global, 'QComponent', () => getCache('Component'));

Ext.init = init;

/**
 * 添加插件函数
 * @param  {String}     name             插件名
 * @param  {Function}   adapter          适配函数
 * @param  {Function}   initFn           Ext 初始化回调函数
 * @param  {Function}   registerFn       View 注册回调函数
  */
Ext.addPlugin = (name, adapter, initFn, registerFn) => {
    if (_isString(name)) {
        plugins[name] = adapter;
        if (_isFunction(initFn)) {
            initFns.push(initFn);
        }
        if (_isFunction(registerFn)) {
            registerCallbacks.push(registerFn);
        }
    }
};

Ext.register = register;

Ext.viewMap = viewMap;

Ext.utils = {
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
    createEventManager: _createEventManager,
    CustEvent: CustEvent,
    startWith: _startWith,
    endWith: _endWith,
    sliceFn : _sliceFn
};

global.Ext = React.Ext = Ext;

export default Ext
