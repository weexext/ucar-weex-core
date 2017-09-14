<template>

    <div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container">
        <navpage backgroundColor="#3e50b5" title="weex页面" :leftItemSiblingSrc="imgClose"
                 @naviBarLeftSiblingItemClick="naviBarLeftSiblingItemClick"
                 @naviBarLeftItemClick="naviBarLeftItemClick">
        </navpage>
        <div class="button" @click="onClick">
            跳转native带参数
        </div>
    </div>

</template>

<style scoped>

    .button {
        background-color: #3e50b5;
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
    const nativeRouter = weex.requireModule('nativeRouter')

    import uweex from 'ucar-weex'
    //    import uweex from '../../../packages/weex-vue-expand';
    export default {
        components: {
            navpage: require('../../include/navpage.vue'),
        },
        data() {
            return {
                price: '',
                pageName: 'pageB'
            }
        },
        beforeCreate: function () {
            console.log(this.pageName + ':beforeCreate');
        },
        created: function () {
            console.log(this.pageName + ':created');
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
            ready(e){
                console.log(this.pageName + ':ready')
                let p = JSON.stringify(e.param)
                console.log('pageA页面传参数=' + p);
            },

            actived(e){
                console.log(this.pageName + ':actived')
            },

            deactived(){
                console.log(this.pageName + ':deactived')
                modal.toast({
                    message: 'deactived',
                    duration: 0.3
                });
            },
            naviBarLeftItemClick(){
                uweex.router.pop();
            },
            onAndroidBack(){
                uweex.router.pop();
            },
            onClick(){
//                http://10.99.44.54:8088/debugger.html?sessionId=j7fm0joc22fz2pf4t7d#
//                        uweex.bridge.sendScheme('ucar://portal/nativeA', {param: 'weex页面传参数'}, ()=> {
//                        });
                nativeRouter.push('nativeA',{param: 'weex页面传参数'});
            }
        },

    };
</script>
