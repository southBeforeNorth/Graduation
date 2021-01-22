<template>
  <div>
    <h1>test</h1>
    <a-input>e</a-input>
    <button @click="createUser">user</button>
    <br>
    <button @click="login">test</button>
  </div>
</template>

<script>
import userService from '@/service/user';

export default {
  name: 'User',
  mounted() {
    this.getUserById();
  },
  methods: {
    getUserById() {
      userService.getUserById().then((n) => {
        console.log(n);
      });
    },
    createUser() {
      const user = { name: '王德略2', birthDay: '2020-01-01', sex: '男' };
      userService.createUser(user).then((n) => {
        if (n.success) {
          console.log(n);
        }
      });
    },
    login() {
      const user = { name: '王德略2', birthDay: '2020-01-01', sex: '男' };
      userService.login(user).then((n) => {
        if (n.success) {
          console.log(n);
          this.$store.commit('SET_TOKEN', n.data);
        }
      });
    }
  }
};
</script>

<style scoped>

</style>
