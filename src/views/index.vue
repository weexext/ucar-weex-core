<template>
    <div class="container">
        <navpage title="UCAR-WEEX" />
        <list>
            <cell v-for="item in items" @click="onItemClick(item)">
                <div class="row">
                    <text class="row-name">{{item.name}}</text>
                </div>
            </cell>
        </list>
    </div>
</template>
<style scoped>
    .container {
        flex:1;
        background-color: #f3f3f3;
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
    import uweex from 'ucar-weex'
    export default {
        components:{
          navpage:require("../include/navpage.vue")
        },
        data() {
            return {
                items: [
                    // common
                    {name: 'common', page: 'vue/index.js'},
                    // custom
                    {name: 'custom', page: 'custom/index.js'},
                ]
            }
        },
        created () {
          console.log('views/index.vue created')
            uweex.bridge.addEventListener('test',(e)=>{
                console.log('registerBroadcast='+JSON.stringify(e));
            });
        },
        methods:{
          onItemClick(item) {
            let options = {
              url:item.page
            }
            uweex.router.push(options,()=>{
                console.log('uweex')
            })
          }
        },
    }
</script>
