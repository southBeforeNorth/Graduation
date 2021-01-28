export default {
  state: {
    isLogin: false,
    name: ''
  },
  mutations: {
    SET_IS_LOGIN(state, isLogin) {
      state.isLogin = isLogin;
    },
    SET_IS_NAME(state, name) {
      state.name = name;
    }
  },
  actions: {
  },
  modules: {
  }
};
