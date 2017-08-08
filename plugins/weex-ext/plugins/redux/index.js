import connect from './lib/connect'
import defineStore from './lib/defineStore'
import compose from './lib/redux/compose'
import combineReducers from './lib/redux/combineReducers'
import bindActionCreators from './lib/redux/bindActionCreators'
import invariant from './lib/utils/invariant'

const {
    isArray,
    isPlainObject,
    isString,
    noop
} = Ext.utils;

Ext.Redux = {
    defineStore,
    combineReducers,
    bindActionCreators,
    compose,
    usingRedux,
    invariant
};

function usingRedux() {
    return Ext.defaults.redux && Ext.defaults.redux.reducer;
}

function visitState(state, path) {
    const visitor = new Function('state', `return state.${path}`);
    try {
        return visitor(state);
    } catch (e) {
        throw new Error(`解析 state 对象出错，请确保 state.${path} 存在并合法`);
    }
}

function mergeOpts(opts = {}) {
    /**
     * 特殊处理数组方式的简写
     * 如 ['a', 'b', 'c.d.e']
     * 会被解析为
     * (state) => ({
     *   a: state.a,
     *   b: state.b,
     *   e: state.c.d.e
     * })
     */
    if (isArray(opts.mapStateToProps)) {
        let states = opts.mapStateToProps;
        opts.mapStateToProps = (state) => {
            return states.reduce((prev, current) => {
                invariant(
                    isString(current),
                    'mapStateToProps 数组内容应为字符串，但你输入的是 %s。',
                    current
                );
                let matched = current.match(/[^\.]+$/);
                invariant(
                    isArray(matched),
                    `%s 最后一个 '.' 的后面应为一个合法属性名。`,
                    current
                );
                prev[matched[0]] = visitState(state, current);
                return prev;
            }, {});
        }
    }

    // 特殊处理 dispatcher 的简写
    // 这里直接传入一个对象即可
    if (isPlainObject(opts.mapDispatchToProps)) {
        let actions = opts.mapDispatchToProps;
        opts.mapDispatchToProps = (dispatch) => {
            return Object.keys(actions).reduce((prev, current) => {
                prev[current] = bindActionCreators(actions[current], dispatch);
                return prev;
            }, {});
        }
    }

    return opts
}

Ext.addPlugin('redux', noop, noop, (Component, isView, plugins, name) => {
    let options = Component.reduxPlugin;

    if (plugins.indexOf('redux') === -1 || !usingRedux()) {
        invariant(
            !isPlainObject(options),
            `如果想使用 redux 插件，请至少配置 'Ext.defaults.redux' 的 'reducer' 属性。`
        );
        return Component;
    }

    // 用户使用 redux 但是没有设置 plugin 参数，依旧返回原组件
    if (!options) {
        return Component;
    }

    invariant(
        isPlainObject(options),
        `%s 组件的 'reduxPlugin' 应该是对象，但你输入的是 %s。`,
        name, options
    );

    let opts = mergeOpts(options);

    return connect(
        opts.mapStateToProps,
        opts.mapDispatchToProps,
        opts.mergeProps,
        opts.options
    )(Component);
});
