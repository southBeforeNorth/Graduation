<template>
    <div>
      <TopPicture/>
      <div>
        <a-divider style="font-size: 38px">{{ $t('login.warningText.top') }}</a-divider>
      </div>
      <div>
      <template>
        <a-form-model
          ref="login"
          :model="form"
          :rules="rules"
          :label-col="{span: 8}"
          :wrapper-col="{span: 8}"
        >
          <a-form-model-item
            :label="$t('login.label.name')"
            prop="name"
          >
            <a-input
              :placeholder="$t('login.placeHolder.name')"
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
import TopPicture from '@/components/topPicture/TopPicture.vue';
import VCode from 'vue-puzzle-vcode';
import rules from '@/views/feture/login/login.rules';
import userService from '@/service/user';
import lodash from 'lodash';

export default {
  name: 'Login',
  mixins: [rules],
  components: {
    TopPicture,
    VCode
  },
  data() {
    return {
      isShow: false,
      form: {
        name: null,
        password: null
      }
    };
  },
  methods: {
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
      userService.login(this.form).then((n) => {
        if (n.success) {
          this.$message.success(this.$t('login.warningText.success'));
          userService.getUserById().then((user) => {
            if (n.success) {
              this.$store.commit('SET_IS_NAME', user.data.name);
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

      // todo
      // this.$store.commit('SET_IS_LOADING', true);
    },
    VCodeClose() {}

  }
};
</script>

<style scoped>
  /deep/ .ant-input {
    height: 40px;
  }
  /deep/ .verify-btn {
    height: 40px;
  }
</style>
