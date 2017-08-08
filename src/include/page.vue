<template >
    <div class="container"  @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived">
    </div>
</template>
<style scoped>
    .container {
        flex: 1;
    }
</style>
<script>
    module.exports = {
        props: {
            //左侧按钮颜色
            canBack:true,
        },
        data: function () {
            return {
                page: 'page'
            }
        },
        methods: {
            onAndroidBack: function () {
                this.$emit('onAndroidBack');

            },
            ready: function (e) {
                this.$emit('ready');
            },
            actived: function (e) {
                this.$emit('actived');
            },
            deactived: function () {
                this.$emit('deactived');
            },
        },
        events: {
//            ready(param){
//                console.log(this.page+':ready')
//                let p = JSON.stringify(param)
//                console.log('pageA页面传参数=' + p);
//            },
//
//            actived(param){
//                console.log(this.page+':actived')
//            },
//
//            deactived(){
//                console.log(this.page+':deactived')
//            },
//            onAndroidBack(){
//                if (this.canBack){
//                    Ext.pop(() => {})
//                }
//            },

        }
    }
</script>

