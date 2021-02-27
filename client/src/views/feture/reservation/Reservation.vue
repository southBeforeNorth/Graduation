<template>
  <div style="margin-top: 40px;">
    <div>
      <a-row>
        <a-col span="15" push="2">
      <a-descriptions
        style="margin: auto"
        :column="1"
        :title="name">
        <a-descriptions-item
          :label="$t('sportGroundDetail.label.phone')"
        >
          {{phone}}
        </a-descriptions-item>
        <a-descriptions-item
          :label="$t('sportGroundDetail.label.price')"
        >
        <span style="font-weight: bolder;
              color: #1890ff;
              font-size: 30px; font-family: 华光行草_CNKI">
          {{price}}
        </span>
          <span>
            {{ this.$t('sportGroundDetail.label.priceTime')}}
          </span>
        </a-descriptions-item>
        <a-descriptions-item
          :label="$t('sportGroundDetail.label.address')"
        >
          {{address}}
        </a-descriptions-item>
      </a-descriptions>
        </a-col>
        <a-col span="9">
          <img :src="picture" style="width: 400px; height: 200px"/>
        </a-col>
      </a-row>
    </div>
    <a-divider></a-divider>
    <div>
      <a-row>
        <a-col span="14" push="2">
      <a-tabs
        v-model="tabActiveKey"
        :tab-position="mode"
        style=" overflow-x: auto"
        @prevClick="callback"
        @nextClick="callback"
        @change="changeTab"
      >
        <template v-for="(item, index) in dateList">
          <a-tab-pane
            v-if="item.isActive"
            :key="'key_'+index"
            :tab="item.week+' '+item.dateFormat">
            <a-table
              :scroll="{ x: tableLength }"
              :pagination="false"
              :data-source="areaList"
              :row-key="record => record.sequence"
              :columns="columns"
            >
              <template
                v-for="(col, index) in columns"
                :slot="col.dataIndex"
                slot-scope="text, record"
              >
                <a-button
                  :disabled="isAllowSelect(col, record)"
                  @click="changeButton($event ,col, record, text)"
                  v-if="col.dataIndex !=='areaName'"
                  :key="index"
                >
                  {{'￥'+text}}
                </a-button>
                <template
                  v-else
                >
                  {{text}}
                </template>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane
            v-else
            disabled
            :key="item.week"
            :tab="item.week+' '+item.dateFormat">
          </a-tab-pane>
        </template>
      </a-tabs>
        </a-col>
        <a-col span='7' push="3" >
          <a-row>
            <a-col span="8">
              <a-col span="14">
                <span>{{ this.$t('sportGroundDetail.label.isActive')}}</span>
              </a-col>
              <a-col span="10" >
                <div style="border:3px solid #e1e1e1; width: 20px;height: 20px"/>
              </a-col>
            </a-col>
            <a-col span="8">
              <a-col span="14">
                <span>{{ this.$t('sportGroundDetail.label.noSelected')}}</span>
              </a-col>
              <a-col span="10" >
                <div style="background: #e1e1e1;  width: 20px;height: 20px"/>
              </a-col>
            </a-col>
            <a-col span="8">
              <a-col span="14">
                <span>{{ this.$t('sportGroundDetail.label.selected')}}</span>
              </a-col>
              <a-col span="10" >
                <div style="background: #1890f0; width: 20px;height: 20px"/>
              </a-col>
            </a-col>
          </a-row>
          <a-divider/>
          <a-row>
            <span style="font-size: 16px">
              {{ this.$t('sportGroundDetail.label.type')}}
              {{type}}</span>
          </a-row>
          <a-row>
            <span style="font-size: 16px">
              {{ this.$t('sportGroundDetail.label.date')}}
              {{date.format}}
            </span>
          </a-row>
          <a-row>
              <span style="font-size: 16px">
              {{ this.$t('sportGroundDetail.label.selectedArea')}}
            </span>
          </a-row>
          <a-row>
            <template v-if="selectedAreaList.length === 0">
              <div class="selectRes">
                {{this.$t('sportGroundDetail.label.selectedTip')}}
              </div>
            </template>
            <template v-for="(item, index) in selectedAreaList">
              <div class="selectRes" :key="index">
                {{item.areaName}}
                {{$t(`sportGroundDetail.label.${item.time}`)}}
                {{item.price +'元'}}
              </div>
            </template>
          </a-row>
          <a-row style="margin-top: 10px">
            {{$t('sportGroundDetail.label.areaNumber') }}
            <span style="font-size: 18px; color: #1890f0">
              {{selectedAreaList.length}}
            </span>
            {{$t('sportGroundDetail.label.areaNumber1') }}
          </a-row>
          <a-row style="margin-top: 10px;font-size:24px">
            {{$t('sportGroundDetail.label.totalPrice') }}
            <span style="color: #1890f0">
              {{totalPrice+ '元'}}
            </span>
          </a-row>
          <a-row>
            <a-col push="0">
            <a-button
              @click="submit"
              :disabled="isAllowClickButton"
              size="large"
              type="primary"
              style="margin-top: 20px;margin-left: 100px;">
              {{$t('sportGroundDetail.label.button') }}
            </a-button>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import sportGroundService from '@/service/sportGround';
