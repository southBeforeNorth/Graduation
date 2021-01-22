import VueI18n from 'vue-i18n';
import Vue from 'vue';
import zh_CN from './zh_CN';
import en_US from './en_US';

Vue.use(VueI18n);


export default new VueI18n({
  locale: localStorage.getItem('lang') || 'zh_CN',
  messages: {
    zh_CN,
    en_US
  }
});
