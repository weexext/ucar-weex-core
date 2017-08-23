<template>

    <div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container">
        <navpage backgroundColor="#3e50b5" title="pageB" :leftItemSiblingSrc="imgClose"
                 @naviBarLeftSiblingItemClick="naviBarLeftSiblingItemClick"
                 @naviBarLeftItemClick="naviBarLeftItemClick">
        </navpage>
        <div class="button" @click="onClick">
            返回PageA
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

    import uweex from 'ucar-weex'
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
            onAndroidBack(){
                uweex.router.pop();
            },
            onClick(){
                uweex.router.popTo({index:-1,tagCode:"pageb",param:{test:'testB'}},() => {} );
            }
        },

    };
</script>
