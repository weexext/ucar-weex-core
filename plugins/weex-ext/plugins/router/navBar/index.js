/**
 * Weact-Router navBar
 * Weact 路由插件的 navBar
 */

import styles from './styles.js'
import errorHandler from '../util/errorHandler'

const {
    View,
    Text,
    TouchableOpacity,
    Animated,
    Dimensions,
} = React;

// 创建对象，防止有人直接 Ext.defaults.navBar.isShow = true 酱紫
Ext.defaults.navBar = {};

// 默认值
const DEFAULTS = {
    // [全局/页面]是否显示 navBar，默认显示
    isShow: true,
    // [页面]标题，默认空字符串
    title: '',
    // [全局/页面]颜色，默认白色
    color: '#fff',
    // [全局/页面]背景色，默认 Qunar 蓝
    backgroundColor: '#1ba9ba',
    // [全局/页面]左侧按钮文字，默认 '返回'
    leftButtonText: '返回',
    // [页面]右侧按钮默认文字
    rightButtonText: '',
    // [全局/页面]左侧按钮默认点击事件
    leftButtonPressEvent() {
        React.Ext.back();
    },
    // [全局]点击默认不透明度
    activeOpacity: 0.6,
    // [全局]默认动画时间
    animatedDuration: 400,
};
// 存储
let store = {
    bgColor: DEFAULTS.backgroundColor,
    actualBgColor: DEFAULTS.backgroundColor,
    // 左侧按钮点击事件
    leftButtonPressEvent() {},
    // 右侧按钮点击事件
    rightButtonPressEvent() {},
};


// 导航栏到两边的距离
let titleGap = 50;

class NavBarComp extends React.Component {
    styles = styles;

    constructor(props, context) {
        super(props, context);

        this.state = {
            isShow: DEFAULTS.isShow,

            // 控制背景色渐变
            bgColorValue: new Animated.Value(1),

            title: DEFAULTS.title,
            actualTitle: DEFAULTS.title,

            color: DEFAULTS.color,
            actualColor: DEFAULTS.color,

            leftButtonText: DEFAULTS.leftButtonText,
            actualLeftButtonText: DEFAULTS.leftButtonText,

            rightButtonText: DEFAULTS.rightButtonText,
            actualRightButtonText: '',

            headerOpacity: new Animated.Value(0),
            actualHeaderOpacity: new Animated.Value(1),

            titleTranslateX: 0,
            actualTitleTranslateX: new Animated.Value(0),
        };
    };

    leftButtonPressEvent(...args) {
        store.leftButtonPressEvent.apply(this, args);
    };
    rightButtonPressEvent(...args) {
        store.rightButtonPressEvent.apply(this, args);
    };

    render() {
        let opts = this.props.opts;
        // 用户定义的样式
        let customStyles = {
            navBar: {},
            header: {},
            headerItemCenter: {
                left: titleGap,
                right: titleGap,
            },
        };

        // 处理页面级显隐
        if (this.state.isShow) {
            delete customStyles.navBar.height;
            delete customStyles.navBar.paddingTop;
        } else {
            customStyles.navBar = {
                height: 0,
                paddingTop: 0,
            };
        }

        // 高度
        if (typeof opts.height === 'number') {
            customStyles.header.height = opts.height;
        }
        // title 到两边距离
        if (typeof opts.titleGap === 'number') {
            titleGap = opts.titleGap;
            customStyles.headerItemCenter.left = titleGap;
            customStyles.headerItemCenter.right = titleGap;
        }
        // 按钮点击不透明度
        let activeOpacity = opts.activeOpacity || DEFAULTS.activeOpacity;

        return (
            <Animated.View
                style={[
                    styles.navBar,
                    customStyles.navBar,
                    {
                        backgroundColor: this.state.bgColorValue.interpolate({
                            inputRange: [0, 1],
                            outputRange: [store.bgColor, store.actualBgColor],
                        }),
                    }
                ]}>
                <Animated.View style={[
                    styles.header,
                    customStyles.header,
                    {
                        opacity: this.state.headerOpacity,
                    },
                ]}>
                    <View style={[
                        styles.headerItemCenter,
                        customStyles.headerItemCenter,
                    ]}>
                        <Animated.Text style={[
                            styles.headerTextCenter,
                            {
                                transform: [{
                                    translateX: this.state.titleTranslateX
                                }],
                                color: this.state.color,
                            }
                        ]} numberOfLines={1}>
                            {this.state.title}
                        </Animated.Text>
                    </View>
                    <TouchableOpacity style={styles.headerItemLeft} activeOpacity={activeOpacity}>
                        <Text style={[
                            {
                                color: this.state.color,
                            },
                        ]}>
                            {this.state.leftButtonText}
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={styles.headerItemRight}
                        activeOpacity={activeOpacity}
                    >
                        <Animated.Text style={[
                            {
                                color: this.state.color,
                            },
                        ]}>
                            {this.state.rightButtonText}
                        </Animated.Text>
                    </TouchableOpacity>
                </Animated.View>
                <Animated.View style={[
                    styles.header,
                    styles.actualHeader,
                    customStyles.header,
                    {
                        opacity: this.state.actualHeaderOpacity,
                    },
                ]}>
                    <View style={[
                        styles.headerItemCenter,
                        customStyles.headerItemCenter,
                    ]}>
                        <Animated.Text style={[
                            styles.headerTextCenter,
                            {
                                transform: [{
                                    translateX: this.state.actualTitleTranslateX
                                }],
                                color: this.state.actualColor,
                            },
                        ]} numberOfLines={1}>
                            {this.state.actualTitle}
                        </Animated.Text>
                    </View>
                    <TouchableOpacity
                        style={styles.headerItemLeft}
                        activeOpacity={activeOpacity}
                        onPress={this.leftButtonPressEvent.bind(this)}
                    >
                        <Text style={[
                            {
                                color: this.state.actualColor,
                            }
                        ]}>
                            {this.state.actualLeftButtonText}
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={styles.headerItemRight}
                        activeOpacity={activeOpacity}
                        onPress={this.rightButtonPressEvent.bind(this)}
                    >
                        <Animated.Text style={[
                            {
                                color: this.state.actualColor,
                            }
                        ]}>
                            {this.state.actualRightButtonText}
                        </Animated.Text>
                    </TouchableOpacity>
                </Animated.View>
            </Animated.View>
        );
    };
};




