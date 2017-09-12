<template>
    <div @onAndroidBack="onAndroidBack"  class="container">
        <navpage backgroundColor="#3e50b5" title="Image" @naviBarLeftItemClick="naviBarLeftItemClick"/>
        <div class="img-wraper">
            <div class="img-item" v-for="(item,index) in imageArr" @click="clickImage(index)">
                <image class="img"  :src="item"></image>
            </div>
        </div>
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
    const imageBrowser = weex.requireModule('imageBrowser')
    import config from '../../../config/index'

    export default {
      components: {
        navpage: require('../../../include/navpage.vue'),
      },
      data() {
        return {
          imageArr:[],
        }
      },
      computed: {
      },
      created() {
        console.log("image.vue created")
        this.imageArr = [
            'https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg',
            config.image('test.jpg'),
        ]
      },
      methods:{
        naviBarLeftItemClick(e) {
            console.log(e)
        },
        clickImage(index) {
          imageBrowser.browserImages(this.imageArr, index)
        }
      },
    }
</script>

