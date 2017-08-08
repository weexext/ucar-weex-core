<template>
    <div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container">
        <navpage backgroundColor="#3e50b5" title="Image"
                 @naviBarLeftItemClick="naviBarLeftItemClick"/>

        <image style="width: 560px;height: 560px;"
               src="https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg"></image>
        <image style="width: 560px;height: 560px;" src="file:///android_asset/weex/image/test.jpg"></image>
    </div>
</template>
<script>
    module.exports = {
        components: {
            navpage: require('../../include/navpage.vue'),
        },

    }
</script>

