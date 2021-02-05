<template>
<div>
  <div>
  <TopPicture/>
  </div>
  <div>
    <a-divider style="font-size: 38px; font-family: 华光行楷_CNKI; color: slategray">
      {{ $t('merchant.register.label.topText') }}
    </a-divider>
  </div>
  <div>
    <template>
      <a-form-model
        ref="merchantRegister"
        :label-col="{span: 8}"
        :wrapper-col="{span: 8}"
        :model="form"
        :rules="rules"
      >
        <a-form-model-item
          :label="$t('merchant.register.label.merchantName')"
          prop="merchantName"
        >
          <a-input
            :placeholder="$t('merchant.register.placeHolder.merchantName')"
            v-model="form.merchantName"
          >
            <a-icon slot="prefix" type="bank" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>

        <a-form-model-item
          :label="$t('merchant.register.label.contactPerson')"
          prop="contactPerson"
        >
          <a-input
            :placeholder="$t('merchant.register.placeHolder.contactPerson')"
            v-model="form.contactPerson"
          >
            <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>

        <a-form-model-item
          :label="$t('merchant.register.label.phone')"
          prop="phone"
        >
          <a-input
            :placeholder="$t('merchant.register.placeHolder.phone')"
            v-model="form.phone"
          >
            <a-icon slot="prefix" type="phone" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item
        :label="$t('merchant.register.label.city')"
        prop="city"
      >
        <a-cascader :options="cities"
                    :value="this.form.city"
                    :placeholder="$t('merchant.register.placeHolder.city')"
                    @change="onChange" />

      </a-form-model-item>

        <a-form-model-item
          :label="$t('merchant.register.label.detailedAddress')"
          prop="detailedAddress"
        >
          <a-textarea
            v-model="form.detailedAddress"
            :auto-size="{ minRows: 3, maxRows: 5 }"
            :placeholder="$t('merchant.register.placeHolder.detailedAddress')"
          >

          </a-textarea>
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
        <a-row>
          <a-col
            span="9"
            :push="5"
          >
            <a-form-model-item
              label=" "
              :colon="false"
            >
              <a-button type="primary" @click="submitForm()" >
                {{ $t('merchant.register.label.submit') }}
              </a-button>
              <a-button style="margin-left: 10px" @click="resetForm()">
                {{ $t('register.warningText.reset') }}
              </a-button>
            </a-form-model-item>
          </a-col>
          <a-col span="8"
                 :push="2"
          >
            <Verify
              style="margin-left:130px"
              @success="validateSuccess"
              @error="validateError"
              :type="1"
              :codeLength="4"
              v-if="isShowValidatePicture"/>
          </a-col>
        </a-row>
      </a-form-model>
    </template>
  </div>
</div>
</template>

<script>
import merchantService from '@/service/merchant';
import moment from 'moment';
import TopPicture from '@/components/topPicture/TopPicture.vue';
import Verify from 'vue2-verify';
import city from './city.values';
import rules from './register.rules';

export default {
  components: {
    TopPicture,
    Verify
  },
  mixins: [city, rules],
  name: 'Register',
  data() {
    return {
      cities: city,
      form: {
        merchantName: null,
        contactPerson: null,
        phone: null,
        city: [],
        detailedAddress: null,
        password: null,
        rePassword: null
      },
      merchantNameList: [],
      isShowValidatePicture: true
    };
  },
  mounted() {
    this.getMerchantNameList();
  },
  methods: {
    getMerchantNameList() {
      merchantService.getMerchantNameList().then((n) => {
        if (n.success) {
          this.merchantNameList = n.data;
        }
      });
    },
    submitForm() {
      const that = this;
      this.$refs.merchantRegister.validate().then(() => {
        if (this.isShowValidatePicture) {
          this.$message.warning(this.$t('register.warningText.validate'));
          return;
        }
        const merchant = {
          merchantName: this.form.merchantName.trim(),
          phone: this.form.phone,
          password: this.form.password.trim(),
          contactPerson: this.form.contactPerson.trim(),
          area: this.form.city.join('/'),
          detailedAddress: this.form.detailedAddress.trim()
        };
        merchantService.createMerchant(merchant).then((n) => {
          if (n.success) {
            this.$success({
              title: this.$t('merchant.register.label.success'),
              content: this.$t('merchant.register.label.successContent'),
              onOk() {
                that.$router.push({ path: '/feature/home/homeBody' });
              }
            });
            return;
          }
          this.$error({
            title: this.$t('merchant.register.label.error')
          });
        });
      });
    },
    disabledDate(current) {
      return moment().diff(current) < 0;
    },
    onChange(value) {
      this.form.city = [];
      value.forEach((item) => {
        // eslint-disable-next-line operator-assignment
        this.form.city.push(item);
      });
    },
    resetForm() {
      this.isShowValidatePicture = true;
      this.$refs.merchantRegister.resetFields();
    },
    validateSuccess() {
      this.$message.success(this.$t('merchant.register.label.validateSuccess'));
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
</style>
