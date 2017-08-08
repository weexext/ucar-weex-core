/**
 * React.Ext-Router
 * React.Ext 路由插件
 *
 * @author  yayu.wang@qunar.com
 * @version 0.0.0
 * @since   2016/2/24
 */

import mixRedux from './mix-redux'

import NavBar from './navBar'
import Bridge from './bridge.js'
import errorHandler from './util/errorHandler.js';

let Router = {};

/**
 * VC 数组，每个 VC 包含一个导航器和一个导航栏
 * @type {Array}
 * @example
 * vcs = [{
 *     nav,     // 导航器
 *     navBar,  // 导航栏
 * }]
 */
let vcs = [];

/**
 * 存放所有页面的容器
 * @type {Object}
 * @example
 * views = {
 *     pageA: {
 *         Component,   // viewClass
 *         em,          // 事件函数处理对象
 *         routerOpts   // 路由插件配置参数
 *         reactView    // render 后的 view element，已废弃
 *     }
 * }
 */
let views = {};

// 是否使用导航栏，默认不使用
let isUseNavBar = false;
// 是否是 Qunar React Native
let isQReact = !!React.NativeModules.QRCTDeviceInfo;
// 暂存 actived 参数
let activedParam = null;

Ext.addPlugin('router', function(context, pOpts={}, React, isView) {
    if (!isView) {
        return;
    }

    let name;
    let view = {};

    // 寻找 view
    Object.keys(views).some(key => {
        let viewForKey = views[key];
        if (context.constructor === viewForKey.Component ||
            context.constructor === viewForKey.Component.WrappedComponent) {
            name = key;
            view = viewForKey;
            return true;
        }
    });

    if (!view) {
        return;
    }

    // 获取事件函数处理对象
    view.em = this;
    // 获取页面内 Router 插件配置参数
    view.routerOpts = pOpts;

    // 获取 view element
    // this.on('beforeComponentWillMount', (reactView) => {
    //     view.reactView = reactView;
    // });

    // 触发 ready
    this.on('afterComponentWillMount', (reactView) => {
        this.trigger('ready');
    });

    // 触发 destroy
    this.on('afterComponentWillUnmount', (reactView) => {
        this.trigger('destroy');
    });

    // 适配 React.Ext-Redux
    let routeInfo = getRouteInfoByName(name);
    let routerParam = {};
    if (routeInfo) {
        let route = routeInfo.route;
        if (route.opts && route.opts.param) {
            routerParam = route.opts.param;
        }
    }

    // @redux 将 routerParam 插入到 context 中, 令子组件调用
    mixRedux.setChildContext(context, routerParam);
}, function() {
    init();
}, function(Component, isView, plugins, className) {
    // 获取 Component
    if (isView) {
        if (!Ext.defaults.indexView) {
            Ext.defaults.indexView = className;
        }
        views[className] = { Component };
    }
});

/**
 * Router 初始化函数
 */
