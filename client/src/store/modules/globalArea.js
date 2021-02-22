export default {
  state: {
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
    }
  },
  actions: {
  },
  modules: {
  }
};
