<template>
    <div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container">
        <navpage leftItemTitle="返回" backgroundColor="#3e50b5" title="pageA"
                 @naviBarLeftItemClick="naviBarLeftItemClick"/>

        <div class="button" @click="onClick">
            打开PageA
        </div>
    </div>
</template>

<style scoped>

    .button {
        width: 200px;
        height: 80px;
        background-color: #3e50b5;
        justify-content: center;
        align-items: center;
    }

    .container {
        background-color: #f3f3f3;
        flex-direction: column;
        flex: 1;
        justify-content: center;
        align-items: center;
    }

</style>

<script>
    const modal = weex.requireModule('modal')
    import config from '../../config/index'
    import uString from '../../utils/UString'
    import uweex from 'ucar-weex'

    export default {
        components: {
            navpage: require('../../include/navpage.vue'),
        },
        data() {
            return {
                price: '11',
                pageName: 'pageA'
            }
        },
        beforeCreate: function () {
            console.log(this.pageName + ':beforeCreate');
        },
        created: function () {
            console.log(this.pageName + ':created');
            let param = uString.decodeURIComponent(config.params('param'))
            console.log(param)
            uweex.bridge.addEventListener('test', (e)=> {
                console.log('registerBroadcast=' + e);
            })

        },
        beforeMount: function () {
            console.log(this.pageName + ':beforeMount');
        },
        mounted: function () {

            console.log(this.pageName + ':mounted');
        },
        beforeUpdate: function () {
            console.log(this.pageName + ':beforeUpdate');
        },
        updated: function () {
            console.log(this.pageName + ':updated');
        },
        beforeDestroy: function () {
            console.log(this.pageName + ':beforeDestroy');
        },
        destroyed: function () {
            console.log(this.pageName + ':destroyed');
        },
        methods: {
            //
            ready(param){
                console.log(this.pageName + ':ready')

            },

            actived(e){
                console.log(this.pageName + ':actived')
                //                let p = JSON.stringify(e.param)
                console.log('pageB回传参数=' + e.tagCode + e.param);
            },

            deactived(){
                console.log(this.pageName + ':deactived')
            },

            naviBarLeftItemClick(e) {
                // 测试数据返回接收
                let options = {
                    index: -1,
                    tagCode: 'PAGE_INDEX',
                    param: {
                        'KEY': 'VALUE',
                        'KEY1': 'VALUE1'
                    }
                }
                uweex.router.popTo(options, ()=> {
                })
            },
            onAndroidBack(){
                uweex.router.pop()
            },
            onClick(){
                uweex.router.push({
                    url: 'pageB.js',
                    param: {
                        api: "push",
                    }
                }, () => {
                })
            }
        },
    };
</script>
