<template>
<div class="header" :style="{height:height}">
  <image v-if="leftItemSrc" naviItemPosition="left" :src="leftItemSrc" class="left-image" v-on:click="naviBarLeftItemClick"></image>
  <text v-if="leftItemTitle" naviItemPosition="left" :style="{ color: leftItemColor }" class="left-text" v-on:click="naviBarLeftItemClick">{{leftItemTitle}}</text>

  <text v-if="rightItemTitle" naviItemPosition="right" :style="{ color: rightItemColor }" class="right-text" v-on:click="naviBarRightItemClick">{{rightItemTitle}}</text>

  <div class="center-view">
    <text class="to-do-list" :class="[switchIndex?'background-color-normal':'background-color-highlight',]" v-on:click="onClickLeftSwitchItem">待处理</text>
    <text class="done-list" :class="[switchIndex?'background-color-highlight':'background-color-normal',]" v-on:click="onClickRightSwithcItem">已处理</text>
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
}

.background-color-highlight {
  background-color: #349bfc;
}

.background-color-normal {
  background-color: none;
}

.left-image {
  position: absolute;
  bottom: 20;
  left: 28;
  width: 50;
  height: 50;
}
.left-text {
  position: absolute;
  bottom: 28px;
  left: 70px;
  text-align: left;
  font-size: 32px;
}

.right-text {
  position: absolute;
  bottom: 28;
  right: 32;
  text-align: right;
  font-size: 32;
}

.center-view {
  position: absolute;
  bottom: 15px;
  border-radius: 30;
  border-width: 2;
  border-color: #349BFC;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  height: 60;
  left: 235;
}

.to-do-list {
  float: left;
  width: 140;
  height: 60;
  line-height: 56;
  border-bottom-left-radius: 28;
  border-top-left-radius: 28;
  font-size: 26;
  text-align: center;
  color: white;
}

.done-list {
  float: right;
  width: 140;
  height: 60;
  line-height: 56;
  border-bottom-right-radius: 28;
  border-top-right-radius: 28;
  font-size: 26;
  text-align: center;
  color: white;
}
</style>
<script>
  const config = require("../config");
module.exports = {
  props: {
    switchIndex: {
      default: 0
    },
    placeholder: {
      default: ''
    },
    leftItemSrc: {
      default: config.image('icon_arrow_back.png')
    },
    leftItemTitle:{
      default:'返回'
    },
    rightItemTitle: {
      default: "筛选"
    },
    leftItemColor:{
      default: "#ffffff"
    },
    rightItemColor: {
      default: "#ffffff"
    },
    height: {
      default: weex.config.env.platform == 'android' ? 98 : 128
    }
  },
  methods: {
    onClickLeftSwitchItem: function(e) {
      this.switchIndex = 0;
      this.$emit('onClickLeftSwitchItem');
    },
    onClickRightSwithcItem: function(e) {
      this.switchIndex = 1;
      this.$emit('onClickRightSwithcItem');
    },

    naviBarRightItemClick: function(e) {
      this.$emit('naviBarRightItemClick', e)
    },
    naviBarLeftItemClick: function(e) {
      this.$emit('naviBarLeftItemClick', e)
    },
  },
}
</script>
