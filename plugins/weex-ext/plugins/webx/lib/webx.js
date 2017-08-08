let _ = React.Ext.utils;

let transformRegs = [
    /(translate|skew|scale)(X|Y)?\s*\(([^\)]+)\)/g,
    /perspective\s*\(([^\)]+)\)/g,
    /rotate(X|Y|Z|3d)?\s*\(([^\)]+)\)/g
];
let Direct3d = ['X', 'Y', 'Z'];

let ViewPressAttrs = ['onPress', 'onLongPress', 'onPressIn', 'onPressOut'];

let WindowSize = {},
    vWidth = 1,
    vHeight = 1;

let computeRule = (attr, type, num) => {
    num = parseFloat(num);
    if (!WindowSize[attr]) {
        return false;
    }
    return type == 'max' ? num >= WindowSize[attr] : num <= WindowSize[attr];
}

let mergeStyles = (styles, tag, extra) => {
    for (let key in styles) {
        let value = styles[key];
        if (_.isObject(value) && key.indexOf('@media') == 0) {
            if (key.substring(6).split(',').some((item) => {
                var flag = true;
                item.replace(/\(\s*(min|max)\-(width|height)\s*:\s*(\s*[0-9]+)(px)?\s*\)/g, function(a, b, c, d) {
                    flag &= computeRule(c, b, d);
                    return a;
                });
                return flag;
            })) {
                _.extend(styles, value, true);
            }
            delete styles[key];
        }
    }

    if (tag) {
        extra.active[tag] = {};
        for (let key in styles) {
            let value = styles[key];
            if (key == 'transform' && _.isString(value)) {
                let transforms = [];
                styles[key].replace(transformRegs[0], (a, b, c, d) => {
                    var num = parseFloat(d);
                    if (c) {
                        transforms.push({
                            [b + c] : num
                        });
                    } else if (b == 'scale') {
                        transforms.push({
                            scale : num
                        });
                    } else {
                        let s = d.split(','),
                            x = parseFloat(s[0]),
                            y = parseFloat(s[1]) || x;
                        transforms.push({
                            [b + 'X'] : x
                        });
                        transforms.push({
                            [b + 'Y'] : y
                        });
                    }
                    return '';
                });
                styles[key].replace(transformRegs[1], (a, b) => {
                    transforms.push({
                        perspective : parseFloat(b)
                    });
                    return '';
                });
                styles[key].replace(transformRegs[2], (a, b, c) => {
                    if (!b) {
                        let s = c.split(',');
                        if (s.length == 1) {
                            transforms.push({
                                rotate : c.trim()
                            });
                        } else {
                            for (let i = 1, l = s.length; i < l; i ++) {
                                if (s[i]) {
                                    transforms.push({
                                        ['rotate' + Direct3d[i]] : s[i].trim()
                                    });
                                }
                            }
                        }
                    } else if (b == '3d') {
                        let s = c.split(',');
                        if (s.length == 3) {
                            for (let i = 1, l = s.length; i < l; i ++) {
                                if (s[i]) {
                                    transforms.push({
                                        ['rotate' + Direct3d[i]] : s[i].trim()
                                    });
                                }
                            }
                        }
                    } else {
                        transforms.push({
                            ['rotate' + b] : f
                        });
                    }
                    return '';
                });
                styles.transform = transforms;
            }
            if (_.endWith(key, ':active')) {
                let activeKey = key.substring(0, key.length - 7) || tag;
                extra.active[tag][activeKey] = value;
                extra.activeList.push(activeKey);
                delete styles[key];
            }
        }
    }
}

