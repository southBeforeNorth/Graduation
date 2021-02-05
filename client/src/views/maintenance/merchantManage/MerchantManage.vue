<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System">{{ $t('merchant.manage.name') }}</span>
      <a-button
        name="addButton"
        class="editable-add-btn"
        @click="addMerchant"
      >
        <a-icon type="plus" />{{ $t('merchant.manage.addButton') }}
      </a-button>
    </div>
    <div class="form-with-table-wrapper">
      <div class="select-form-wrapper">
        <a-form-model
          ref="form"
          :model="form"
          layout="vertical"
        >
          <a-row
            type="flex"
            justify="start"
          >
            <a-col
              span="5"
            >
              <a-form-model-item
                :label="this.$t('merchant.manage.merchantName')"
              >
                <a-input
                v-model="form.merchantName"
                :allow-clear="true"
                @keyup.enter="searchMerchant"
              />

              </a-form-model-item>
            </a-col>
            <a-col
              span="5"
              offset="1"
            >
              <a-form-model-item
                :label="this.$t('merchant.manage.contactPerson')"
              >
                <a-input
                  v-model="form.contactPerson"
                  :allow-clear="true"
                  @keyup.enter="searchMerchant"
                />
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
                  @click="searchMerchant"
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
        <a-menu-item key="allMerchant">
          <a-icon type="team" />
          {{this.$t('merchant.manage.allMerchant')}}
        </a-menu-item>
        <a-menu-item key="apply">
          <a-icon type="user-add" />
          {{this.$t('merchant.manage.apply')}}
        </a-menu-item>
      </a-menu>
      <a-table
        :pagination="pagination"
        :data-source="merchantDataSource"
        :columns="columns"
        :row-key="record => record.merchantName"
        size="middle"
      >
        <template
          v-for="col in merchantColumns"
          :slot="col"
          slot-scope="text, record"
        >
        <template
          v-if="col === 'lastModifiedTime'"
        >
          {{ text | dateTime }}
        </template>
          <template  v-else-if=" col === 'active' ">
            <a-switch
              :key="col"
              :disabled="true"
              v-model="record.active" />
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
                <a @click="displayMerchant(record)">{{ $t('common.view') }}</a>
                <a @click="editMerchant(record)">{{ $t('common.button.edit') }}</a>
                <a-popconfirm
                  :title="$t('common.warningText.delete')"
                  :ok-text="$t('common.button.ok')"
                  :cancel-text="$t('common.button.cancel')"
                  @confirm="deleteMerchant(record)"
                >
                  <a
                    href="javascript:"
                  >{{ $t('common.button.delete') }}</a>
                </a-popconfirm>
              </span>
          </div>
        </template>
      </a-table>
    </div>
    <merchant-model
      :visible.sync="modelVisible"
      @onClose="closeModel"
      :editable="editable"
      :selected-merchant-info.sync="selectedMerchantInfo"
    />
  </div>
</template>

<script>
import merchantService from '@/service/merchant';
import merchantManageConfig from './merchantManage.config';
import MerchantModel from './MerchantModel.vue';

export default {
  name: 'MerchantManage',
  components: {
    MerchantModel
  },
  mixins: [merchantManageConfig],
  data() {
    return {
      modelVisible: false,
      current: ['allMerchant'],
      form: {
        merchantName: null,
        contactPerson: null
      },
      merchantDataSource: [],
      pagination: {
        size: 'normal',
        current: 1,
        total: 1,
        pageSize: 5,
        showSizeChanger: true,
        onShowSizeChange: this.onShowSizeChange,
        onChange: this.pageChange,
        pageSizeOptions: ['5', '10', '20', '40']
      },
      active: null,
      selectedMerchantInfo: null,
      editable: false
    };
  },
  mounted() {
    this.getMerchantByPage(0, this.pagination.pageSize, 0);
  },
  methods: {
    editMerchant(record) {
      this.selectedMerchantInfo = record;
      this.editable = true;
      this.modelVisible = true;
    },
    displayMerchant(record) {
      this.selectedMerchantInfo = record;
      this.editable = false;
      this.modelVisible = true;
    },
    deleteMerchant(record) {
      merchantService.deleteMerchant(record.id)
        .then((n) => {
          if (n.success) {
            this.$message.success(this.$t('common.successText.delete'));
            this.getMerchantByPage(0, this.pagination.pageSize, 0);
          }
        });
    },
    closeModel() {
      this.selectedMerchantInfo = null;
      this.getMerchantByPage(0, this.pagination.pageSize, 0);
    },
    addMerchant() {
      this.modelVisible = true;
      this.editable = true;
      this.selectedMerchantInfo = null;
    },
    searchMerchant() {
      this.getMerchantByPage(0, this.pagination.pageSize, 0)
        .then(() => {
          this.pagination.current = 1;
        });
    },
    handleReset() {
      this.form.merchantName = '';
      this.form.contactPerson = '';
      this.searchMerchant();
    },
    changeStatus(target) {
      if (target.key === 'allMerchant') {
        this.active = null;
      }
      if (target.key === 'apply') {
        this.active = false;
      }
      this.getMerchantByPage(0, this.pagination.pageSize, 0);
    },
    onShowSizeChange(page, pageSize) {
      this.pageChange(1, pageSize);
      this.pagination.current = 1;
      this.pagination.pageSize = pageSize;
    },
    pageChange(page, pageSize) {
      this.getMerchantByPage(page - 1, pageSize, 0);
    },
    getMerchantByPage(page, pageSize, start) {
      const merchantName = this.form.merchantName;
      const contactPerson = this.form.contactPerson;
      const active = this.active;
      const params = {
        page, pageSize, start, merchantName, contactPerson, active
      };
      merchantService.getMerchantByPage(params).then((n) => {
        this.pagination.current = page + 1;
        this.pagination.total = n.data.totalElements;
        const data = n.data.content;
        this.merchantDataSource.splice(0, this.merchantDataSource.length);
        this.merchantDataSource.push(...data);
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
