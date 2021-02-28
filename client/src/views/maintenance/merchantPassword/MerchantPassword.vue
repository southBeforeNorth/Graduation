<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System">{{ $t('merchantInfo.label.name') }}</span>
    </div>
    <div class="table-wrapper">
      <template>
        <a-form-model
          style="margin-top: 30px"
          ref="merchantPassword"
          :label-col="{span: 8}"
          :wrapper-col="{span: 8}"
          :model="form"
          :rules="rules"
        >

          <a-form-model-item
            :label="$t('merchantPassword.label.oldPassword')"
            prop="oldPassword"
          >
            <a-input-password
              :placeholder="$t('merchantPassword.placeHolder.oldPassword')"
              v-model="form.oldPassword"
            >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password >
          </a-form-model-item>
          <a-form-model-item
            :label="$t('merchantPassword.label.newPassword')"
            prop="newPassword"
          >
            <a-input-password
              :placeholder="$t('merchantPassword.placeHolder.newPassword')"
              v-model="form.newPassword"
            >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password >
          </a-form-model-item>
          <a-form-model-item
            :label="$t('register.label.rePassword')"
            prop="rePassword"
          >
            <a-input-password
              :placeholder="$t('merchantPassword.placeHolder.rePassword')"
              v-model="form.rePassword"
            >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password >
          </a-form-model-item>
          <a-row>
              <a-form-model-item
                label=" "
                :colon="false"
              >
                <a-button type="primary" @click="submitForm()" >
                  {{ $t('merchant.register.label.submit') }}
                </a-button>
                <a-button style="margin-left: 40px" @click="resetForm()">
                  {{ $t('register.warningText.reset') }}
                </a-button>
              </a-form-model-item>
          </a-row>
        </a-form-model>
      </template>
    </div>
  </div>
</template>

<script>
import merchantService from '@/service/merchant';
import lodash from 'lodash';
import rules from './merchantPassword.rules';

export default {
  name: 'MerchantPassword',
  mixins: [rules],
  data() {
    return {
      form: {
        oldPassword: null,
        newPassword: null,
        rePassword: null
      }
    };
  },
  methods: {
    submitForm() {
      const that = this;
      this.$refs.merchantPassword.validate().then(() => {
        const params = {
          oldPassword: this.form.oldPassword,
          newPassword: this.form.newPassword
        };
        merchantService.changeMerchantPasswordByOwner(params)
          .then((res) => {
            if (res.success) {
              if (!lodash.isEmpty(res.data.id)) {
                that.$success({
                  title: that.$t('merchantPassword.warningText.updateSuccess'),
                  okText: that.$t('sportGroundDetail.label.orderOk'),
                  onOk() {
                    that.$store.commit('SET_IS_LOGIN', false);
                    that.$store.commit('SET_IS_NAME', null);
                    that.$store.commit('SET_TYPE', null);
                    that.$store.commit('SET_TOKEN', null);
                    that.$router.push({
                      path: '/feature/login'
                    });
                  }
                });
                return;
              }
              that.$message.error(this.$t('merchantPassword.warningText.updateError'));
            }
          });
      });
    },
    resetForm() {
      this.form.oldPassword = null;
      this.form.newPassword = null;
      this.form.rePassword = null;
      this.$refs.merchantPassword.clearValidate();
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