/**
 * 处理页面的配置项，返回待设置的 state 对象
 * @param   {Object}  opts      view.routerOpts
 * @param   {Boolean} isActual  是否为 actual 熟悉设置
 * @return  {Object}            待设置的 state 对象
 */
function getState(opts, isActual) {
    let state = {};

    // 处理页面级属性
    ['title', 'rightButtonText'].forEach(item => {
        if (opts[item] === undefined) {
            setAttr(item, DEFAULTS[item]);
        } else {
            setAttr(item, opts[item]);
        }
    });
    // 处理全局级属性
    ['color', 'leftButtonText'].forEach(item => {
        setAttr(item, getRealValue(opts, item));
    });

    // 顺便做一些事情，嘻嘻
    if (isActual) {
        // 处理左按钮点击事件
        if (typeof opts.leftButtonPressEvent === 'function') {
            store.leftButtonPressEvent = opts.leftButtonPressEvent;
        } else {
            store.leftButtonPressEvent = DEFAULTS.leftButtonPressEvent;
        }
        // 处理右按钮点击事件
        if (typeof opts.rightButtonPressEvent === 'function') {
            store.rightButtonPressEvent = opts.rightButtonPressEvent;
        } else {
            store.rightButtonPressEvent = () => {};
        }
    }

    return state;

    function setAttr(attr, value) {
        if (isActual) {
            attr = 'actual' + attr[0].toUpperCase() + attr.slice(1);
        }
        state[attr] = value;
    }
}
/**
 * 处理页面级显隐
 * @param  {Object} opts  view.routerOpts
 * @param  {Object} state 待设置的 state 对象
 */
function handlerShow(opts, state) {
    ['isShow'].forEach(item => {
        if (opts[item] !== undefined) {
            state[item] = opts[item];
        } else {
            state[item] = DEFAULTS[item];
        }
    });
}

/**
 * 按 routerOpts > Weact.defaults.navBar > DEFAULTS 的优先级取值
 * @param  {Object} opts routerOpts
 * @param  {String} name 属性名
 * @return {Any}         值
 */
function getRealValue(opts, name) {
    let value = opts[name];
    if (value === undefined) {
        value = React.Ext.defaults.navBar && React.Ext.defaults.navBar[name];
        if (value === undefined) {
            value = DEFAULTS[name];
        }
    }
    return value;
}

class NavBar {
    constructor(elem) {
        this.navBarElem = elem;
    }

    init(opts) {
        let state = getState(opts, true);

        // 处理页面级显隐
        handlerShow(opts, state);

        // bgColor 处理
        store.bgColor = store.actualBgColor = getRealValue(opts, 'backgroundColor');

        this.navBarElem.setState(state);
    }
    /**
     * navBar 动画函数
     * @param  {Object}  opts   view.routerOpts
     * @param  {Boolean} isBack 是否是回退动作
     * @return {Boolean}        是否成功
     */
    transform(opts, isBack) {
        if (!this.navBarElem) {
            errorHandler.warn('导航栏还未准备好');
            return false;
        }

        let direction = isBack ? 1 : -1;
        let distance = Dimensions.get('window').width/2 - titleGap;
        let state = getState(opts);

        // 处理页面级显隐
        handlerShow(opts, state);

        // 处理下一个页面标题初始位置
        state.titleTranslateX = new Animated.Value(- distance * direction);


        // bgColor 处理
        state.bgColorValue = new Animated.Value(0);
        store.actualBgColor = getRealValue(opts, 'backgroundColor');

        // 设置背景图层，准备动画
        this.navBarElem.setState(state);

        let navBarElemState = this.navBarElem.state;
        Animated.parallel([
            Animated.timing(
                navBarElemState.headerOpacity,
                {
                    toValue: 1,
                    duration: DEFAULTS.animatedDuration,
                }
            ),
            Animated.timing(
                navBarElemState.actualHeaderOpacity,
                {
                    toValue: 0,
                    duration: DEFAULTS.animatedDuration,
                }
            ),
            Animated.timing(
                navBarElemState.titleTranslateX,
                {
                    toValue: 0,
                    duration: DEFAULTS.animatedDuration,
                }
            ),
            Animated.timing(
                navBarElemState.actualTitleTranslateX,
                {
                    toValue: distance  * direction,
                    duration: DEFAULTS.animatedDuration,
                }
            ),
            Animated.timing(
                navBarElemState.bgColorValue,
                {
                    toValue: 1,
                    duration: DEFAULTS.animatedDuration,
                }
            ),
        ]).start(() => {
            let state = getState(opts, true);

            // bgColor 处理
            store.bgColor = store.actualBgColor;

            this.navBarElem.setState(state);
            // 不和上面的 setState 写在一起的原因是为了防止下次动画闪动
            this.navBarElem.setState({
                headerOpacity: new Animated.Value(0),
                actualHeaderOpacity: new Animated.Value(1),
                actualTitleTranslateX: new Animated.Value(0),
            });
        });

        return true;
    }
}

NavBar.comp = NavBarComp;


export default NavBar;
