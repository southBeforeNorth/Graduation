<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System">{{ $t('merchantInfo.label.name') }}</span>
    </div>
    <div class="table-wrapper">
      <template >
        <a-form-model
          style="margin-top: 40px"
          ref="userInfoForm"
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
            :label="$t('register.label.phone')"
            prop="phone"
          >
            <a-input
              :placeholder="$t('register.placeHolder.phone')"
              v-model="form.phone"
            >
              <a-icon slot="prefix" type="phone" style="color:rgba(0,0,0,.25)" />
            </a-input>
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
            <a-button type="primary" @click="updateUser()" >
              {{ $t('userInfo.button.submit') }}
            </a-button>
            <a-button style="margin-left: 10px" @click="resetForm()">
              {{ $t('userInfo.button.reset') }}
            </a-button>
          </a-form-model-item>
        </a-form-model>
      </template>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import userService from '@/service/user';
import rules from './userInfo.rules';

export default {
  name: 'UserInfo',
  mixins: [rules],
  data() {
    return {
      form: {
        name: null,
        phone: null,
        birthDay: null,
        sex: null
      },
      userNameList: [],
      user: null
    };
  },
  mounted() {
    this.initDate();
  },
  methods: {
    resetForm() {
      this.initForm(this.user);
      this.$refs.userInfoForm.clearValidate();
    },
    updateUser() {
      const that = this;
      this.$refs.userInfoForm.validate().then(() => {
        const user = {
          name: this.form.name,
          phone: this.form.phone,
          birthDay: moment(this.form.birthDay),
          sex: this.form.sex
        };
        userService.updateUserByOwner(user).then((n) => {
          if (n.success) {
            that.$success({
              title: that.$t('merchantInfo.warningText.updateSuccess'),
              okText: that.$t('sportGroundDetail.label.orderOk'),
              onOk() {
                that.$store.commit('SET_IS_NAME', n.data.name);
                that.$router.go(0);
              }
            });
            return;
          }
          that.$message.error(this.$t('merchantInfo.warningText.updateError'));
        });
      });
    },
    initForm(target) {
      this.form.name = target.name;
      this.form.phone = target.phone;
      this.form.birthDay = moment(target.birthDay);
      this.form.sex = target.sex;
    },
    initDate() {
      userService.getUserNameList().then((n) => {
        if (n.success) {
          this.userNameList = n.data;
        }
      });
      userService.getUserById().then((res) => {
        if (res.success) {
          this.user = res.data;
          this.initForm(res.data);
        }
      });
    },
    disabledDate(current) {
      return moment().diff(current) < 0;
    }
  }

};
</script>

<style scoped>
  .root {
    margin: 0 !important;
    padding: 0 !important;
  }
  .header-wrapper {
    background: #D1E3FC;
    padding: 20px;
  }
  .System{
    font-size: 20px;
    color: #333333;
    font-family: Raleway Bold;
    line-height: 26px;
  }
  .form-with-table-wrapper {
    background: rgb(240, 245, 252);
    margin: 20px;
  }
  .table-wrapper {
    margin: 20px;
    padding: 15px;
    background: white;
  }
  /deep/ .ant-input {
    height: 40px;
  }
</style>
