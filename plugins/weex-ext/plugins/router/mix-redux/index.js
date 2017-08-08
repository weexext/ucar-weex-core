import Provider from './provider'

const { PropTypes } = React;
const { defineStore, usingRedux, invariant } = Ext.Redux;

const uniqueStoreSymbol = `This_view_has_it's_own_store.`;
const renderedElementSymbol = `I_don't_want_be_rendered_again.`;

export default {
    wrapperRouter(Router) {
        let open = Router.open;
        Router.open = (viewName, options = {}) => {
            let [name, openParam] = viewName.split(':');

            invariant(
                !openParam || usingRedux(),
                `在未配置 'Ext.defaults.redux.reducer' 的情况下不能使用 Router.open('view:%s') 的功能。`,
                openParam
            );

            switch (openParam) {
                case 'new':
                    options[uniqueStoreSymbol] = true;
                    break;
            }
            return open.call(Router, name, options);
        }
    },
    setChildContext(context, routerParam) {
        // 给 view 插入一个 context
        context.constructor.childContextTypes = {
            param: PropTypes.object
        };
        context.getChildContext = () => ({
            param: routerParam
        });
    },
    wrapperView(route, Component, getCurrentHashKey) {
        // 缓存渲染过的页面
        let renderedElement = route.opts[renderedElementSymbol];
        if (renderedElement) return renderedElement;

        const param = (route.opts && route.opts.param) || {};
        renderedElement = <Component param={param} />;

        // 如果渲染的页面是当前第一页，且拥有 uniqueStore 标志
        // 渲染 Provider 包裹的 component
        if (usingRedux() &&
            route.opts[uniqueStoreSymbol] &&
            route.hashKey === getCurrentHashKey()) {
            const state = this.__store.getState();
            renderedElement = <Provider store={ defineStore(state) }>{ renderedElement }</Provider>;
        }

        route.opts[renderedElementSymbol] = renderedElement;
        return renderedElement;
    },
    wrapperNavigator(navigatorComponent) {
        if (usingRedux()) {
            this.__store = defineStore();
            return <Provider store={ this.__store }>{ navigatorComponent }</Provider>;
        } else {
            return navigatorComponent;
        }
    }
}