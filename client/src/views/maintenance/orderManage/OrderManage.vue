<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System"  v-if="type !== 'user'">
        {{ $t('order.warningText.name') }}
      </span>
      <span class="System"  v-else>
        {{ $t('order.warningText.userName') }}
      </span>
    </div>
    <div class="form-with-table-wrapper">
      <div class="select-form-wrapper">
        <a-form-model
          ref="form"
          :model="searchForm"
          layout="vertical"
        >
          <a-row
            type="flex"
            justify="start"
          >
            <a-col
              v-if="type !=='user' "
              span="5"
            >
              <a-form-model-item
                :label="this.$t('order.label.contactName')"
              >
                <a-input
                  v-model="searchForm.name"
                  :allow-clear="true"
                  @keyup.enter="searchOrder"
                />

              </a-form-model-item>
            </a-col>
            <a-col
              v-else
              span="5"
            >
              <a-form-model-item
                :label="this.$t('order.label.sportGroundName')"
              >
                <a-input
                  v-model="searchForm.sportGroundName"
                  :allow-clear="true"
                  @keyup.enter="searchOrder"
                />
              </a-form-model-item>
            </a-col>
            <a-col
              span="8"
              offset="1"
            >
              <a-form-model-item
                :label="this.$t('order.label.rangTime')"
              >
                <a-range-picker
                  v-model="searchForm.rangeTime"
                  :show-time="{format: 'HH:mm'}"
                  style="width: 100%"
                >
                  <a-icon
                    slot="suffixIcon"
                    type="calendar"
                  />
                </a-range-picker>
              </a-form-model-item>
            </a-col>
            <a-col
              offset="1"
              class="button-wrapper"
            >
              <a-form-model-item class="button-group">
                <a-button
                  type="primary"
                  class="search-btn"
                  @click="searchOrder"
                >
                  {{ $t('common.button.search') }}
                </a-button>
                <a-button
                  class="reset-btn"
                  @click="handleReset"
                >
                  {{ $t('common.button.reset') }}
                </a-button>
              </a-form-model-item>
            </a-col>
          </a-row>
        </a-form-model>
      </div>
    </div>
    <div class="table-wrapper">
      <a-menu
        v-model="current"
        @click="changeStatus"
        mode="horizontal">
        <a-menu-item key="new" v-if="type!=='manage'">
          <a-icon type="clock-circle" />
          {{this.$t('order.menu.new')}}
        </a-menu-item>
        <a-menu-item key="review" v-if="type!=='manage'">
          <a-icon type="check-circle" />
          <span v-if="type === 'merchant'">
            {{this.$t('order.menu.review')}}
          </span>
          <span v-if="type === 'user'">
            {{this.$t('order.menu.userReview')}}
          </span>
        </a-menu-item>
        <a-menu-item key="all" v-if="type!=='manage'">
          <a-icon type="filter" />
          <span v-if="type ==='merchant'">{{this.$t('order.menu.all')}}</span>
          <span v-else>{{this.$t('order.menu.userAll')}}</span>
        </a-menu-item>
      </a-menu>
      <a-table
        :pagination="pagination"
        :data-source="orderDateSource"
        :columns="columns"
        :row-key="record => record.id"
        size="middle"
      >
        <template
          v-for="col in OrderColumns"
          :slot="col"
          slot-scope="text"
        >
          <template
            v-if="col === 'orderDate'"
          >
            {{ text | date }}
          </template>
          <template
            v-else-if="col === 'status'"
          >
            {{ $t('order.status.'+text) }}
          </template>
          <template v-else>
            {{ text }}
          </template>
        </template>
        <template
          slot="action"
          slot-scope="text, record"
        >
          <div class="editable-row-operations">
              <span>
                <a @click="displayOrder(record)">{{ $t('common.view') }}</a>

                <a-popconfirm
                    v-if="menuKey === 'new' && type !== 'manage' "
                    :title="$t('order.warningText.recall')"
                    :ok-text="$t('common.button.ok')"
                   :cancel-text="$t('common.button.cancel')"
                   @confirm="recallOrder(record)"
                  >
                  <a
                    href="javascript:"
                  >{{ $t('order.button.recall') }}
                  </a>
                </a-popconfirm>

                <a-popconfirm
                   v-if="menuKey === 'review' && !isExpire(record) && type !=='user'"
                   :title="$t('order.warningText.recall')"
                   :ok-text="$t('common.button.ok')"
                   :cancel-text="$t('common.button.cancel')"
                   @confirm="recallOrder(record)"
                   >
                  <a
                    href="javascript:"
                  >{{ $t('order.button.recall') }}
                  </a>
                </a-popconfirm>

                 <a-popconfirm
                   v-if="menuKey === 'new' && type !== 'manage' && type !=='user'"
                    :title="$t('order.warningText.review')"
                    :ok-text="$t('common.button.ok')"
                    :cancel-text="$t('common.button.cancel')"
                     @confirm="reviewOrder(record)"
                     >
                     <a
                       href="javascript:"
                     >
                        {{ $t('order.button.reviewPass') }}
                     </a>
                </a-popconfirm>

                  <a
                    v-if="menuKey === 'review' && type ==='user'"
                    @click="disPlayCertificate(record)">
                    {{ $t('order.button.certificate') }}
                  </a>

                  <a
                  v-if="menuKey === 'review' && !isExpire(record) && type !=='user'"
                  @click="disPlayModel(record)">
                    {{ $t('order.button.review') }}
                  </a>

                  <a
                     v-if="menuKey === 'all'
                     && type ==='user'
                     && record.status=== 'finish'
                     && record.isComment !== true"
                     @click="createComment(record)">
                    {{ $t('order.button.comment') }}
                  </a>

                  <a
                    v-if="
                    menuKey === 'all'
                    && type ==='user'
                    && record.isComment === true"
                     @click="displayCommentModel(record)">
                    {{ $t('order.button.reComment') }}
                  </a>

                  <a-popconfirm
                  v-if="menuKey === 'all' || type ==='manage'"
                  :title="$t('order.warningText.delete')"
                  :ok-text="$t('common.button.ok')"
                  :cancel-text="$t('common.button.cancel')"
                  @confirm="deleteOrder(record)"
                  >
                     <a
                       href="javascript:"
                     >
                        {{ $t('order.button.delete') }}
                     </a>
                </a-popconfirm>

                <a-popconfirm
                v-if="menuKey === 'review' && isExpire(record) && type ==='merchant'"
                :title="$t('order.warningText.expire')"
                :ok-text="$t('common.button.ok')"
                :cancel-text="$t('common.button.cancel')"
                @confirm="changeExpire(record)"
                >
                     <a
                       href="javascript:"
                     >
                        {{ $t('order.button.expire') }}
                     </a>
                </a-popconfirm>

              </span>
          </div>
        </template>
      </a-table>
    </div>
    <merchant-model
      :visible.sync="modelVisible"
      @onClose="closeModel"
      :selectedOrderInfo.sync="selectedOrderInfo"
    />
    <comment-model
      :visible.sync="commentVisible"
      @onClose="closeModel"
      :comment-info.sync="selectedCommentInfo"
      :order-id="orderId"
      :editable="commentEditable"
    />
    <div ref="replayModal">
        <a-modal
          :get-container="()=>this.$refs.replayModal"
          v-model="isReview"
          :title="this.$t('order.warningText.header')"
          :width="400"
          :okText="this.$t('order.button.ok')"
          :cancelText="this.$t('order.button.cancel')"
          @cancel="()=>{
           this.isReview = false;
           }"
          @ok="finishOrder"
        >
          <a-input v-model="certificate"/>
        </a-modal>
    </div>
    <div ref="replayModal">
      <a-modal
        style="text-align: center"
        :get-container="()=>this.$refs.replayModal"
        v-model="isCertificate"
        :title="this.$t('order.warningText.userHeader')"
        :width="400"
        @cancel="()=>{
           this.isCertificate = false;
           }"
        @ok="()=>{
           this.isCertificate = false;
           }"
      >
        <a-row>
       <span style="color: #1890f0; font-size: 32px">
         {{certificate}}
       </span>
        </a-row>
        <a-row style="margin-top: 20px">
          {{this.$t('order.warningText.tip')}}
        </a-row>
      </a-modal>
    </div>
  </div>
