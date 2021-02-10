<template>
    <div class="login-background">
      <div class="login-form">
      <template style="background: #bae7ff">
        <a-menu
          v-model="current"
          @click="changeType"
          mode="horizontal">
          <a-menu-item
            key="user"
            v-if="!computeManager"
          >
            <a-icon type="user" />
            {{this.$t('login.label.user')}}
          </a-menu-item>
          <a-menu-item key="merchant"
                       v-if="!computeManager"
          >
            <a-icon type="shop" />
            {{this.$t('login.label.merchant')}}
          </a-menu-item>
          <a-menu-item
            v-if="computeManager"
            key="manage">
            <a-icon type="user" />
            {{this.$t('login.label.manageName')}}
          </a-menu-item>
        </a-menu>
        <a-form-model
          style="margin-top: 40px"
          ref="login"
          :model="form"
          :rules="rules"
          :label-col="{span: 6}"
          :wrapper-col="{span: 12}"
        >
          <a-form-model-item
            :label="computeLabel"
            prop="name"
          >
            <a-input
              :placeholder="computePlaceholder"
              v-model="form.name"
            >
              <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)" />
            </a-input>
          </a-form-model-item>
          <a-form-model-item
            :label="$t('login.label.password')"
            prop="password"
          >
            <a-input-password
              :placeholder="$t('login.placeHolder.password')"
              v-model="form.password"
            >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password >
          </a-form-model-item>
          <a-form-model-item
            label=" "
            :colon="false"
          >
            <a-button type="primary" @click="login">
              {{$t('login.button.login')}}
            </a-button>
            <a-button style="margin-left: 10px" @click ="register">
              {{$t('login.button.register')}}
            </a-button>
          </a-form-model-item>
        </a-form-model>
      </template>
      </div>
      <VCode
        :show="isShow"
        @success="VCodeSuccess"
        @close="VCodeClose"
      />
    </div>
</template>

<script>
import VCode from 'vue-puzzle-vcode';
import rules from '@/views/feture/login/login.rules';
import userService from '@/service/user';
import lodash from 'lodash';
import merchantService from '@/service/merchant';

export default {
  name: 'Login',
  mixins: [rules],
  components: {
    VCode
  },
  data() {
    return {
      isShow: false,
      form: {
        name: null,
        password: null
      },
      current: ['user'],
      type: 'user'
    };
  },
  computed: {
    computeManager() {
      return this.$router.history.current.fullPath === '/feature/manage-login';
    },
    computePlaceholder() {
      if (this.type === 'user') {
        return this.$t('login.placeHolder.userName');
      }
      if (this.type === 'merchant') {
        return this.$t('login.placeHolder.merchantName');
      }
      if (this.type === 'manage') {
        return this.$t('login.placeHolder.manageName');
      }
      return 'null';
    },
    computeLabel() {
      if (this.type === 'user') {
        return this.$t('login.label.userName');
      }
      if (this.type === 'merchant') {
        return this.$t('login.label.merchantName');
      }
      if (this.type === 'manage') {
        return this.$t('login.label.manageName');
      }
      return 'null';
    }
  },
  mounted() {
    this.computeRouterPath();
  },
  methods: {
    computeRouterPath() {
      if (this.$router.history.current.fullPath === '/feature/manage-login') {
        this.type = 'manage';
      }
    },
    changeType(type) {
      this.$refs.login.resetFields();
      this.$refs.login.clearValidate();
      this.type = type.key;
    },
    login() {
      this.$refs.login.validate().then(() => {
        this.isShow = true;
      });
    },
    register() {
      this.$router.push({ path: 'register' });
    },
    VCodeSuccess() {
      this.isShow = false;
      if (this.type === 'user' || this.type === 'manage') {
        this.userLogin();
        return;
      }
      this.merchantLogin();
    },
    merchantLogin() {
      const merchant = {
        merchantName: this.form.name.trim(),
        password: this.form.password
      };
      merchantService.login(merchant).then((n) => {
        if (n.success) {
          this.$message.success(this.$t('login.warningText.success'));
          merchantService.getMerchantById().then((res) => {
            if (res.success) {
              this.$store.commit('SET_IS_NAME', res.data.merchantName);
            }
          });
          this.$store.commit('SET_TOKEN', n.data);
          this.$store.commit('SET_IS_LOGIN', true);
          const curr = localStorage.getItem('preRoute');
          if (lodash.isEmpty(curr)) {
            console.log(curr);
          } else {
            this.$router.push({ path: curr });
          }
          return;
        }
        this.$message.error(this.$t('login.warningText.error'));
      });
    },
    userLogin() {
      const user = {
        name: this.form.name,
        password: this.form.password,
        type: this.type
      };
      userService.login(user).then((n) => {
        if (n.success) {
          this.$message.success(this.$t('login.warningText.success'));
          userService.getUserById().then((res) => {
            if (res.success) {
              this.$store.commit('SET_IS_NAME', res.data.name);
              this.$store.commit('SET_TYPE', res.data.type);
            }
          });
          this.$store.commit('SET_TOKEN', n.data);
          this.$store.commit('SET_IS_LOGIN', true);
          const curr = localStorage.getItem('preRoute');
          if (lodash.isEmpty(curr)) {
            console.log(curr);
          } else {
            this.$router.push({ path: curr });
          }
          return;
        }
        this.$message.error(this.$t('login.warningText.error'));
      });
    },
    VCodeClose() {}

  }
};
</script>

<style scoped>
  .login-background {
    height: 700px;
    background: url(../../../../static/login.jpg) no-repeat;
  }
  .login-form {
    margin-top:100px;
    float: right;
    width: 600px;
    height: 300px;
    position:relative;
    margin-right: 100px;
    background-color: rgba(255,255,255,0.5);
  }
  /deep/ .ant-menu-horizontal {
    font-weight: bolder;
    background-color: rgba(255,255,255,0.1);
  }
  /deep/ .ant-input {
    height: 40px;
  }
  /deep/ .verify-btn {
    height: 40px;
  }
</style>
