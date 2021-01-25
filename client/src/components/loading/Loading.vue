<template>
  <div
    v-if="this.$store.state.globalArea.isLoading"
    class="wrapper"
  >
    <div class="position">
      <a-spin
        size="large"
        tip="Loading..."
      />
    </div>
  </div>
</template>

<script>

export default {
  name: 'Loading'
};
</script>

<style scoped>

.wrapper {
  position: fixed;
  text-align: center;
  background-color: #eaf4f6;
  width: 100%;
  height: 100%;
  z-index:10000000;
  opacity: 0.8;
}

.position {
  top: 45%;
  position: relative;
}

</style>
