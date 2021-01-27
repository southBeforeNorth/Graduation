<template>
<div>
  <GlobalHeader/>
  <a-layout :style="{ height: screenHeight + 'px', padding: '60px 0 0 0' }">
    <a-layout-sider>
      <Menu/>
    </a-layout-sider>
    <a-layout-content
      :style="{background: '#F0F5FC', height: screenHeight + 'px' }"
    >
      <router-view
        :style="{ margin: '15px 15px', padding:
              '15px 15px'}"
      />
    </a-layout-content>
  </a-layout>
</div>
</template>

<script>
import GlobalHeader from '@/components/layout/GlobalHeader.vue';
import Menu from '@/components/menu/Menu.vue';

export default {
  name: 'ManageLayout',
  components: {
    GlobalHeader,
    Menu
  },
  data() {
    return {
      screenHeight: document.body.clientHeight - 60
    };
  }
};
</script>

<style scoped>
  .ant-layout-sider,.ant-menu{
    background: #fdfdfd;
  }
</style>
