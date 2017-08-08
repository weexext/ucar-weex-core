<template>
<div class="header">
  <div class="search">
    <image :src="searchImage" class="searchImg"></image>
    <input type="text" return-key-type="search" :placeholder="placeholder" class="input" @input="searchBarOnInput" @change="searchBarOnchange" @return = "onreturn" ref="search-input" />
  </div>
  <div class="cancel-container">
    <text class="cancel" @click="searchBarCancelClick">取消</text>
  </div>
</div>
</template>
<style scoped>
.header {
  top: 0;
  left: 0;
  right: 0;
  width: 750;
  height: 128;
  background-color: #385198;
  flex-direction: row;
}

.search {
  height: 60;
  width: 600;
  margin-left: 24;
  margin-top: 50;
  flex-direction: row;
  align-items: center;
  background-color: #fff;
  border-radius: 20;
}

.input {
  font-size: 30;
  color: #000;
  height: 40;
  width: 540;
  margin-left: 5;
  placeholder-color: #b2b2b2;
}

.searchImg {
  margin-left: 10;
  width: 40;
  height: 40;
}

.cancel {
  font-size: 34;
  height: 34;
  width: 100;
  color: #fff;
}

.cancel-container {
  margin-top: 60;
  margin-left: 40;
}

.search-box {
  margin-left: 30;
  margin-right: 30;
  margin-top: 20;
  width: 690;
  height: 70;
  flex-direction: row;
  align-items: center;
}

.search-box-title {
  width: 230;
  line-height: 32;
  font-size: 32;
  color: #374F9A;
  text-align: center;
}
</style>
<script>
  const config = require("../config");
module.exports = {
  props: {
    placeholder: {default: ''},
    searchImage: {default: config.image('search.png')},
  },
  methods: {
    searchBarCancelClick: function (e) {
      this.$emit('searchBarCancelClick', e)
    },
    searchBarOnInput:function (e) {
      console.log("searchBarOnInput"+e)
      this.$emit('searchBarOnInput', e)
    },
    searchBarOnchange: function(e) {
      console.log("searchBarOnchange"+e)
      this.$emit('searchBarOnchange', e)
    },
    onreturn: function (event) {
      console.log("onreturn:"+ event.value)
      //键盘收起
      this.$refs['search-input'].blur()
      this.$emit('onreturn', event)
    },
  },
}
</script>
