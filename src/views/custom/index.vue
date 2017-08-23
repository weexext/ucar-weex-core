<template>
    <div  class="container">
        <navpage title="UCAR-WEEX" leftItemTitle="返回" @naviBarLeftItemClick="naviBarLeftItemClick"/>
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
    import uweex from 'ucar-weex';
    export default {
        components:{
          navpage:require("../../include/navpage.vue")
        },
        data() {
            return {
                items: [
                    // common
                    {name: 'uNavigator', page: 'pageA.js'},
                    // component
                    {name: 'Text', page: 'component/text.js'},
                    {name: 'Image', page: 'component/image.js'},
                    // module
                    {name: 'module/instance-api', page: 'Instance API'},
                    // showcase
                    {name: 'showcase/new-fashion/index', page: 'Activity'},
                    // market
                    {name: 'market/gcanvas', page: 'Gcanvas'},
                ]
            }
        },
        created: function () {
          console.log('index.vue created')
            console.log('uweex=' + uweex.utils.appName);
            console.log('uweex=' + uweex.utils.isString(5));
            uweex.bridge.addEventListener('PAGE_INDEX',(event)=>{

          });
        },
        methods:{
            onItemClick(item){
                uweex.bridge.postGlobalEvent('test', {key: '你好'});
                let options = {
                    url: item.page,
                    param: {
                        'KEY_INDEX': 'VALUE_INDEX'
                    },
                    navBar:{
                        backgroundColor: '#000000',
                    }
                };
              uweex.router.push(options,()=> {
                  console.log(uweex.appName)
              })
            }
        },

    }
</script>
