import Vue from 'vue';
import Vuex from 'vuex';
import globalArea from './modules/globalArea';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
    }
  },
  actions: {
  },
  modules: {
    globalArea
  }
});
