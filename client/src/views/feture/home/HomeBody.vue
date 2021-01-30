<template>
  <div>
<div style="margin-top: 10px;">
  <a-carousel arrows>
    <div
      slot="prevArrow"
      class="custom-slick-arrow"
      style="left: 10px;zIndex: 1"
    >
      <a-icon type="left-circle" />
    </div>
    <div slot="nextArrow" class="custom-slick-arrow" style="right: 10px">
      <a-icon type="right-circle" />
    </div>
    <div style="text-align: center">
      <a >
        <img src="../../../../static/homeBody5.jpg"  alt="" style="margin: auto"/>
      </a>
    </div>
    <div><h3>3</h3></div>
    <div><h3>4</h3></div>
  </a-carousel>
</div>
    <a-divider style="color: #0191C7;font-size: 40px; font-family: 华光胖头鱼_CNKI">
      {{ $t('home.body.searchText') }}
    </a-divider>
  <div style="text-align: center">
      <a-input-search
        :placeholder="$t('home.body.search')"
        :enter-button="$t('home.body.searchButton')"
        size="large"
        @search="onSearch"
      />
  </div>
  </div>
</template>

<script>
export default {
  name: 'HomeBody'
};
</script>

<style scoped>
  /deep/ .ant-input-group-wrapper{
    width: 40%;
    height: 60px;
    border-radius: 30px;
    border-width: 3px;
  }
  .log-top{
    width: 100%; height: 100%;
  }
  /deep/ .ant-carousel{
    width: 80%;
    margin: auto;
  }
  .ant-carousel >>> .slick-slide {
    text-align: center;
    height: 250px;
    line-height: 250px;
    background: #364d79;
    overflow: hidden;
  }

  .ant-carousel >>> .custom-slick-arrow {
    width: 25px;
    height: 25px;
    font-size: 25px;
    color: #fff;
    background-color: rgba(31, 45, 61, 0.11);
    opacity: 0.3;
  }
  .ant-carousel >>> .custom-slick-arrow:before {
    display: none;
  }
  .ant-carousel >>> .custom-slick-arrow:hover {
    opacity: 0.5;
  }

  .ant-carousel >>> .slick-slide h3 {
    color: #fff;
  }
</style>
