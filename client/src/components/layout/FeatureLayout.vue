<template>
<div>
  <Loading/>
  <a-layout>
      <GlobalHeader/>
    <a-layout style="padding: 64px 0 0 0" >
      <a-layout-content>
        <router-view/>
      </a-layout-content>
    </a-layout>
    <a-layout>
    </a-layout>
  </a-layout>

</div>
</template>

<script>
import Loading from '../loading/Loading.vue';
import GlobalHeader from './GlobalHeader.vue';

export default {
  name: 'FeatureLayout',
  components: {
    Loading,
    GlobalHeader
  }

};
</script>

<style scoped>
/deep/ .ant-layout-content {
  background: white;
}
</style>
