<template>
<div>
  <Loading/>
  <a-layout>
      <GlobalHeader/>
      <a-layout-content style="padding: 64px 0 0 0">
        <router-view/>
      </a-layout-content>
      <GlobalFoot/>
  </a-layout>

</div>
</template>

<script>
import Loading from '../loading/Loading.vue';
import GlobalHeader from './GlobalHeader.vue';
import GlobalFoot from './GlobalFoot.vue';

export default {
  name: 'FeatureLayout',
  components: {
    Loading,
    GlobalHeader,
    GlobalFoot
  }

};
</script>

<style scoped>
/deep/ .ant-layout-content {
  background: white;
}
</style>
