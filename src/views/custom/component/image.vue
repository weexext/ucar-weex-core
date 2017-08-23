<template>
    <div @onAndroidBack="onAndroidBack"  class="container">
        <navpage backgroundColor="#3e50b5" title="Image" @naviBarLeftItemClick="naviBarLeftItemClick"/>
        <div class="img-wraper">
            <div class="img-item" v-for="(item,index) in imgArr">
                <image class="img"  :src="item"></image>
            </div>
        </div>
        <image style="width: 560px;height: 200px;" src="assets:///image/test.jpg"
               placeholder="assets:///image/1test.jpg"
        ></image>
        <image style="width: 560px;height: 200px;" src="assets:///image/test.jpg"
               placeholder="file:///sdcard../../1test.jpg"
        ></image>
    </div>
</template>

<style scoped>
    .container {
        flex: 1;
    }
    .img-wraper {
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .img-item {
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .img {
        width: 300px;
        height: 250px;
    }

</style>

<script>
    import config from '../../../config/index'

    export default {
      components: {
        navpage: require('../../../include/navpage.vue'),
      },
      data() {
        return {
          imgArr:[],
        }
      },
      computed: {
      },
      created() {
        console.log("image.vue created")
        this.imgArr = [
            'https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg',
            config.image('test.jpg'),
        ]
      },
      methods:{
        naviBarLeftItemClick(e) {
            console.log(e)
        },
      },
    }
</script>