function init() {
    const { Navigator, AppRegistry, View } = React;

    let indexName;

    let NavComp = React.createClass({
        componentDidMount() {
            if (indexName) {
                let view = getViewByName(indexName);
                let param = getCurrentRoute().opts.param;

                if (isQReact) {
                    // 暂存数据
                    activedParam = param;
                } else {
                    // 是 qunar-react-native，此处无需触发 actived，交由 onShow 来触发
                    view.em.trigger('actived', getCurrentRoute().opts.param);
                }

                // reander NavBar
                if (isUseNavBar) {
                    let navBar = new NavBar(this.refs.navBarElem);
                    navBar.init(view.routerOpts);
                    let currentVC = getCurrentVC();
                    if (currentVC) {
                        currentVC.navBar = navBar;
                    }
                }
            } else {
                errorHandler.noIndexView();
            }
        },
        componentWillUnmount() {
            let vcIndex = vcs.indexOf(this.vc);

            if (vcIndex > -1) {
                vcs.splice(vcIndex, 1);
            }
        },
        renderScene(route, navigator) {
            // navigator 存储
            let isNewVC = false;
            if (this.props.isQRCTDefCreate === true) {
                if (vcs.length > 0) {
                    if (navigator !== getCurrentVC().nav) {
                        // 如果传入的 navigator 不是当前的，则判定为新开了 VC
                        isNewVC = true;
                    }
                } else {
                    isNewVC = true;
                }
            }

            if (isNewVC) {

                let vc = {
                    nav: navigator,
                    navBar: null,
                };
                this.vc = vc;
                vcs.push(vc);
            }

            let view = getViewByName(route.name);

            if (view) {
                // @redux 新增 store 页面生成 Provider
                return mixRedux.wrapperView(route, view.Component, getCurrentHashKey);
            }
        },
        configureScene(route) {
            // todo: 实现页面级别的自定义

            // PushFromRight
            // FloatFromRight
            // FloatFromLeft
            // FloatFromBottom
            // FloatFromBottomAndroid
            // FadeAndroid
            // HorizontalSwipeJump
            // HorizontalSwipeJumpFromRight
            // VerticalUpSwipeJump
            // VerticalDownSwipeJump

            if (route.opts && route.opts.sceneConfigs) {
                let sceneConfigs = route.opts.sceneConfigs,
                    sceneConfigsType = typeof sceneConfigs;

                if (sceneConfigsType === 'string') {
                    return Navigator.SceneConfigs[sceneConfigs];
                } else if (sceneConfigsType === 'object') {
                    return sceneConfigs;
                }
            }

            return {
                ...Navigator.SceneConfigs.PushFromRight,
                animationInterpolators: Navigator.SceneConfigs.HorizontalSwipeJump.animationInterpolators,
            };
        },
        onDidFocus(route) {
            // 判断是否是通过方法切换页面的（与手势回退（右滑）相对）
            // 之所以这么做，是因为右滑是 `jumpBack` 而不是 `popToRoute`
            if (route._isBackByFunction) {
                return;
            }

            delete route._isBackByFunction;

            // 左划返回
            // setTimeout 是因为此时 routes 还没减少
            setTimeout(() => {
                checkAndOpenSwipeBack();
            }, 0);

            if (vcs.length < 1) {
                return;
            }

            let {nav, navBar} = getCurrentVC();
            let routes = nav.getCurrentRoutes();
            let curRouteIndex = routes.lastIndexOf(route);

            // 手势回退检测
            if (curRouteIndex !== routes.length - 1) {
                // 当前页不是路由列表最后一个，说明是通过手势返回的
                let lastView = getCurrentView();

                // 触发前一页面的 deactived
                lastView.em.trigger('deactived');
                // 触发当前页面的 actived
                let currentView = getViewByName(route.name);
                currentView.em.trigger('actived');

                // reander NavBar
                navBar && navBar.transform(currentView.routerOpts, true);

                // 重写路由列表，保证当前页时最后一个
                nav.immediatelyResetRouteStack(routes.slice(0, curRouteIndex + 1));
            }
        },
        render() {
            // 先取 native 指定的首页
            indexName = this.props.qInitView;

            if (indexName) {
                // 判断是否注册了
                if (!views[indexName]) {
                    // 如果没注册，使用前端指定的首页
                    indexName = Ext.defaults.indexView;
                }
            } else {
                // 如果 native 没指定，使用前端指定的首页
                indexName = Ext.defaults.indexView;
            }

            if (indexName) {
                let indexOpts = {
                    param: this.props.param || {},
                };
                let navBarOpts = Ext.defaults.navBar;

                // @redux 使用 store 包裹 Navigator
                let navigatorComponent = mixRedux.wrapperNavigator(
                    <Navigator
                        initialRoute={{name: indexName, opts: indexOpts, hashKey: getHashKey()}}
                        configureScene={this.configureScene}
                        renderScene={this.renderScene}
                        onDidFocus={this.onDidFocus}
                    />
                );

                if (navBarOpts.isShow === true) {
                    // 使用导航栏
                    isUseNavBar = true;
                    return (
                        <View style={{flex: 1}}>
                            <NavBar.comp opts={navBarOpts} ref="navBarElem" />
                            { navigatorComponent }
                        </View>
                    );
                } else {
                    return navigatorComponent;
                }


            } else {
                errorHandler.noIndexView();
            }
        }
    });

    let appName = Ext.defaults.appName;
    if (appName) {
        AppRegistry.registeExtrComponent(appName, () => NavComp);
    } else {
        errorHandler.noAppName();
    }
}

