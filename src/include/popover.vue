<template>
  <div class="popover-backdrop" @click="cancelPopover">
    <div class="popover">
      <div class="popover-wrapper">
        <div class="popover-row">
          <image class="image" :src="location_light"></image>
          <text class="pop-text-left">地点</text>
          <text class="pop-text-right">{{meetingAddress}}</text>
        </div>
        <div class="popover-row">
          <image class="image" :src="person_light"></image>
          <text class="pop-text-left">可容纳人数</text>
          <text class="pop-text-right">{{meetingPeoples}}</text>
        </div>
        <div class="popover-row">
          <image class="image" :src="equipment"></image>
          <text class="pop-text-left">设备</text>
          <text class="pop-text-right">{{meetingDevice}}</text>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.popover-backdrop {
  position: fixed;
  top: 0;
  bottom: 0;
  opacity: 0.1;
  background-color: black;
  width: 750;
}

.popover {
  position: fixed;
  top: 150;
  left: 0;
  right: 0;
  background-color: white;
  flex-direction: column;
  /*justify-content: space-around;*/
  opacity: 1;
  margin-left: 35px;
  margin-right: 35px;
  height: 340px;
}

.popover-wrapper {
  margin-top: 48px;
  margin-bottom: 48px;
}

.popover-row {
  flex-direction: row;
  align-items: center;
  margin-left: 32px;
  margin-bottom: 48px;
}

.pop-text-left {
  font-size: 28px;
  color: #bdbdbd;
  margin-left: 4px;
  margin-right: 16px;
}

.pop-text-right {
  font-size: 28px;
  color: #212121;
}

.image {
  height: 48px;
  width: 48px;
}

</style>

<script>
  const config = require("../config");
module.exports = {
  props: {
    person_light: {default: config.image('icon_person_light.png')},
    location_light: {default: config.image('icon_location_light.png')},
    equipment: {default: config.image('icon_equipment.png')},
    meetingAddress: {default: ''},
    meetingPeoples: {default: ''},
    meetingDevice: {default: ''},
  },
  methods: {
    cancelPopover:function(e){
      this.$emit('cancelPopover', e);
    },
  }
}
</script>
