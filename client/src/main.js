import Vue from 'vue';
import Antd from 'ant-design-vue';
import VueQuillEditor from 'vue-quill-editor';
// eslint-disable-next-line import/no-extraneous-dependencies
import 'quill/dist/quill.core.css';
// eslint-disable-next-line import/no-extraneous-dependencies
import 'quill/dist/quill.snow.css';
// eslint-disable-next-line import/no-extraneous-dependencies
import 'quill/dist/quill.bubble.css';
import moment from 'moment';
import i18n from '@/assets/lang';
import timeFilter from '@/common/filters/time.filter';
import App from './App.vue';
import router from './router';
import store from './store';
import 'ant-design-vue/dist/antd.css';

Vue.use(Antd);
Vue.use(VueQuillEditor);
Vue.prototype.$moment = moment;
Vue.config.productionTip = false;

Vue.filter('dateTime', timeFilter.dateTime);
Vue.filter('date', timeFilter.date);
Vue.filter('momentZoneTime', timeFilter.momentZoneTime);

new Vue({
  router,
  store,
  i18n,
  render: (h) => h(App)
}).$mount('#app');
