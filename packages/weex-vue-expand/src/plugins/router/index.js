import config from '../../lib'
// import defaultNavBar from './navBar';
// import utils from '../../lib/utils'
const _uNavigator = weex.requireModule('UNavigator')

let Router = {};
/**
 * 打开native页面
 * */
Router.pushNative = (options, callback) => {
    //参数添加自定义默认数值
    let param = !options.param ? {} : options.param
    let animated = !options.animated ? 'true' : options.animated
    let _options = {
        url: options.url,
        param: param,
        navBar: options.navBar,
        animated: animated
    }
    _uNavigator.pushNative(_options, callback)
}
/**
 * 进入下级页面
 * */
Router.push = (options, callback) => {
    //参数添加自定义默认数值
    let url = config.js(options.url)
    let param = !options.param ? {} : options.param
    // let navBar = !options.navBar ? defaultNavBar : utils.extend({},defaultNavBar ,options.navBar);

    let animated = !options.animated ? 'true' : options.animated
    let _options = {
        url: url,
        param: param,
        navBar: options.navBar,
        animated: animated
    }
    _uNavigator.push(_options, callback)
}

/**
 * 回到上级页面
 */
Router.popTo = (options, callback) => {
    //参数添加自定义默认值
    let index = !options.index ? -1 : options.index
    let tagCode = !options.tagCode ? '' : options.tagCode
    let param = !options.param ? {} : options.param
    let animated = !options.animated ? 'true' : options.animated
    let _options = {
        index: index,
        tagCode: tagCode,
        param: param,
        animated: animated
    }
    _uNavigator.pop(_options, callback)
}
/**
 * 回到上级页面
 */
Router.pop = ()=> {

    //参数添加自定义默认值
    let _options = {
        index: -1,
        animated: 'true'
    }
    _uNavigator.pop(_options, ()=> {
    })
}

/**
 *  回到当前堆栈根节点
 * */
Router.home = (options, callback)=> {
    let _options = !options ? {} : options
    _uNavigator.home(_options, callback)
}


export default Router;