</template>

<script>
import orderInfoService from '@/service/orderInfo';
import lodash from 'lodash';
import moment from 'moment';
import merchantManageConfig from './orderManage.config';
import MerchantModel from './OrderModel.vue';
import CommentModel from './CommentModel.vue';

export default {
  name: 'OrderManage',
  components: {
    MerchantModel,
    CommentModel
  },
  mixins: [merchantManageConfig],
  data() {
    return {
      commentEditable: false,
      orderId: null,
      selectedCommentInfo: null,
      commentVisible: false,
      isCertificate: false,
      id: null,
      certificate: null,
      isReview: false,
      menuKey: 'new',
      selectedOrderInfo: null,
      orderDateSource: [],
      orderList: [],
      status: this.$store.state.user.type === 'manage' ? null : 'new',
      current: ['new'],
      searchForm: {
        rangeTime: [],
        name: null,
        sportGroundName: null
      },
      modelVisible: false,
      pagination: {
        size: 'normal',
        current: 1,
        total: 1,
        pageSize: 5,
        showSizeChanger: true,
        onShowSizeChange: this.onShowSizeChange,
        onChange: this.pageChange,
        pageSizeOptions: ['5', '10', '20', '40']
      }
    };
  },
  computed: {
    type() {
      return this.$store.state.user.type;
    }
  },
  mounted() {
    this.getOrderListByPage(0, this.pagination.pageSize, 0);
  },
  methods: {
    displayCommentModel(record) {
      if (lodash.isEmpty(record.comment)) {
        this.$message.warning(this.$t('order.warningText.isDeleted'));
        return;
      }
      this.selectedCommentInfo = record.comment;
      this.orderId = record.id;
      this.commentEditable = false;
      this.commentVisible = true;
    },
    createComment(record) {
      this.orderId = record.id;
      this.selectedCommentInfo = null;
      this.commentEditable = true;
      this.commentVisible = true;
    },
    disPlayCertificate(record) {
      if (this.isExpire(record)) {
        this.$message.warning(this.$t('order.warningText.orderExpire'));
      } else {
        this.isCertificate = true;
        this.certificate = record.certificate;
      }
    },
    changeExpire(record) {
      orderInfoService.changeOrderStatus(record.id, { status: 'expire' }).then((res) => {
        if (res.success) {
          this.$message.success(this.$t('order.warningText.expireSuccess'));
          this.getOrderListByPage(0, this.pagination.pageSize, 0);
        }
      });
    },
    isExpire(record) {
      let target = false;
      const dateStr = moment(record.orderDate).format('YYYY-MM-DD,HH:mm:ss');
      const hours = this.$t(`sportGround.model.table.${record.orderDetails[0].time}`);
      const dateTime = `${dateStr.split(',')[0] } ${ hours }:00`;
      const expired = moment(dateTime, 'YYYY-MM-DD HH:mm:ss').fromNow().split(' ');
      console.log(dateTime);
      const day = expired[expired.length - 1] === 'ago'
          && (expired[expired.length - 2] === 'days' || expired[expired.length - 2] === 'day');
      const hour = expired[expired.length - 1] === 'ago' && (expired[expired.length - 2] === 'hours'
          || expired[expired.length - 2] === 'hour');
      const year = expired[expired.length - 1] === 'ago' && (expired[expired.length - 2] === 'years'
          || expired[expired.length - 2] === 'year');

      if (day || hour || year) {
        target = true;
      }
      return target;
    },
    deleteOrder(record) {
      orderInfoService.deleteOrderById(record.id)
        .then((res) => {
          if (res.success) {
            this.$message.success(this.$t('order.warningText.deleteSuccess'));
            this.getOrderListByPage(0, this.pagination.pageSize, 0);
            return;
          }
          this.$message.warning(this.$t('order.warningText.deleteError'));
        });
    },
    disPlayModel(record) {
      this.isReview = true;
      this.id = record.id;
    },
    finishOrder() {
      orderInfoService.changeOrderStatus(this.id, { status: 'finish', certificate: this.certificate })
        .then((res) => {
          if (res.success) {
            if (lodash.isEmpty(res.data.id)) {
              this.$message.warning(this.$t('order.warningText.finishError'));
              return;
            }
            this.isReview = false;
            this.$message.success(this.$t('order.warningText.finishSuccess'));
            this.getOrderListByPage(0, this.pagination.pageSize, 0);
          }
        });
    },
    reviewOrder(record) {
      orderInfoService.changeOrderStatus(record.id, { status: 'review' }).then((res) => {
        if (res.success) {
          this.$message.success(this.$t('order.warningText.reviewSuccess'));
          this.getOrderListByPage(0, this.pagination.pageSize, 0);
        }
      });
    },
    displayOrder(record) {
      this.selectedOrderInfo = record;
      this.modelVisible = true;
    },
    recallOrder(record) {
      orderInfoService.changeOrderStatus(record.id, { status: 'recall' }).then((res) => {
        if (res.success) {
          this.$message.success(this.$t('order.warningText.recallSuccess'));
          this.getOrderListByPage(0, this.pagination.pageSize, 0);
        }
      });
    },
    closeModel() {
      this.selectedOrderInfo = null;
      this.getOrderListByPage(0, this.pagination.pageSize, 0);
    },
    searchOrder() {
      this.getOrderListByPage(0, this.pagination.pageSize, 0)
        .then(() => {
          this.pagination.current = 1;
        });
    },
    handleReset() {
      this.searchForm.name = null;
      this.searchForm.rangeTime = [];
      this.searchForm.sportGroundName = null;
      this.searchOrder();
    },
    changeStatus(target) {
      if (target.key === 'all') {
        this.status = 'recall;expire;finish';
      } else {
        this.status = target.key;
      }
      this.menuKey = target.key;
      this.getOrderListByPage(0, this.pagination.pageSize, 0);
    },
    onShowSizeChange(page, pageSize) {
      this.pageChange(1, pageSize);
      this.pagination.current = 1;
      this.pagination.pageSize = pageSize;
    },
    pageChange(page, pageSize) {
      this.getOrderListByPage(page - 1, pageSize, 0);
    },
    getOrderListByPage(page, pageSize, start) {
      /*     const startDate = null;
      const end = null;
      if (!lodash.isEmpty(this.searchForm.startDate)) {
        start;
      } */
      const startTime = this.searchForm.rangeTime[0];
      const endTime = this.searchForm.rangeTime[1];
      const params = {
        page,
        pageSize,
        start,
        status: this.status,
        userName: this.searchForm.name,
        sportGroundName: this.searchForm.sportGroundName,
        startDate: lodash.isEmpty(startTime) ? null : startTime.valueOf(),
        endDate: lodash.isEmpty(endTime) ? null : endTime.valueOf()
      };
      console.log(params);
      return orderInfoService.getOrderListByPage(params).then((res) => {
        if (res.success) {
          console.log(res);
          this.pagination.current = page + 1;
          this.pagination.total = res.data.totalElements;
          const data = res.data.content;
          this.orderDateSource.splice(0, this.orderDateSource.length);
          this.orderDateSource.push(...data);
        }
      });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-modal-header {
    background: #D1E3FC;
  }
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
  .table-wrapper {
    margin: 20px;
    padding: 15px;
    background: white;
  }
  .editable-add-btn,
  .editable-add-btn:hover,
  .editable-add-btn:focus{
    width: 162px;
    height: 40px;
    font-size: 16px;
    color: #FFFFFF;
    letter-spacing: 0;
    float: right;
    position: relative;
    top: -5px;
    background: #0053C5;
    box-shadow: 0px 4px 8px rgba(0, 83, 197, 0.15);
    border-radius: 4px;
  }
  .editable-row-operations a {
    margin-right: 8px;
    text-decoration: underline;
  }
  /deep/ .ant-table-thead > tr > th {
    background: #F0F5FC;
  }
  .form-with-table-wrapper {
    background: rgb(240, 245, 252);
    margin: 20px;
  }
  .select-form-wrapper {
    height: 100px;
    background: white;
    padding: 10px 20px;
  }
  .search-btn {
    font-size: 16px;
    color: white;
  }
  .button-group .ant-btn {
    width: 120px;
    height: 40px;
    border-radius: 4px;
    margin: 22px 15px;
    border: 1px solid rgb(217, 217, 217);
  }
</style>