React.Ext.addPlugin('webx', function(opts, pOpts, React) {
    let ORIGIN_CREATE_ElEMENT = React.createElement;
    let {TouchableWithoutFeedback} = React;

    opts.state = opts.state || {};

    opts.state._css = {
        active: {}
    };

    let styles = opts.styles || {};

    let extra = {
        active: {},
        activeList: []
    };

    mergeStyles(styles);

    for (let key in styles) {
        mergeStyles(styles[key], key, extra)
    }

    for (let key in extra.active) {
        opts.state._css.active[key] = false;
    }

    for (let key in styles) {
        let style = styles[key];
        for (let attr in style) {
            let value = style[attr];
            if (_.isString(value)) {
                if (_.endWith(value, 'vw')) {
                    value = parseFloat(value);
                    style[attr] = value * vWidth;
                } else if (_.endWith(value, 'vh')) {
                    value = parseFloat(value);
                    style[attr] = value * vHeight;
                }
            }
        }
    }

    opts.styles = React.StyleSheet.create(opts.styles);

    this.on('beforeRender', function(context) {
        context.createElement = function createElement(obj, options) {
            var displayName = obj.displayName;
            options = options || {};
            options.style = options.style ? [].concat(options.style) : [];
            if (_.isString(options['class'])) {
                options['class'].split(' ').reverse().forEach((name) => {
                    if (styles[name]) {
                        options.style.unshift(styles[name]);
                    }
                    if (extra.active[name]) {
                        for (let k in extra.active[name]) {
                            options.style.push(context.state._css.active[k] ? extra.active[name][k] : {})
                        }
                    }
                    if (extra.activeList.indexOf(name) > -1) {
                        let _onStartShouldSetResponder = options.onStartShouldSetResponder;
                        options.onStartShouldSetResponder = function () {
                            if (_.isFunction(_onStartShouldSetResponder)) {
                                _onStartShouldSetResponder.apply(this, arguments);
                            }
                            return true;
                        };
                        _.sliceFn(options, 'onResponderGrant', (evt) => {
                            context.setState(_.extend(context.state, {
                                _css: {
                                    active: {
                                        [name]: true
                                    }
                                }
                            }));
                        });
                        _.sliceFn(options, 'onResponderRelease', (evt) => {
                            context.setState(_.extend(context.state, {
                                _css: {
                                    active: {
                                        [name]: false
                                    }
                                }
                            }));
                        });
                    }
                });
            }
            if (styles[displayName]) {
                options.style.unshift(styles[displayName]);
            }
            for (let key in options) {
                if (_.isFunction(options[key]) && options[key].toString().indexOf('.createElement') > -1) {
                    let originFn = options[key];
                    options[key] = function() {
                        this.createElement = createElement;
                        let ret = originFn.apply(this, arguments);
                        return ret;
                    }
                }
            }
            Object.keys(options).forEach((key) => {
                if (key.indexOf('on') === 0) {
                    if (_.isFunction(options[key])) {
                        options[key] = options[key].bind(opts);
                    }
                    if (_.isString(options[key])) {
                        if (_.isFunction(opts[options[key]])) {
                            options[key] = opts[options[key]].bind(opts);
                        } else {
                            console.warn('Not Found Function: this.' + options[key]);
                            options[key] = _.noop;
                        }
                    }
                }
            });
            let ele = ORIGIN_CREATE_ElEMENT.apply(this, arguments);

            // 在 rn 0.20 版本中，View 的 displayName 是 RCTView
            if (displayName == 'View' || displayName == 'RCTView') {
                let opt = {};
                ViewPressAttrs.forEach((attr) => {
                    if (options[attr]) {
                        opt[attr] = options[attr];
                        delete options[attr];
                    }
                });
                if (Object.keys(opt).length > 0) {
                    // 修复列表的 touchable 丢失 key 属性的 bug
                    if (options.key) opt.key = options.key;
                    ele = ORIGIN_CREATE_ElEMENT.apply(this, [TouchableWithoutFeedback, opt, ele]);
                }
            }
            return ele;
        };
    });
}, function(React) {
    WindowSize = React.Dimensions.get('window');
    vWidth = WindowSize.width / 100;
    vHeight = WindowSize.height / 100;
});
