### navigator相关API

#### 把一个 Weex 页面 URL 弹出导航堆栈中
	
	push(options, callback)
		options {Object}：选项参数
       url {string}：要压入的 Weex 页面的 URL
       param {Object}:传入参数到下一个页面
       navBar {Object}:导航栏一些默认设置
          height {number}：导航栏高度
          backgroundColor {'#ffffff'}：导航栏背景色
       animated {string}："true" 示意为页面压入时需要动画效果，"false" 则不需要，默认值为 "true"
    callback {Function}：执行完该操作后的回调函数
    
    ---------------
    下级页面参数接收方式：
      解析url中的指定参数[param]
      
  
#### 把一个 Weex 页面 URL 弹出导航堆栈中
  
    pop(options, callback)
      options {object}：选项参数对象
        index {string}: 返回到指定层级页面,index为pop的层级数
        tagCode {string}: 标记code,用于返回上级页面接收参数,若无参数，则可以不用传
        param {Object}: 传回参数到返回页面
        animated {string}："true" 示意为弹出页面时需要动画效果，"false" 则不需要，默认值为 "true"
      callback {function}：执行完该操作后的回调函数
    
    ----------------
    上级页面参数接收方式:
      添加监听
      globalEvent.addEventListener("geolocation", function (event) {
        console.log("event"+event)
      });

#### 把一个WEEX页面弹出到根节点

		home(options,callback)
			options {object}：选项参数对象
			callback {function}：执行完该操作后的回调函数
			
			

			

		
