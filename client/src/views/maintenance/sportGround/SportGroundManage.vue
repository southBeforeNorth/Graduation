<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System">{{ $t('sportGround.manage.name') }}</span>
      <a-button
        name="addButton"
        class="editable-add-btn"
        @click="addMerchant"
      >
        <a-icon type="plus" />{{ $t('sportGround.manage.addButton') }}
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
                :label="this.$t('sportGround.model.label.name')"
              >
                <a-input
                v-model="form.name"
                :allow-clear="true"
                @keyup.enter="searchSportGround"
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
                  @click="searchSportGround"
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
      <a-table
        :pagination="pagination"
        :data-source="sportGroundDataSource"
        :columns="columns"
        :row-key="record => record.id"
        size="middle"
      >
        <template
          v-for="col in sportGroundColumns"
          :slot="col"
          slot-scope="text, record"
        >
        <template
          v-if="col === 'lastModifiedTime'"
        >
          {{ text | dateTime }}
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
                <a @click="displaySportGround(record)">{{ $t('common.view') }}</a>
                <a @click="editSportGround(record)">{{ $t('common.button.edit') }}</a>
                <a-popconfirm
                  :title="$t('common.warningText.delete')"
                  :ok-text="$t('common.button.ok')"
                  :cancel-text="$t('common.button.cancel')"
                  @confirm="deleteSportGround(record)"
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
  <sport-ground-model
      :visible.sync="modelVisible"
      @onClose="closeModel"
      :editable="editable"
      :selected-sport-ground-info.sync="selectedSportGroundInfo"
    />
  </div>
</template>

<script>
import merchantService from '@/service/merchant';
import SportGroundModel from '@/views/maintenance/sportGround/SportGroundModel.vue';
import sportGroundManageConfig from '@/views/maintenance/sportGround/sportGroundManage.config';
import sportGroundService from '@/service/sportGround';

export default {
  name: 'SportGroundManage',
  components: {
    SportGroundModel
  },
  mixins: [sportGroundManageConfig],
  data() {
    return {
      modelVisible: false,
      current: ['allMerchant'],
      form: {
        name: null
      },
      sportGroundDataSource: [],
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
      selectedSportGroundInfo: null,
      editable: false
    };
  },
  mounted() {
    this.getSportGroundById(0, this.pagination.pageSize, 0);
  },
  methods: {
    editSportGround(record) {
      this.selectedSportGroundInfo = record;
      this.editable = true;
      this.modelVisible = true;
    },
    displaySportGround(record) {
      this.selectedSportGroundInfo = record;
      this.editable = false;
      this.modelVisible = true;
    },
    deleteSportGround(record) {
      sportGroundService.deleteSportGround(record.id)
        .then((n) => {
          if (n.success) {
            this.$message.success(this.$t('common.successText.delete'));
            this.getSportGroundById(0, this.pagination.pageSize, 0);
          }
        });
    },
    closeModel() {
      this.selectedSportGroundInfo = null;
      this.getSportGroundById(0, this.pagination.pageSize, 0);
    },
    addMerchant() {
      this.modelVisible = true;
      this.editable = true;
      this.selectedSportGroundInfo = null;
    },
    searchSportGround() {
      this.getSportGroundById(0, this.pagination.pageSize, 0)
        .then(() => {
          this.pagination.current = 1;
        });
    },
    handleReset() {
      this.form.name = '';
      this.searchSportGround();
    },
    onShowSizeChange(page, pageSize) {
      this.pageChange(1, pageSize);
      this.pagination.current = 1;
      this.pagination.pageSize = pageSize;
    },
    pageChange(page, pageSize) {
      this.getSportGroundById(page - 1, pageSize, 0);
    },
    getSportGroundById(page, pageSize, start) {
      const name = this.form.name;
      const params = {
        page, pageSize, start, name
      };
      return sportGroundService.getSportGroundsById(params).then((res) => {
        this.pagination.current = page + 1;
        this.pagination.total = res.data.totalElements;
        this.sportGroundDataSource.splice(0, this.sportGroundDataSource.length);
        this.sportGroundDataSource.push(...res.data.content);
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
