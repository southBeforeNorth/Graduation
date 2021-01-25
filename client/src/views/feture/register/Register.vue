<template>
  <div>
    <TopPicture/>
    <div>
      <a-divider style="font-size: 38px">{{ $t('register.warningText.top') }}</a-divider>
    </div>
    <div>
      <template>
        <a-form-model
          ref="register"
          :label-col="{span: 8}"
          :wrapper-col="{span: 8}"
          :model="form"
          :rules="rules"
        >
          <a-form-model-item
            :label="$t('register.label.name')"
            prop="name"
          >
            <a-input
              :placeholder="$t('register.placeHolder.name')"
              v-model="form.name"
            >
              <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)" />
            </a-input>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('register.label.password')"
            prop="password"
          >
            <a-input-password
              :placeholder="$t('register.placeHolder.password')"
              v-model="form.password"
            >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password >
          </a-form-model-item>
          <a-form-model-item
            :label="$t('register.label.rePassword')"
            prop="rePassword"
          >
            <a-input-password
              :placeholder="$t('register.placeHolder.rePassword')"
              v-model="form.rePassword"
            >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password >
          </a-form-model-item>
          <a-form-model-item
            :label="$t('register.label.birthDay')"
            prop="birthDay"
          >
            <a-date-picker
              v-model="form.birthDay"
              type="date"
              :disabled-date="disabledDate"
              :placeholder="$t('register.placeHolder.birthDay')"
              style="width: 100%;"
            />
          </a-form-model-item>
          <a-form-model-item
            :label="$t('register.label.sex')"
            prop="sex"
          >
            <a-radio-group
              v-model="form.sex">
              <a-radio value="男" >
                <a-icon type="man" />
                {{ $t('register.label.boy') }}
              </a-radio>
              <a-radio value="2">
                <a-icon type="woman" />
                {{ $t('register.label.girl') }}
              </a-radio>
            </a-radio-group>
          </a-form-model-item>
          <a-form-model-item
            label=" "
            :colon="false"
          >
            <a-row>
              <a-col span="8">
              <a-button type="primary" @click="submitForm()" >
                {{ $t('register.warningText.submit') }}
              </a-button>
              <a-button style="margin-left: 10px" @click="resetForm()">
                {{ $t('register.warningText.reset') }}
              </a-button>
            </a-col>
            <a-col span ="16">
              <Verify
                style="margin-left:130px"
                @success="validateSuccess"
                @error="validateError"
                :type="1"
                :codeLength="4"
                v-if="isShowValidatePicture"/>
            </a-col>
            </a-row>
          </a-form-model-item>
        </a-form-model>
      </template>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import Verify from 'vue2-verify';
import userService from '@/service/user';
import TopPicture from '@/components/topPicture/TopPicture.vue';
import rules from './register.rules';

export default {
  name: 'Register',
  mixins: [rules],
  components: {
    Verify,
    TopPicture
  },
  data() {
    return {
      userNameList: [],
      form: {
        name: null,
        password: null,
        rePassword: null,
        birthDay: null,
        sex: null
      },
      isShowValidatePicture: true
    };
  },
  mounted() {
    this.getUserNameList();
  },
  methods: {
    getUserNameList() {
      userService.getUserNameList().then((n) => {
        if (n.success) {
          this.userNameList = n.data;
        }
      });
    },
    disabledDate(current) {
      return moment().diff(current) < 0;
    },
    submitForm() {
      this.$refs.register.validate().then(() => {
        if (this.isShowValidatePicture) {
          this.$message.warning(this.$t('register.warningText.validate'));
          return;
        }
        const user = {
          name: null,
          password: null,
          birthDay: null,
          sex: null
        };
        user.name = this.form.name;
        user.password = this.form.password;
        user.birthDay = moment(this.form.birthDay).valueOf();
        user.sex = this.form.sex;
        userService.createUser(user).then((n) => {
          if (n.success) {
            this.$message.success(this.$t('register.warningText.success'));
            this.$router.push({
              path: '/login'
            });
            return;
          }
            this.$message.error(this.$t('register.warningText.error'));
        });
      });
    },
    resetForm() {
      this.isShowValidatePicture = true;
      this.$refs.register.resetFields();
    },
    validateSuccess() {
      this.isShowValidatePicture = false;
    },
    validateError() {
      this.$message.warning(this.$t('register.warningText.validateError'));
    }
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