/*************************
 * Router API
 ************************/

/**
 * [API] open
 * @param  {String} name 页面名字
 * @param  {Object} opts 参数
 * @return {Boolean} 执行结果（true为成功 ，false 为失败）
 */
Router.open = (name, opts = {}) => {
    let currentView = getCurrentView(),
        nextView = getViewByName(name),
        res = false;

    if (nextView) {
        let {nav, navBar} = getCurrentVC();

        nav.push({
            name,
            opts,
            hashKey: getHashKey(),
            _isBackByFunction: true,
        });
        setSwipeBackEnabled(false);

        // reander NavBar
        if (opts.title) {
            nextView.routerOpts.title = opts.title;
        }
        navBar && navBar.transform(nextView.routerOpts);

        nextView.em.trigger('actived', opts.param);
        currentView.em.trigger('deactived');

        res = true;
    }

    return res;
};

/**
 * [API] back
 * @param  {String} name 页面名字
 * @param  {Object} opts 参数
 * @return {Boolean} 执行结果（true为成功 ，false 为失败）
 */
Router.back = (opts = {}) => {
    let {nav, navBar} = getCurrentVC();
    let routes = nav.getCurrentRoutes();
    let currentView = getCurrentView();
    let res = false;

    if (routes.length > 1) {
        // 如果当前 routes 有超过一个路由，说明在当前 VC 回退
        let nextRoute = routes[routes.length - 2];
        let nextView = getViewByName(nextRoute.name);

        // 方法回退识别（与手势回退（右滑）区分）
        nextRoute._isBackByFunction = true;

        nav.pop();
        checkAndOpenSwipeBack();

        // reander NavBar
        navBar && navBar.transform(nextView.routerOpts, true);

        nextView.em.trigger('actived', opts.param);
        currentView.em.trigger('deactived');

        res = true;
    } else {
        // 如果当前 routes 只有一个路由，说明要关闭当前 VC 了
        if (vcs.length > 1) {
            // 如果此时有超过1个 VC，那么放心关
            currentView.em.trigger('deactived');
            closeCurrentVC();

            let routes = nav.getCurrentRoutes();
            let nextRoute = routes[routes.length - 1];
            let nextView = getViewByName(nextRoute.name);

            // 方法回退识别（与手势回退（右滑）区分）
            nextRoute._isBackByFunction = true;

            res = true;
        } else {
            // 如果此时只有1个 VC
            currentView.em.trigger('deactived');
            closeCurrentVC();
        }
    }

    return res;
};

/**
 * [API] backTo
 * @param  {String} name 页面名字
 * @param  {Object} opts 参数
 * @param  {Object} _fromGoto 来自内部方法 Router.goto 的标志
 * @return {Boolean} 执行结果（true为成功 ，false 为失败）
 */
