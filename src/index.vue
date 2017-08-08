<template>
    <div @onAndroidBack="onAndroidBack" @ready="ready" @actived="actived" @deactived="deactived" class="container">
        <navpage title="UCAR-WEEX" leftItemTitle="" @naviBarLeftItemClick="naviBarLeftItemClick"/>
        <div class="body">
            <list>
                <cell v-for="item in items" @click="onItemClick(item)">
                    <div class="row">
                        <text class="row-name">{{item.name}}</text>
                    </div>
                </cell>
            </list>
        </div>
    </div>
</template>
<style scoped>
    .body {
        background-color: #f3f3f3;
        flex: 1;
    }
    .row{
        flex-direction: row;
        width: 750px;
        height: 100px;
        border-bottom-width: 2px;
        border-bottom-color: #e3e3e3;
        border-bottom-style: solid;
        padding-top: 25px;
        padding-bottom: 25px;
        padding-left: 30px;
        padding-right: 30px;
    }
    .row-name{
        margin-left: 20px;
        align-self: center;
        flex: 1;
        font-size: 40px;
        color: black;
    }
</style>
<script>
    import router from "./utils/router"
    export default   {
        components:{
          navpage :require("./include/navpage.vue")
        },
        methods:{
            onItemClick(item){
                router.push(item.page,{})
            }
        },
        data: {
            items: [
                // common
                {name: 'Router', page: 'views/pageA.js'},

                // component
                {name: 'Text', page: 'views/component/text.js'},
                {name: 'Image', page: 'views/component/image.js'},

                // module
                {name: 'module/instance-api', page: 'Instance API'},

                // showcase
                {name: 'showcase/new-fashion/index', page: 'Activity'},

                // market
                {name: 'market/gcanvas', page: 'Gcanvas'},
            ]
        }

    }
</script>
