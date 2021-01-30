<template >
<div style="padding: 10px 0 0 0">
  <a-layout>
    <Navigation/>
  </a-layout>
  <a-layout-content>
    <router-view/>
  </a-layout-content>
</div>
</template>

<script>
import Navigation from '@/components/layout/Navigation.vue';

export default {
  name: 'Home',
  components: {
    Navigation
  }
};
</script>

<style scoped>

</style>
