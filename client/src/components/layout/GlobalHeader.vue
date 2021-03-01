<template>
<a-layout>
  <a-layout-header>
    <div class="logo">
      <router-link
        :to="{ path: '/feature/home/homeBody' }"
      >
        <img src="../../../static/log.svg"  alt="logo" class="logo-icon"/>
        <span style="color: white; font-family: 华光行草_CNKI">
          {{ $t('home.navigation.loge') }}
        </span>
      </router-link>
    </div>
    <div class="header-bar">
      <template v-if="!isLogin">
      <router-link
        :to="{path: '/feature/login'}"
        tag="a"
      >
        <span
          class="login"
          style="margin-right:20px"
        > {{ $t('globalHeader.login') }}
        </span>
      </router-link>
      <span style="color: white">|</span>
      <router-link
        :to="{path: '/feature/register'}"
        tag="a"
      >
        <span
          class="register"
          style="margin-right:30px; margin-left: 20px"
        > {{ $t('globalHeader.register') }}
        </span>
      </router-link>
      </template>
      <template v-if="isLogin">
        <a-avatar :size="45" :src="header" icon="user" style="margin-right: 20px" />
        <a-dropdown style="margin-right: 20px">
          <a class="ant-dropdown-link" @click="e => e.preventDefault()">
            <span
              style="color: white;"
            >
              {{ $t('globalHeader.welcome')}}{{ userName }}
            </span>
            <a-icon type="down" />
          </a>
          <a-menu
            slot="overlay"
            @click="signOut"
          >
            <a-menu-item>
              {{$t('globalHeader.signOut')}}
            </a-menu-item>
          </a-menu>
        </a-dropdown>
        <span style="color: white">|</span>
        <router-link
          v-if="userType === 'manage'"
          :to="{path: '/manage/dictionary'}"
          tag="a"
        >
          <span
            class="register"
            style="margin-right:30px; margin-left: 20px;"
          > {{ $t('globalHeader.manageCenter') }}
          </span>
        </router-link>
        <router-link
          v-if="userType === 'user'"
          :to="{path: '/manage/OrderManage'}"
          tag="a"
        >
          <span
            class="register"
            style="margin-right:30px; margin-left: 20px;"
          > {{ $t('globalHeader.userCenter') }}
          </span>
        </router-link>
        <router-link
          v-if="userType === 'merchant'"
          :to="{path: '/manage/sportGroundManage'}"
          tag="a"
        >
          <span
            class="register"
            style="margin-right:30px; margin-left: 20px;"
          > {{ $t('globalHeader.merchantCenter') }}
          </span>
        </router-link>
      </template>
      <a-dropdown
        class="swapLanguage"
      >
        <a-menu
          slot="overlay"
          @click="changeLang"
        >
          <a-menu-item key="en_US">
            English
          </a-menu-item>
          <a-menu-item key="zh_CN">
            简体中文
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 22px">
          {{ lang }}<a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>
  </a-layout-header>
</a-layout>
</template>

<script>
export default {
  name: 'GlobalHeader',
  computed: {
    lang() {
      return this.$i18n.locale === 'en_US' ? 'English' : '简体中文';
    },
    isLogin() {
      return this.$store.state.user.isLogin;
    },
    userName() {
      return this.$store.state.user.name;
    },
    header() {
      return this.$store.state.user.header;
    },
    userType() {
      return this.$store.state.user.type;
    }
  },
  methods: {
    changeLang(e) {
      const lang = e.key;
      localStorage.setItem('lang', lang);
      this.$i18n.locale = lang;
    },
    signOut() {
      this.$store.commit('SET_IS_LOGIN', false);
      this.$store.commit('SET_IS_NAME', null);
      this.$store.commit('SET_TYPE', null);
      this.$store.commit('SET_TOKEN', null);
      this.$store.commit('SET_HEADER', null);
      this.$router.push({
        path: '/feature/login'
      });
    }
  }
};
</script>

<style scoped>
  .logo {
    display: inline;
    font-size: 30px;
    padding:0 10px;
    position:relative;
    top:-1.5px;
  }

  .logo-icon {
    margin-left: 5%;
    height: 100%;
    width: 6%;
    position: relative;
  }
  /deep/ .anticon.anticon-down{
    color: white;
  }
  .ant-layout-header{
    width: 100%;
    height: 64px;
    background: linear-gradient(90deg, #0181C9 0%, #0191C7 35.94%, #00B9D7 66.15%, #00B0D7 98.96%);
    -moz-box-shadow:0 4px 4px 0 rgba(0,0,0,0.3);
    -webkit-box-shadow:0 4px 4px 0 rgba(0,0,0,0.3);
    box-shadow: 0 4px 4px rgba(0, 0, 0, 0.3);
    z-index: 1;
    position: fixed;
  }
  .swapLanguage {
    width: 120px;
    height: 32px;
    color: #FFFFFF;
    font-size: 16px;
    text-align: center;
    font-weight: 500;
    background: none;
    border: 1px solid #FFFFFF;
    border-radius: 3px;
    padding:0;
  }

  .header-bar {
    color: #0053C5;
    font-size: 16px;
    display: inline;
    position: absolute;
    top: -0.2px;
    right: 40px;
  }
  .login {
    margin-right: 12px;
    font-style: normal;
    font-weight: 500;
    font-size: 16px;
    color: #FFFFFF;
  }
  .register {
    margin-right: 12px;
    font-style: normal;
    font-weight: 500;
    font-size: 16px;
    line-height: 32px;
    color: #FFFFFF;
  }
</style>