import dictionaryService from '@/service/dictionary';
import lodash from 'lodash';
import moment from 'moment';
import orderInfoService from '@/service/orderInfo';

export default {
  name: 'Reservation',
  data() {
    return {
      historyOrder: [],
      selectedAreaList: [],
      tableLength: 0,
      tableCol: [],
      areaList: [],
      weekList: [],
      dateList: [],
      mode: 'top',
      name: null,
      phone: null,
      price: null,
      address: null,
      picture: null,
      type: null,
      merchant: null,
      id: null
    };
  },
  mounted() {
    this.initData();
  },
  computed: {
    isAllowClickButton() {
      return !this.selectedAreaList.length > 0;
    },
    totalPrice() {
      let total = 0;
      this.selectedAreaList.forEach((n) => {
        // eslint-disable-next-line radix
        total += Number.parseInt(n.price);
      });
      return total;
    },
    date: {
      get() {
        const diff = window.Number.parseInt(this.tabActiveKey.split('_')[1]) + 1;
        const target = moment().add(diff, 'd').valueOf();
        console.log(diff);
        console.log(target);
        return {
          value: target,
          format: `${moment(target).format('YYYY年MM月DD日')}(${this.setWeek(moment(target).valueOf()) })`
        };
      }
    },
    tabActiveKey: {
      get() {
        return this.$store.state.globalArea.tabActiveKey;
      },
      set(newValue) {
        this.$store.commit('SET_TAB_ACTIVE_KEY', newValue);
      }
    },
    isLogin() {
      return this.$store.state.user.isLogin;
    },
    columns() {
      const col = [];
      col.push(
        {
          title: this.$t('sportGround.model.label.areaName'),
          dataIndex: 'areaName',
          width: 150,
          scopedSlots: { customRender: 'areaName' }
        }
      );
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.tableLength += 150;
      this.tableCol.forEach((item) => {
        const temp = {
          title: this.$t(`sportGroundDetail.label.${item}`),
          dataIndex: item,
          width: 120,
          scopedSlots: { customRender: item }
        };
        this.tableLength += 120;
        col.push(temp);
      });
      return col;
    }
  },
  methods: {
    isAllowSelect(col, record) {
      let res = false;
      this.historyOrder.forEach((n) => {
        if (n.status !== 'recall') {
          n.orderDetails.forEach((target) => {
            if (target.areaName === record.areaName && target.time === col.dataIndex) {
              res = true;
            }
          });
        }
      });
      return res;
    },
    submit() {
      const that = this;
      if (!this.isLogin) {
        this.$confirm({
          title: this.$t('sportGroundDetail.label.noLogin'),
          onOk() {
            that.$router.push({ path: '/feature/login' });
          }
        });
        return;
      }
      const target = {
        userName: this.$store.state.user.name,
        merchantId: this.merchant.id,
        merchantName: this.merchant.merchantName,
        contactMerchant: this.merchant.phone,
        sportGroundName: this.name,
        sportGroundId: this.id,
        totalPrice: this.totalPrice,
        status: 'new',
        orderDate: this.date.value,
        orderDetails: this.selectedAreaList
      };
      this.$confirm({
        title: that.$t('sportGroundDetail.label.orderConfirm'),
        okText: that.$t('sportGroundDetail.label.confirmOk'),
        cancelText: that.$t('sportGroundDetail.label.confirmCancel'),
        onOk() {
          orderInfoService.createOrder(target).then((res) => {
            if (res.success) {
              that.$success({
                title: that.$t('sportGroundDetail.label.orderSuccess'),
                okText: that.$t('sportGroundDetail.label.orderOk'),
                onOk() {
                  that.$router.go(0);
                }
              });
              return;
            }
            that.$message.warning(that.$t('sportGroundDetail.label.orderError'));
          });
        },
        onCancel() {}
      });
    },
    changeButton(event, col, record, value) {
      const target = {
        areaName: record.areaName,
        date: this.date.value,
        time: col.dataIndex,
        price: value
      };
      if (lodash.isEmpty(event.srcElement.style.background)) {
        // eslint-disable-next-line no-param-reassign
        event.srcElement.style.background = '#1890f0';
        if (this.selectedAreaList.length >= 4) {
          this.$message.warning(this.$t('sportGroundDetail.label.maxLength'));
          // eslint-disable-next-line no-param-reassign
          event.srcElement.style.background = '';
          return;
        }
        this.selectedAreaList.push(target);
      } else {
        this.selectedAreaList = this.selectedAreaList
          .filter((n) => n.areaName !== record.areaName || n.time !== col.dataIndex);
        // eslint-disable-next-line no-param-reassign
        event.srcElement.style.background = '';
      }
    },
    setWeek(date) {
      const week = moment(date).day();
      // eslint-disable-next-line default-case
      switch (week) {
        case 1:
          return '星期一';
        case 2:
          return '星期二';
        case 3:
          return '星期三';
        case 4:
          return '星期四';
        case 5:
          return '星期五';
        case 6:
          return '星期六';
        case 0:
          return '星期日';
      }
    },
    setDateList(weeks) {
      this.dateList = [];
      for (let i = 1; i < 8; i++) {
        const target = moment().add(i, 'd');
        const week = this.setWeek(target);
        const isActive = !lodash.isEmpty(weeks.find((n) => n === week));
        const temp = {
          week,
          date: target.valueOf(),
          isActive,
          dateFormat: target.format('MM/DD')
        };
        this.dateList.push(temp);
      }
    },
    getDictionary(weeks) {
      dictionaryService.getMultipleDictionaries(['星期']).then((n) => {
        const week = n.data.find((target) => target.name === '星期');
        if (!lodash.isEmpty(week)) {
          this.weekList = [];
          week.dictionaryOptions.forEach((target) => {
            this.weekList.push(target.value);
          });
          this.setDateList(weeks);
        }
      });
    },
    changeTab(value) {
      const diff = window.Number.parseInt(value.split('_')[1]) + 1;
      const target = moment().add(diff, 'd').valueOf();
      console.log(diff);
      console.log(target);
      this.selectedAreaList = [];
      this.date.value = target;
      this.date.format = `${moment(target).format('YYYY年MM月DD日')}(${this.setWeek(moment(target)) })`;
      this.$store.commit('SET_TAB_ACTIVE_KEY', value);
      window.location.reload();
    },
    callback() {
    },
    initData() {
      const base64 = 'data:image/png;base64,';
      const id = this.$store.state.globalArea.sportGroundId;
      sportGroundService.getSportGroundById(id).then((res) => {
        if (res.success) {
          this.id = res.data.id;
          this.type = res.data.type;
          this.name = res.data.name;
          this.phone = res.data.phone;
          this.price = res.data.price;
          this.address = res.data.city.split('/').join('') + res.data.detailedAddress;
          this.picture = base64 + res.data.pictures[0].fileContent;
          const weeks = res.data.weeks.split(';');
          this.areaList = res.data.areas;
          this.tableCol = res.data.timeArea.split(';');
          this.getDictionary(weeks);
        }
      });
      sportGroundService.getMerchantById(id).then((res) => {
        if (res.success) {
          this.merchant = res.data;
        }
      });
      orderInfoService.getOrderInfoBySportGroundId(id, { date: this.date.value })
        .then((res) => {
          if (res.success) {
            this.historyOrder = res.data;
          }
        });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-descriptions-title{
    font-size: 24px;
  }
  /deep/ .ant-descriptions-item-content {
    font-size: 14px;
  }
  /deep/ .ant-descriptions-item-label {
    font-size: 14px;
  }

  .selectRes {
    color: #1890f0;
    text-align: center;
    border:2px solid #1890f0;
    width: 300px;
    margin-top: 8px;
    height: 30px;
    box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.3);
  }

</style>
