<template>
<div ref="replayModal">
  <a-modal
    :get-container="()=>this.$refs.replayModal"
    v-model="visibleScoped"
    :title="modelTitle"
    :width="800"
    :footer="null"
    @cancel="()=>{
           this.visibleScoped = false;
           }"
  >
    <template>
      <a-descriptions :title="this.orderInfo.sportGroundName" bordered>
        <a-descriptions-item
          span="2"
          v-if="type === 'user' || type === 'manage' "
          :label="this.$t('order.label.merchantName')"
        >
          {{this.orderInfo.merchantName}}
        </a-descriptions-item>
        <a-descriptions-item
          v-if="type ==='user' || type === 'manage'"
          :label="this.$t('order.label.contactMerchant')">
          {{this.orderInfo.contactMerchant}}
        </a-descriptions-item>
        <a-descriptions-item
          span="2"
          v-if="type === 'merchant'|| type === 'manage' "
          :label="this.$t('order.label.contactName')"
        >
          {{this.orderInfo.userName}}
        </a-descriptions-item>
        <a-descriptions-item
          v-if="type === 'merchant' || type === 'manage'"
          :label="this.$t('order.label.contactPhone')">
          {{this.orderInfo.contactPhone}}
        </a-descriptions-item>
        <a-descriptions-item :label="this.$t('order.label.totalPrice')">
          <span style="color: #1890f0">{{this.orderInfo.totalPrice}}</span>
        </a-descriptions-item>
        <a-descriptions-item :label="this.$t('order.label.orderDate')" :span="1">
         <span style="color: #1890f0">
           {{this.orderInfo.orderDate | date}}
         </span>
        </a-descriptions-item>
        <a-descriptions-item :label="this.$t('order.label.status')" :span="2">
          <a-badge status="processing" :text="this.$t('order.status.'+orderInfo.status)" />
        </a-descriptions-item>
        <a-descriptions-item :label="this.$t('order.label.areaTime')">
          <template v-for="(item, index) in orderInfo.orderDetails">
            <span :key="index" style="color: #1890f0">
              {{item.areaName+':  '}}{{$t(`sportGroundDetail.label.${item.time}`)}}
            </span>
            <br :key="item.id" />
          </template>
        </a-descriptions-item>
      </a-descriptions>
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
    selectedOrderInfo: {
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
      orderInfo: null,
      //
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
    type() {
      return this.$store.state.user.type;
    },
    visibleScoped: {
      get() {
        return this.visible;
      },
      set(visible) {
        this.$emit('update:visible', visible);
      }
    },
    modelTitle() {
      return this.$t('merchant.register.label.view');
    }
  },
  watch: {
    selectedOrderInfo(newInfo) {
      if (!lodash.isEmpty(newInfo)) {
        this.orderInfo = newInfo;
      } else {
        this.orderInfo = null;
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
