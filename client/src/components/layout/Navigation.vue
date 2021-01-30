<template>
  <div>
    <a-menu v-model="current" mode="horizontal">
      <template v-for="(target, index) in navigationList">
      <a-menu-item :key="index" :style="computeStyle(index)">
        <router-link
          :to="target.description"
          tag="a"
        >
          <a-icon :type="target.value" />
          {{ $t('home.navigation.'+target.key)}}
        </router-link>
      </a-menu-item>
      </template>
    </a-menu>
  </div>
</template>

<script>
import dictionaryService from '@/service/dictionary';

export default {
  name: 'Navigation',
  data() {
    return {
      current: [0],
      navigationList: []
    };
  },
  mounted() {
    this.getAllNavigations();
  },
  methods: {
    computeStyle(index) {
      if (index === 0) {
        return 'margin-left: 10%';
      }
    },
    getAllNavigations() {
      dictionaryService.getDictionaryOption('导航栏').then((n) => {
        if (n.success) {
          this.navigationList = n.data;
        }
      });
    }
  }
};
</script>

<style scoped>

</style>
