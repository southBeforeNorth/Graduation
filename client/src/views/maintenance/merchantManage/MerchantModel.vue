<template>
<div ref="replayModal">
  <a-modal
    :get-container="()=>this.$refs.replayModal"
    v-model="visibleScoped"
    :title="modelTitle"
    :width="700"
    :footer="null"
    @cancel="()=>{
           this.$refs.merchantModel.clearValidate();
           this.visibleScoped = false;
           }"
  >
    <template>
      <a-form-model
        ref="merchantModel"
        :label-col="{span: 6}"
        :wrapper-col="{span: 16}"
        :model="form"
        :rules="rules"
      >
        <a-form-model-item
          :label="$t('merchant.register.label.merchantName')"
          prop="merchantName"
        >
          <a-input
            :disabled="!editable"
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
            :disabled="!editable"
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
            :disabled="!editable"
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
                      :disabled="!editable"
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
            :disabled="!editable"
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
            :disabled="!editable"
            :placeholder="$t('register.placeHolder.password')"
            v-model="form.password"
          >
            <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
          </a-input-password >
        </a-form-model-item>
        <a-form-model-item
          v-if="editable"
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
          :label="$t('merchant.register.label.active')"
        >
          <a-switch
            :disabled="!editable"
            v-model="form.active"
          />
        </a-form-model-item>
        <a-row
          v-if="editable"
        >
          <a-col
            span="16"
            :push="5"
          >
            <a-form-model-item
              label=" "
              :colon="false"
            >
              <a-button type="primary" @click="submitForm()"
                        v-if="this.selectedMerchantInfo === null">
                {{ $t('merchant.register.label.submit') }}
              </a-button>
              <a-button type="primary" @click="updateForm()"
                        v-if="this.selectedMerchantInfo !== null">
                {{ $t('merchant.register.label.update') }}
              </a-button>
              <a-button style="margin-left: 10px"
                        @click="()=>{
                        this.$refs.merchantRegister.resetFields();
                        this.form.active= false;
                        }">
                {{ $t('register.warningText.reset') }}
              </a-button>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </template>
  </a-modal>
</div>
</template>

<script>
import merchantService from '@/service/merchant';
import city from '@/views/feture/merchant/city.values';
import rules from '@/views/feture/merchant/register.rules';
import lodash from 'lodash';

export default {
  name: 'MerchantModel',
  mixins: [city, rules],
  props: {
    editable: {
      type: Boolean,
      default: false
    },
    selectedMerchantInfo: {
      type: Object,
      default: null
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
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
        rePassword: null,
        active: false
      },
      id: null,
      merchantNameList: []
    };
  },
  computed: {
    visibleScoped: {
      get() {
        return this.visible;
      },
      set(visible) {
        this.$emit('update:visible', visible);
      }
    },
    modelTitle() {
      if (this.selectedMerchantInfo === null) {
        return this.$t('merchant.register.label.add');
      }
      if (this.editable === true) {
        return this.$t('merchant.register.label.edit');
      }
      return this.$t('merchant.register.label.view');
    }
  },
  watch: {
    selectedMerchantInfo(newInfo) {
      if (!lodash.isEmpty(newInfo)) {
        this.id = newInfo.id;
        this.form.merchantName = newInfo.merchantName;
        this.form.contactPerson = newInfo.contactPerson;
        this.form.phone = newInfo.phone;
        this.form.active = newInfo.active;
        this.form.detailedAddress = newInfo.detailedAddress;
        this.form.city = newInfo.area.split('/');
        this.form.password = newInfo.password;
        this.form.rePassword = newInfo.password;
      } else {
        this.id = null;
        this.form.merchantName = null;
        this.form.contactPerson = null;
        this.form.phone = null;
        this.form.active = false;
        this.form.detailedAddress = null;
        this.form.city = [];
        this.form.password = null;
        this.form.rePassword = null;
        this.$refs.merchantModel.clearValidate();
      }
    }
  },
  mounted() {
    this.getMerchantNameList();
  },
  methods: {
    updateForm() {
      const that = this;
      this.$refs.merchantModel.validate().then(() => {
        const merchant = {
          merchantName: this.form.merchantName.trim(),
          phone: this.form.phone,
          password: this.form.password.trim(),
          contactPerson: this.form.contactPerson.trim(),
          area: this.form.city.join('/'),
          detailedAddress: this.form.detailedAddress.trim(),
          active: this.form.active
        };
        merchantService.updateMerchant(this.id, merchant).then((n) => {
          if (n.success) {
            that.visibleScoped = false;
            that.$emit('onClose');
            that.$message.success(this.$t('merchant.register.label.addSuccess'));
            return;
          }
          that.$message.error(this.$t('merchant.register.label.error'));
        });
      });
    },
    submitForm() {
      const that = this;
      this.$refs.merchantModel.validate().then(() => {
        const merchant = {
          merchantName: this.form.merchantName.trim(),
          phone: this.form.phone,
          password: this.form.password.trim(),
          contactPerson: this.form.contactPerson.trim(),
          area: this.form.city.join('/'),
          detailedAddress: this.form.detailedAddress.trim(),
          active: this.form.active
        };
        merchantService.createMerchantByManager(merchant).then((n) => {
          if (n.success) {
            that.visibleScoped = false;
            that.$emit('onClose');
            that.$message.success(this.$t('merchant.register.label.updateSuccess'));
            return;
          }
          that.$message.error(this.$t('merchant.register.label.updateError'));
        });
      });
    },
    getMerchantNameList() {
      merchantService.getMerchantNameList().then((n) => {
        if (n.success) {
          this.merchantNameList = n.data;
        }
      });
    },
    onChange(value) {
      this.form.city = [];
      value.forEach((item) => {
        // eslint-disable-next-line operator-assignment
        this.form.city.push(item);
      });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-modal-header {
    background: #D1E3FC;
  }
  /deep/ .ant-input {
    height: 40px;
  }
</style>
