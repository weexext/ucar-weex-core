<template>
<div class="no-data-shelter">
  <div class="no-data-no-space">
    <image :src="noDataImage" class="no-data-image"></image>
  </div>
  <div class="no-data-no-space-label-row">
    <text class="no-data-label">{{noDataLabelTitle}}</text>
  </div>
</div>
</template>
<style scoped>
.no-data-shelter {
  margin-top: 200;
  margin-left: 0;
  width: 750;
  height: 500;
}

.no-data-no-space {
  padding: 0px;
  margin: 0px;
  text-align: center;
}

.no-data-image {
  margin-left: 275;
  width: 200px;
  height: 280px;
}

.no-data-no-space-label-row {
  padding: 0px;
  margin: 0px;
  align-items: center;
  margin-top: 20px;
}

.no-data-label {
  font-size: 36;
  color: #385198;
  margin-top: 20px;
}

</style>
<script>
  const config = require("../config");
  module.exports = {
    props: {
      noDataImage: {default: config.image('no-data.png')},
      noDataLabelTitle: {default: ''},
    },
    methods: {},
  };
</script>