Router.backTo = (name, opts = {}, _fromGoto) => {
    let currentView = getCurrentView(),
        nextRouteInfo = getRouteInfoByName(name),
        nextView = getViewByName(name),
        res = false;

    if (nextView) {
        if (nextRouteInfo) {

            let {route, vcIndex} = nextRouteInfo;
            let {nav, navBar} = vcs[vcIndex];

            // 方法回退识别（与手势回退（右滑）区分）
            route._isBackByFunction = true;

            // MAIN: 调用原生 API，路由回退
            nav.popToRoute(route);

            // QReact
            if (vcIndex < vcs.length - 1) {
                // 暂存数据
                activedParam = opts.param;
                // 通知 Native
                Bridge.backToReactVC({
                    // VC 标识
                    index: vcIndex,
                    // 可选，安卓透明层标识（只有安卓才有）
                    adrToken: '',
                });
            } else {
                nextView.em.trigger('actived', opts.param);
                // reander NavBar
                navBar && navBar.transform(nextView.routerOpts, true);
            }

            checkAndOpenSwipeBack(vcIndex);

            currentView.em.trigger('deactived');

            res = true;
        } else {
            if (_fromGoto !== true) {
                errorHandler.noRoute(name);
            }
        }
    }

    return res;
};

/**
 * [API] goto
 * @param  {String} name 页面名字
 * @param  {Object} opts 参数
 * @return {Boolean} 执行结果（true为成功 ，false 为失败）
 */
Router.goto = (name, opts = {}) => {
    let res = Router.backTo(name, opts, true);

    if (!res) {
        res = Router.open(name, opts);
    }

    return res;
};

/**
 * [API] home
 * @param  {Object} opts 参数
 * @return {Boolean} 执行结果（true为成功 ，false 为失败）
 */
Router.home = (opts = {}) => {
    let currentView = getCurrentView();
    let vcsLen = vcs.length;

    if (vcsLen > 1) {
        // 暂存数据
        activedParam = opts.param;
        // 通知 Native
        Bridge.backToReactVC({
            // VC 标识
            index: 0,
            // 可选，安卓透明层标识（只有安卓才有）
            adrToken: '',
        });
    }

    let {nav, navBar} = vcs[0];
    let routes = nav.getCurrentRoutes();

    if (vcsLen === 1 && routes.length === 1) {
        errorHandler.warn('当前就是历史第一页');
        return false;
    }

    let nextRoute = routes[0];
    let nextView = getViewByName(nextRoute.name);

    // 方法回退识别（与手势回退（右滑）区分）
    nextRoute._isBackByFunction = true;

    nav.popToTop();
    setSwipeBackEnabled(true, 0);

    // reander NavBar
    if (vcsLen === 1) {
        navBar && navBar.transform(nextView.routerOpts, true);
    }

    nextView.em.trigger('actived', opts.param);
    currentView.em.trigger('deactived');

    return true;
};

/**
 * [API] close
 * @param  {String} name 页面名字
 * @return {Boolean} 执行结果（true为成功 ，false 为失败）
 */
Router.close = (name) => {

    if (!name) {
        return Router.back();
    }

    let theRouteInfo = getRouteInfoByName(name),
        theView = getViewByName(name),
        res = false;

    if (theView) {
        if (theRouteInfo) {
            let {routeIndex, vcIndex} = theRouteInfo;
            let nav = vcs[vcIndex].nav;
            let routes = nav.getCurrentRoutes();

            if (vcIndex === vcs.length - 1 && routeIndex === routes.length - 1) {
                // 如果关闭的是当前页面，则做 back
                res = Router.back();
            } else {
                routes.splice(routeIndex, 1);
                nav.immediatelyResetRouteStack(routes);
                checkAndOpenSwipeBack(vcIndex);

                res = true;
            }
        } else {
            errorHandler.noRoute(name);
        }
    }

    return res;
}

/*************************
 * Native Bridge
 ************************/
