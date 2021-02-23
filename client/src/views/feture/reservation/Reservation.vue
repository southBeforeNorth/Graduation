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

export default {
  name: 'Reservation',
  data() {
    return {
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
      type: null
    };
  },
  mounted() {
    this.initData();
  },
  computed: {
    date: {
      get() {
        const diff = this.tabActiveKey.split('_')[1];
        const target = moment().add(diff, 'd').valueOf();
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
          title: this.$t(`sportGround.model.table.${item}`),
          dataIndex: item,
          width: 100,
          scopedSlots: { customRender: item }
        };
        this.tableLength += 100;
        col.push(temp);
      });
      return col;
    }
  },
  methods: {
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
      for (let i = 0; i < 7; i++) {
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
      const target = moment().add(value.split('_')[1], 'd').valueOf();
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

</style>
