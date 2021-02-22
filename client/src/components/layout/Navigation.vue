<template>
  <div>
    <a-menu v-model="current" mode="horizontal">
      <template v-for="(target, index) in navigationList">
      <a-menu-item
        v-if="target.key !== 'space' && !isReservation"
        :key="index"
        :style="computeStyle(index, target.key)">
        <router-link
          :to="target.description"
          tag="a"
        >
          <a-icon :type="target.value" />
          {{ $t('home.navigation.'+target.key)}}
        </router-link>
      </a-menu-item>
        <a-menu-item
          v-if="target.key === 'space' && isReservation"
          :key="index"
          style="margin-left: 10%">
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
  computed: {
    isReservation() {
      const name = this.$route.fullPath.split('/');
      return name[name.length - 1] === 'reservation';
    }
  },
  mounted() {
    this.getAllNavigations();
  },
  methods: {
    setCurrent() {
      const name = this.$route.fullPath.split('/');
      const target = this.navigationList.find((n) => n.description.includes(name[name.length - 1]));
      this.current = [];
      this.current.push(target.sequence);
    },
    computeStyle(index) {
      if (index === 0) {
        return 'margin-left: 10%';
      }
    },
    getAllNavigations() {
      dictionaryService.getDictionaryOption('导航栏').then((n) => {
        if (n.success) {
          this.navigationList = n.data;
          this.setCurrent();
        }
      });
    }
  }
};
</script>

<style scoped>

</style>
