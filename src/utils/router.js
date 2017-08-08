const Ext = weex.requireModule('Ext');
import config from '../config/index';

/**
 * @param page 例如:page/index.js
 * Ext.push('htts:..pageA.js', {
            // 传递参数
            param:{
                api:"push",
                from:'pageA'
            }
            //导航栏配置
            navBar: {
                hasBack: true,
                height: 100,
                navBarBackground: '#ffffff'
            },
            // 配置动画
            sceneConfigs: 'VerticalUpSwipeJump'
        },()=>{});
 */
function push(page, params = {}) {
    let url =  config.js(page)
    let newParams = {
        param: params,
        navBar: {
            hasBack: true,
            height: 100,
            navBarBackground: '#3e50b5'
        },
        sceneConfigs: 'VerticalUpSwipeJump'
    }
    Ext.push(url, newParams, ()=> {

    });
}

/**
 * 退出当前页面
 */
function pop() {
    Ext.pop(1, {}
        , ()=> {

        });
}
/**
 * @param index
 * @param params
 Ext.pop(1,{
    // 传递参数
        backTag,
         param:{

            back_code:'back_tag'
            api:"push",
            from:'pageA'
            }
          },
 ()=>{}
 );
 */
function popTo(index, backTag ,params) {
    Ext.pop(index, backTag ,params, ()=> {
    });
}
export default {
    push:push,
    pop:pop,
    popTo:popTo
}
