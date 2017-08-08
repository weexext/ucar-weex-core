<template>
    <div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container">
        <navpage backgroundColor="#3e50b5" title="pageA"
                 @naviBarLeftItemClick="naviBarLeftItemClick"/>

        <div class="button" @click="onClick">
            打开PageB
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
    import router from '../utils/router'

    export default {
        components: {
            navpage: require('../include/navpage.vue'),
        },
        data() {
            return {
                price: '',
                pageName: 'pageA'
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
            ready(param){
                console.log(this.pageName + ':ready')
            },

            actived(e){
                console.log(this.pageName + ':actived')
//                let p = JSON.stringify(e.param)
                console.log('pageB回传参数=' +e.tagCode + e.param);
            },

            deactived(){
                console.log(this.pageName + ':deactived')
            },

            naviBarLeftItemClick(e) {
                router.popTo(1,{})
            },
            onAndroidBack(){
                router.pop(() => {
                })
            },
            onClick(){
                router.push('pageB.js', {
                    api: "push",
                    from: "pageA",
                    to: 'pageB'
                })
            }
        },
    };
</script>