React.DeviceEventEmitter.addListener('onShow', (data) => {
    // 如果是返回操作，此时 Navigator 的 componentWillUnmount 还未执行，所以 vcs 还未变少
    let index = data.index;
    if (index < 0 || !vcs[index]) return;

    let routes = vcs[index].nav.getCurrentRoutes();
    let routesLen = routes.length;

    if (routesLen === 0) {
        closeCurrentVC();
        return;
    }

    let route = routes[routesLen - 1];
    let view = getViewByName(route.name);

    view.em.trigger('actived', activedParam || {});
    activedParam = null;
});
React.DeviceEventEmitter.addListener('onHide', (data) => {
    let index = data.index;

    if (index >= vcs.length) {
        return;
    }

    let routes = vcs[index].nav.getCurrentRoutes();
    let routesLen = routes.length;

    if (routesLen > 0) {
        let view = getViewByName(routes[routesLen - 1].name);
        view.em.trigger('deactived');
    }
});
/**
 * 兼容以前错误 scheme 拼写
 * TODO: 下个版本删除
 */
React.DeviceEventEmitter.addListener('receiveSchema', (res) => {
    console.warn('receiveSchema 已废弃，请升级 QRN');
    receiveSchemeCB(res);
});
React.DeviceEventEmitter.addListener('receiveScheme', receiveSchemeCB);

function receiveSchemeCB(res) {
   let data = res.data;
   let ret = false;

   mergeDataFromUrl(res.url);

   // 如果没有设置 hybridId，帮他设置下
   if (!Ext.defaults.hybridId) {
       Ext.defaults.hybridId = data.hybridId;
   }

   // 是否需要 ext 处理
   if (data.ext === false) {
       return;
   }

   if (data.forceOpen === true) {
       openVC();
   } else {

       ret = myBackTo(data.qInitView);

       if (!ret) {
           // 返回失败
           openVC();
       }

   }

   if (ret) {
       Bridge.sendNativeEvents({
           id: res.callbackId,
           data: {
               ret,
               msg: '成功'
           },
       });
   } else {
       Bridge.sendNativeEvents({
           id: res.callbackId,
           data: {
               ret,
               msg: '失败'
           },
       });
   }

   // only in android
   Bridge.closeActivityAndroid(res.adrToken);

   function openVC() {
       var openNewVCData = data.initProps || {};

       if (data.qInitView) {
           openNewVCData.qInitView = data.qInitView
       }

       Bridge.openNewVC({
           data: openNewVCData,
       });

       ret = true;
   }

   function myBackTo(name) {

       if (!name) {
           return false;
       }

       let currentView = getCurrentView(),
           nextRouteInfo = getRouteInfoByName(name),
           nextView = getViewByName(name),
           res = false;

       if (nextView) {
           if (nextRouteInfo) {
               // 暂存数据
               activedParam = (data.initProps && data.initProps.param) || {};

               if (currentView === nextView) {
                   Bridge.backToReactVC({
                       // VC 标识
                       index: vcs.length - 1,
                       // 可选，安卓透明层标识（只有安卓才有）
                       adrToken: '',
                   });
               } else {
                   let {route, routeIndex, vcIndex} = nextRouteInfo;
                   let {nav, navBar} = vcs[vcIndex];

                   // 方法回退识别（与手势回退（右滑）区分）
                   route._isBackByFunction = true;

                   // MAIN: 调用原生 API，路由回退
                   nav.popToRoute(route);
                   // VIP: 由于 popToRoute 导致 routes 变化是异步的，Native onShow 触发时最后一个 route 没变，所以这里手动清理下。
                   nav._cleanScenesPastIndex(routeIndex);

                   // 通知 Native
                   Bridge.backToReactVC({
                       // VC 标识
                       index: vcIndex,
                       // 可选，安卓透明层标识（只有安卓才有）
                       adrToken: '',
                   });

                   // reander NavBar
                   navBar && navBar.transform(nextView.routerOpts, true);
               }

               res = true;
           }
       }

       return res;
   }

   /**
    * 从 url 上获取数据并 合并到 res.data 上
    * @param  {String} url - Scheme 的 url
    */
   function mergeDataFromUrl(url) {
       let search = url.split('?')[1];

       if (search) {
           let pairs = search.split('&');

           pairs.forEach((pair) => {
               let pairArr = pair.split('=');
               let key = pairArr[0];
               let value = decodeURIComponent(pairArr[1]);

               try {
                   value = JSON.parse(value);
               } catch(e) {

               }

               // url 的优先级高于 data
               data[key] = value;
           });
       }
   }
}

