# ucar-weex

weex 的增强框架

## 介绍

* Utils: Utils
* Router: 路由部分 push() ,pop()
* Bridge: postGlobalEvent()  ,sendScheme

## 开始

```js
// 引入 ucar-weex
import uweex from 'weex-ext'

//常用utils
uweex.utils.isString('xx');

//发送全局事件
uweex.bridge.postGlobalEvent('test', {key: '你好'});

//接收全局事件
uweex.bridge.addEventListener('test',(e)=>{
    
})
//注销全局事件
uweex.bridge.removeEventListener('test')

//打开新的页面
let options = {
    url: item.page,
    param: {
        'KEY_INDEX': 'VALUE_INDEX'
    },
    navBar:{
        backgroundColor: '#000000',
    }
};
uweex.router.push(options,() => {
    
})

//关闭当前页面
uweex.router.pop()

//打开指定页面
uweex.router.popTo({index:-1,tagCode:"pageb",param:{test:'testB'}},() => {} );

```
