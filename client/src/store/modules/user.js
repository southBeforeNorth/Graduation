export default {
  state: {
    isLogin: localStorage.getItem('isLogin') ? localStorage.getItem('isLogin') : false,
    name: localStorage.getItem('userName') ? localStorage.getItem('userName') : '',
    type: localStorage.getItem('type') ? localStorage.getItem('type') : ''
  },
  mutations: {
    SET_IS_LOGIN(state, isLogin) {
      state.isLogin = isLogin;
      localStorage.setItem('isLogin', isLogin);
    },
    SET_IS_NAME(state, name) {
      state.name = name;
      localStorage.setItem('userName', name);
    },
    SET_TYPE(state, type) {
      state.type = type;
      localStorage.setItem('type', type);
    }
  },
  actions: {
  },
  modules: {
  }
};
