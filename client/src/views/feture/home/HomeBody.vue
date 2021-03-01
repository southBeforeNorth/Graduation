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
    <div>
      <a @click="toDetailPage(pictureList[0])">
        <img src="../../../../static/home2.jpg"  alt="" style="margin: auto"/>
      </a>
    </div>
    <div>
      <a @click="toDetailPage(pictureList[1])">
        <img src="../../../../static/home1.jpg"  alt="" style="margin: auto"/>
      </a>
    </div>    <div>
    <a @click="toDetailPage(pictureList[2])">
      <img src="../../../../static/home3.jpg"  alt="" style="margin: auto"/>
    </a>
  </div>
  </a-carousel>
</div>
    <div style="text-align: center; margin-top: 30px">
      <span style="color: #0191C7;font-size: 40px; margin: auto; font-family: 华光胖头鱼_CNKI">
      {{ $t('home.body.searchText') }}
    </span>
    </div>

    <HomeBodyList/>
  </div>
</template>

<script>
import HomeBodyList from '@/components/layout/HomeBodyList.vue';
import dictionaryService from '@/service/dictionary';

export default {
  name: 'HomeBody',
  components: {
    HomeBodyList
  },
  data() {
    return {
      pictureList: []
    };
  },
  mounted() {
    this.getDictionary();
  },
  methods: {
    toDetailPage(detailId) {
      this.$store.commit('SET_SPORT_GROUND_ID', detailId.value);
      const route = this.$router.resolve({ name: 'reservation' });
      this.$store.commit('SET_TAB_ACTIVE_KEY', 'key_0');
      window.open(route.href, '_blank');
    },
    getDictionary() {
      dictionaryService.getMultipleDictionaries(['首页图片']).then((n) => {
        const pictures = n.data.find((target) => target.name === '首页图片');
        this.pictureList = pictures.dictionaryOptions;
      });
    }
  }
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
    width: 100%;
    margin: auto;
  }
  .ant-carousel >>> .slick-slide {
    text-align: center;
    height: 550px;
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
