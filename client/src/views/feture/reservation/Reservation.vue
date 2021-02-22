<template>
  <div style="margin-top: 10px;">
    fdafdas
  </div>
</template>

<script>
import sportGroundService from '@/service/sportGround';

export default {
  name: 'Reservation',
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      const id = this.$store.state.globalArea.sportGroundId;
      sportGroundService.getSportGroundById(id).then((res) => {
        console.log(res);
      });
    }
  }
};
</script>

<style scoped>

</style>
