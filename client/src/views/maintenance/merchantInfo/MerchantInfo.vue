<template>
<div class="root">
  <div class="header-wrapper">
    <span class="System">{{ $t('merchantInfo.label.name') }}</span>
  </div>
    <div class="table-wrapper">
    <template>
      <a-form-model
        ref="merchantOwnerForm"
        style="margin-top: 30px"
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
        <a-row>
            <a-form-model-item
              label=" "
              :colon="false"
            >
              <a-button type="primary" @click="updateMerchant()" >
                {{ $t('merchantInfo.button.submit') }}
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
import lodash from 'lodash';
import city from '@/views/feture/merchant/city.values';
import merchantService from '@/service/merchant';
import rules from './merchanInfo.rules';

export default {
  mixins: [city, rules],
  name: 'MerchantInfo',
  data() {
    return {
      cities: city,
      form: {
        merchantName: null,
        contactPerson: null,
        phone: null,
        city: [],
        detailedAddress: null
      },
      merchantNameList: [],
      merchant: null
    };
  },
  mounted() {
    this.initDate();
  },
  methods: {
    resetForm() {
      this.initForm(this.merchant);
      this.$refs.merchantOwnerForm.clearValidate();
    },
    initForm(newInfo) {
      this.form.merchantName = newInfo.merchantName;
      this.form.contactPerson = newInfo.contactPerson;
      this.form.phone = newInfo.phone;
      this.form.detailedAddress = newInfo.detailedAddress;
      this.form.city = newInfo.area.split('/');
    },
    updateMerchant() {
      const that = this;
      this.$refs.merchantOwnerForm.validate().then(() => {
        const merchant = {
          id: this.merchant.id,
          merchantName: this.form.merchantName.trim(),
          phone: this.form.phone,
          contactPerson: this.form.contactPerson.trim(),
          area: this.form.city.join('/'),
          detailedAddress: this.form.detailedAddress.trim()
        };
        merchantService.updateMerchantByOwner(merchant).then((n) => {
          if (n.success) {
            that.$success({
              title: that.$t('merchantInfo.warningText.updateSuccess'),
              okText: that.$t('sportGroundDetail.label.orderOk'),
              onOk() {
                that.$store.commit('SET_IS_NAME', n.data.merchantName);
                that.$router.go(0);
              }
            });
            return;
          }
          that.$message.error(this.$t('merchantInfo.warningText.updateError'));
        });
      });
    },
    onChange(value) {
      this.form.city = [];
      value.forEach((item) => {
        // eslint-disable-next-line operator-assignment
        this.form.city.push(item);
      });
    },
    initDate() {
      merchantService.getMerchantNameList().then((n) => {
        if (n.success) {
          this.merchantNameList = n.data;
        }
      });
      merchantService.getMerchantById().then((res) => {
        if (res.success) {
          const newInfo = res.data;
          this.merchant = lodash.cloneDeep(newInfo);
          this.initForm(lodash.cloneDeep(newInfo));
        }
      });
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