/*************************
 * 工具类方法
 ************************/
/**
 * 获取当前 VC
 * @return {Object} VC 对象
 */
function getCurrentVC() {
    return vcs[vcs.length - 1];
}
/**
 * 获取当前 view
 * @return {Route} 当前 route
 */
function getCurrentView() {
    let route = getCurrentRoute();
    return getViewByName(route.name);
}
/**
 * 获取当前 route
 * @return {Route} 当前 route
 */
function getCurrentRoute() {
    let routes = getCurrentVC().nav.getCurrentRoutes();
    return routes[routes.length - 1];
}
/**
 * 根据页面名字获取页面
 * @param  {String} name 页面名字
 * @return {View}        页面
 */
function getViewByName(name) {
    let view = views[name];

    if (!view) {
        errorHandler.noView(name);
    }

    return view;
}
/**
 * 根据页面名字获取页面所在路由的信息
 * @param  {String} name 页面名字
 * @return {Object}      页面所在路由（route 为路由对象，routeIndex 为所在 routes 的 index，vcIndex 为 routes 所在 vcs 的 index）,如果没有则返回 null
 */
function getRouteInfoByName(name) {
    let vcIndex = vcs.length;

    while (vcIndex) {
        vcIndex--;

        let routes = vcs[vcIndex].nav.getCurrentRoutes();
        let routeIndex = routes.length;

        while (routeIndex) {
            routeIndex--;

            let route = routes[routeIndex];
            if (route.name === name) {
                return {
                    route,
                    routeIndex,
                    vcIndex,
                };
            }
        }
    }

    return null;
}
/**
 * 关闭当前 VC
 * @return {[type]} [description]
 */
function closeCurrentVC() {
    Bridge.closeCurrentVC();
}
/**
 * 设置返回手势开关
 * @param {Boolean} isEnabled 开关
 */
function setSwipeBackEnabled(isEnabled, vcIndex) {
    if (vcIndex === undefined) {
        vcIndex = vcs.length - 1;
    }
    Bridge.setSwipeBackEnabled(isEnabled, vcIndex);
}
function checkAndOpenSwipeBack(vcIndex) {
    if (vcIndex === undefined) {
        vcIndex = vcs.length - 1;
    }
    if (vcIndex < 0 || !vcs[vcIndex]) return;

    let routes = vcs[vcIndex].nav.getCurrentRoutes();
    if (routes.length === 1) {
        setSwipeBackEnabled(true);
    }
}

// @redux 强行包裹 Router.open 方法
mixRedux.wrapperRouter(Router);

// 暴露一下
Router.Bridge = Bridge;
Router._views = views;
Router._vcs = vcs;
Router.navBar = {
    setTitle(title) {
        if (!isUseNavBar) {
            return;
        }
        if (typeof title !== 'string') {
            errorHandler.warn('标题必须为字符串类型');
            return false;
        }

        let {navBar} = getCurrentVC();

        getCurrentView().routerOpts.title = title;
        navBar.navBarElem.setState({
            actualTitle: title
        });
    },
};

Ext.Router = Router;

Ext.open = Router.open;
Ext.back = Router.back;
Ext.backTo = Router.backTo;
Ext.goto = Router.goto;
Ext.home = Router.home;
Ext.close = Router.close;

// @redux 给每个页面插入一个 hashKey
// 主要是 android 好像不支持 symbol
const getCurrentHashKey = () => getCurrentRoute().hashKey;
const getHashKey = () => Math.random().toString(32) + +new Date;

export default Router;
