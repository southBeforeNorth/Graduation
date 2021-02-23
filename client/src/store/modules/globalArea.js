export default {
  state: {
    tabActiveKey: localStorage.getItem('tabActiveKey') ? localStorage.getItem('tabActiveKey') : 'key_0',
    isLoading: false,
    sportGroundId: localStorage.getItem('sportGroundId') ? localStorage.getItem('sportGroundId') : false
  },
  mutations: {
    SET_IS_LOADING(state, isLoading) {
      state.isLoading = isLoading;
    },
    SET_SPORT_GROUND_ID(state, sportGroundId) {
      state.sportGroundId = sportGroundId;
      localStorage.setItem('sportGroundId', sportGroundId);
    },
    SET_TAB_ACTIVE_KEY(state, tabActiveKey) {
      state.tabActiveKey = tabActiveKey;
      localStorage.setItem('tabActiveKey', tabActiveKey);
    }
  },
  actions: {
  },
  modules: {
  }
};
