<template>
  <list-item v-on:click="redirect">
    <text class="item-txt">{{title}}</text>
  </list-item>
</template>

<style scoped>
  .item-txt {
    font-size: 48px;
    color: #555;
  }
</style>

<script>
  var navigator = weex.requireModule('navigator')

  module.exports = {
    props: {
      title: { default: '456' },
      url: { default: '' }
    },
    components: {
      listItem: require('./list-item.vue')
    },
    methods: {
      redirect: function() {
//        event.openURL(this.url)
        let bundleUrl = weex.config.bundleUrl
        let baseURL = bundleUrl.substring(0, bundleUrl.lastIndexOf("/"))
        baseURL = baseURL.substring(0, baseURL.lastIndexOf("/"))
        let url = baseURL+'/'+this.url
        navigator.push({url:url},()=>{})
      }
    }
  }
</script>
