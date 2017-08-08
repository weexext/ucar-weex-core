#Ext
#Router - Ext Plugin
介绍
Router 作为 Ext 的内置插件，为其提供更加强大好用的路由功能。

Router 具有如下特点：

封装原生的 Navigator 组件，提供更加友好的接口。


强大、自由的导航栏配置。

传参、动画等拓展功能。

简洁直观的生命周期。

开始
引用与配置：
```
<script>

const Ext = weex.requireModule('Ext');
export default {
    // Actions
    // Router 方法调用
    push() {
        Ext.push('htts:..pageA.js', {
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
    };
    pop() {
        Ext.pop(()=>{});
    };
    popto() {
        Ext.popto(
         1,
         {
            // 传递参数
            param:{
                back_code:'back_tag'
                api:"push",
                from:'pageA'
            }
        },
        ()=>{}
        );
    };
    home() {
        Ext.home({
            param: {
                api: 'home',
                from: 'base',
                to: 'base'
            }
        });
    };
    quit() {
        Ext.quit();
    };


    // ext 生命周期回调注册
    
    created() {
        //push页面传值
        var params  = config.getParams('params')
        //popto页面回传数据
        globalEvent.addEventListener("back_code_test", function(e) {
             console.log(e)
        })
    },
    
 });
 
}
</script>
```
##生命周期
`<div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container"></div>`

###生命周期-
    beforeCreate: function (){
        console.log(this.pageName+':beforeCreate');
    },
    created: function () {
        console.log(this.pageName+':created');
    },
    beforeMount: function () {
        console.log(this.pageName+':beforeMount');
    },
    mounted: function () {
        console.log(this.pageName+':mounted');
    },
    beforeUpdate: function () {
        console.log(this.pageName+':beforeUpdate');
    },
    updated: function () {
        console.log(this.pageName+':updated');
    },
    beforeDestroy: function () {
        console.log(this.pageName+':beforeDestroy');
    },
    destroyed: function () {
        console.log(this.pageName+':destroyed');
    },
    methods: {
    //页面挂载 带来参数e.param @Ext.push('page.js',{param:{}},()>{})
        ready(e){
            console.log(this.pageName+':ready')
            console.log('pageA页面传参数=' + e.param);
        },
    //页面激活带来参数e.param @Ext.popto(-1,{param:{back_code:"back_tag",key:"value"}},()>{})
        actived(e){
            console.log(this.pageName+':actived')
            console.log('pageB回传参数=' + e.param);
        },
    //页面失活 
        deactived(){
            console.log(this.pageName+':deactived')
        },
    //监听Android返回键
        onAndroidBack(){
            Ext.pop(() => {
            })
        },
    }

 