<template>
  <div class="root">
    <div class="header-wrapper">
    <span class="System">{{ $t('dictionary.name') }}</span>
    <a-button
      name="addButton"
      class="editable-add-btn"
      @click="addDictionaryModal"
    >
      <a-icon type="plus" />{{ $t('dictionary.addButton') }}
    </a-button>
    </div>
    <a-divider dashed />
    <div class="table-wrapper">
    <a-table
      :pagination="false"
      :data-source="dataSource"
      :columns="columns"
      :row-key="record => record.id"
      :expanded-row-keys="expandedRowKeys"
      size="middle"
      @expand="expand"
    >
      <template
        v-for="col in dictionaryColumns"
        :slot="col"
        slot-scope="text, record"
      >
        <div :key="col">
          <a-input
            v-if="record.editable && record.addition && col === 'name'"
            style="margin: -5px 0"
            :value="text"
            @change="e => handleChange(e.target.value, record.id, col)"
          />
          <a-input
            v-else-if="record.editable && col ==='description'"
            style="margin: -5px 0"
            :value="text"
            @change="e => handleChange(e.target.value, record.id, col)"
          />
          <template v-else-if="col === 'lastModifiedBy' || col === 'createdBy'">
            {{ text }}
          </template>
          <template
            v-else-if="col === 'lastModifiedTime'"
          >
            {{ text | dateTime }}
          </template>
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>

      <template
        slot="action"
        slot-scope="text, record, index"
      >
        <div class="editable-row-operations">
          <span v-if="record.editable && !record.addition">
            <a @click="() => update(record.id)">{{ $t('common.button.save') }}</a>
            <a @click="() => cancel(record.id)">{{ $t('common.button.cancel') }}</a>
          </span>
          <span v-else-if="record.editable && record.addition">
            <a @click="() => add(record)">{{ $t('common.button.add') }}</a>
            <a @click="() => cancelAddDictionary(record.name)">{{ $t('common.button.cancel') }}</a>
          </span>
          <span v-else>
            <a @click="() => edit(record.id)">{{ $t('common.button.edit') }}</a>
            <a @click="deleteByCode(record.id)">{{ $t('common.button.delete') }}</a>
            <a @click="addOptionModel(record, index)">{{ $t('common.button.add') }}</a>
          </span>
        </div>
      </template>

      <!-- Inner Columns-->
      <a-table
        slot="expandedRowRender"
        :columns="innerColumns"
        :data-source="innerDataSource"
        :pagination="false"
        :row-key="record => record.id"
        size="small"
        bordered
      >
        <template
          v-for="col in dictionaryOptionColumns"
          :slot="col"
          slot-scope="text, record"
        >
          <div :key="col">
            <a-input
              v-if="record.optionEditable && (col ==='key' || col ==='value'
                || col ==='optionDescription')"
              style="margin: -5px 0"
              :value="text"
              @change="e => handleOptionChange(e.target.value, record.id, col)"
            />
            <a-input-number
              v-else-if="record.optionEditable && col ==='sequence'"
              :min="0"
              :max="99999"
              style="margin: -5px 0"
              :value="text"
              @change="e => handleOptionChange(e, record.id, col)"
            />
            <a-switch
              v-else-if=" col === 'default'"
              :disabled="!record.optionEditable"
              :checked="text"
              @change="optionDefaultValueChange"
            />
            <template
              v-else-if="!record.optionEditable && col ==='metaValue'"
            >
              <div class="mate-value-style">
                {{ text }}
              </div>
            </template>
            <template
              v-else
            >
              {{ text }}
            </template>
          </div>
        </template>
        <template
          slot="action"
          slot-scope="text, record"
        >
          <div class="editable-row-operations">
            <span v-if="record.optionEditable && !record.optionAddition">
              <a @click="() => updateOption(record)">{{ $t('common.button.save') }}</a>
              <a @click="() => cancelOption(record.id)">{{ $t('common.button.cancel') }}</a>
            </span>
            <span v-else-if="record.optionEditable && record.optionAddition">
              <a @click="() => addOption(record)">{{ $t('common.button.add') }}</a>
              <a @click="() => cancelAddDictionaryOption(record.id)">
                {{ $t('common.button.cancel') }}</a>
            </span>
            <span v-else>
              <a @click="() => edit(record.id, record.key)">{{ $t('common.button.edit') }}</a>
              <a @click="deleteOptionById(record.id)">{{ $t('common.button.delete') }}</a>
            </span>
          </div>
        </template>
      </a-table>
    </a-table>
    </div>
  </div>
</template>

<script>

import dictionaryService from '@/service/dictionary';
import dateFormat from '@/common/constant/dateFormat';
import lodash from 'lodash';
import dictionaryConfig from './dictionary.config';

export default {
  name: 'Dictionary',
  mixins: [dictionaryConfig],
  data() {
    return {
      dateFormat,
      name: '',
      dataSource: [],
      innerDataSource: [],
      id: '',
      form: this.$form.createForm(this, { name: 'dictionaryForm' }),
      addCount: 0,
      addOptionCount: 0,
      expandedRowKeys: [],
      addOptionIndex: 0
    };
  },
  mounted() {
    this.findAllDictionary();
  },
  methods: {
    findDictionary(record) {
      let result = null;
      const cache = lodash.cloneDeep(this.dataSource);
      cache.forEach((dictionary) => {
        const optionDictionary = lodash.cloneDeep(dictionary.dictionaryOptions);
        optionDictionary.forEach((option) => {
          if (option.id === record.id) {
            result = dictionary;
          }
        });
      });
      return result;
    },
    updateOption(record) {
      if (this.validateEmpty(record)) {
        const target = this.findAddTarget(record);
        if (this.validateOptionKeyUnique(target)) {
          const dictionary = this.findDictionary(record);
          dictionary.dictionaryOptions = dictionary.dictionaryOptions
            .filter((n) => n.id !== record.id);
          dictionary.dictionaryOptions.push(record);
          dictionaryService
            .updateDictionary(dictionary.id, dictionary)
            .then((n) => {
              if (n.success) {
                this.$message.success(this.$t('dictionary.updateSuccess'));
                this.refreshOption(n.data);
              }
            });
        }
      }
    },
    refreshOption(n) {
      this.findAllDictionary().then(() => {
        const target = this.dataSource.filter((item) => item.id === n.id)[0];
        this.expand(true, target);
        target.dictionaryOptions.forEach((item) => {
          // eslint-disable-next-line no-param-reassign
          item.optionEditable = false;
        });
      });
    },
    addOption(record) {
      if (this.validateEmpty(record)) {
        const target = this.findAddTarget(record);
        if (this.validateOptionKeyUnique(target)) {
          const index = this.addOptionIndex;
          const data = this.dataSource[index];
          data.dictionaryOptions = lodash.cloneDeep(this.innerDataSource);
          dictionaryService
            .updateDictionary(data.id, data)
            .then((n) => {
              if (n.success) {
                this.$message.success(this.$t('dictionary.createSuccess'));
                this.refreshOption(n.data);
              }
            });
        }
      }
    },
    findAddTarget(record) {
      return this.innerDataSource.filter((item) => record.key.trim() === item.key);
    },
    validateEmpty(record) {
      if (lodash.isEmpty(record.key)) {
        this.$message.warning(this.$t('dictionaryOptions.optionEmptyText'));
        return false;
      }
      return true;
    },
    validateOptionKeyUnique(target) {
      if (target.length >= 2) {
        this.$message.warning(this.$t('dictionaryOptions.warningText'));
        return false;
      }
      return true;
    },
    addOptionModel(record, index) {
      const addCount = this.innerDataSource.find((n) => n.optionAddition === true);
      if (addCount) {
        this.$message.warning(this.$t('dictionaryOptions.addMultipleOptionModel'));
        return false;
      }
      this.addOptionIndex = index;
      this.expandedRowKeys = [];
      this.expandedRowKeys = [record.id];
      this.addOptionCount += 1;
      this.id = this.addOptionCount;
      const newDictionaryOption = {
        id: this.addOptionCount,
        key: null,
        value: null,
        metaValue: null,
        description: null,
        sequence: 0,
        default: false,
        optionEditable: true,
        optionAddition: true
      };
      this.innerDataSource.splice(0, this.innerDataSource.length);
      if (this.innerDataSource.length === 0) {
        Object.values(record.dictionaryOptions).forEach((item) => this.innerDataSource.push(item));
      }
      this.innerDataSource.unshift(newDictionaryOption);
      this.cacheOptionData = this.innerDataSource.map((item) => ({ ...item }));
    },
    optionDefaultValueChange(checked) {
      const newInnerData = [...this.innerDataSource];
      const id = this.id;
      const target = newInnerData.filter((item) => id === item.id)[0];
      const columnName = 'default';
      if (target) {
        target[columnName] = checked;
        this.innerDataSource = newInnerData;
      }
    },
    expand(expanded, record) {
      this.innerDataSource.splice(0, this.innerDataSource.length);
      this.expandedRowKeys = [];
      if (expanded) {
        this.expandedRowKeys = [record.id];
      }
      if (record.dictionaryOptions !== undefined) {
        Object.values(record.dictionaryOptions).forEach((item) => this.innerDataSource.push(item));
      }
      this.cacheOptionData = this.innerDataSource.map((item) => ({ ...item }));
    },
    addDictionaryModal() {
      const addDictionaryCount = this.dataSource.find((n) => n.addition === true);
      if (addDictionaryCount) {
        this.$message.warning(this.$t('dictionary.addMultipleDictionaryModel'));
        return false;
      }
      this.addCount += 1;
      const newDictionary = {
        id: this.addCount,
        name: null,
        description: null,
        classification: null,
        createdBy: null,
        createdTime: null,
        lastModifiedBy: null,
        lastModifiedTime: null,
        editable: true,
        addition: true
      };
      this.dataSource.unshift(newDictionary);
    },
    findAllDictionary() {
      return dictionaryService
        .findAllDictionary()
        .then((n) => {
          if (n.success) {
            this.dataSource = lodash.cloneDeep(n.data);
            this.cacheData = lodash.cloneDeep(n.data);
          }
        });
    },
    handleChange(value, id, column) {
      if (value.length >= 255) {
        this.$message.warning(this.$t('dictionary.validateOverLength'));
        return false;
      }
      const newData = [...this.dataSource];
      const target = newData.filter((item) => id === item.id)[0];
      if (target) {
        target[column] = value;
        this.dataSource = newData;
      }
    },
    handleOptionChange(value, id, column) {
      if (column === 'sequence') {
        if (value.toString().length >= 6) {
          this.$message.warning(this.$t('dictionaryOptions.validateOverLength'));
          return false;
        }
      }
      if (value.length >= 255 && column !== 'metaValue') {
        this.$message.warning(this.$t('dictionary.validateOverLength'));
        return false;
      }
      const newInnerData = [...this.innerDataSource];
      const target = newInnerData.filter((item) => id === item.id)[0];
      if (target) {
        if (column === 'optionDescription') {
          // eslint-disable-next-line no-param-reassign
          column = 'description';
        }
        target[column] = value;
        this.innerDataSource = newInnerData;
      }
    },
    edit(id, key) {
      let target = null;
      if (key !== null && key !== undefined && key !== '') {
        this.key = key;
        const newInnerData = [...this.innerDataSource];
        target = newInnerData.filter((item) => id === item.id)[0];
        if (target) {
          this.id = id;
          target.optionEditable = true;
          delete target.optionAddition;
          this.innerDataSource = newInnerData;
        }
      } else {
        const newData = [...this.dataSource];
        target = newData.filter((item) => id === item.id)[0];
        if (target) {
          this.id = id;
          target.editable = true;
          delete target.addition;
          this.dataSource = newData;
        }
      }
    },
    add(record) {
      if (record.name === '' || record.name === null) {
        this.$message.warning(this.$t('dictionary.dictionaryEmptyText'));
        return false;
      }
      const newData = [...this.dataSource];
      const target = newData.filter((item) => record.name === item.name);
      if (target.length >= 2) {
        this.$message.warning(this.$t('dictionary.warningText'));
      } else {
        const {
          name, description
        } = target[0];
        dictionaryService
          .createDictionary({
            name, description
          })
          .then(() => {
            this.findAllDictionary();
            this.$message.success(this.$t('dictionary.createSuccess'));
          });
      }
    },
    cancelAddDictionary(nameValue) {
      const newData = [...this.dataSource];
      const target = newData.filter((item) => nameValue === item.name)[0];
      const index = this.dataSource.indexOf(target);
      this.dataSource.splice(index, 1);
    },
    cancelAddDictionaryOption(id) {
      const newData = [...this.innerDataSource];
      const target = newData.filter((item) => id === item.id)[0];
      const index = this.innerDataSource.indexOf(target);
      this.innerDataSource.splice(index, 1);
    },
    update(id) {
      const newData = [...this.dataSource];
      const target = newData.filter((item) => id === item.id)[0];
      if (target) {
        const {
          name, description
        } = target;
        dictionaryService
          .updateDictionary(target.id, {
            name, description
          })
          .then(() => {
            this.findAllDictionary();
            this.$message.success(this.$t('dictionary.updateSuccess'));
          });
      }
    },
    cancelOption(id) {
      const that = this;
      this.$confirm({
        title: that.$t('common.confirmTitle'),
        content: that.$t('dictionary.cancelModal'),
        okText: that.$t('common.button.ok'),
        cancelText: that.$t('common.button.cancel'),
        onOk() {
          const target = that.innerDataSource.filter((item) => id === item.id)[0];
          if (target) {
            const newInnerData = [...that.innerDataSource];
            Object.assign(target, that.cacheOptionData.filter((item) => id === item.id)[0]);
            delete target.optionEditable;
            that.innerDataSource = newInnerData;
          }
        }
      });
    },
    cancel(id) {
      const that = this;
      this.$confirm({
        title: that.$t('common.confirmTitle'),
        content: that.$t('dictionary.cancelModal'),
        okText: that.$t('common.button.ok'),
        cancelText: that.$t('common.button.cancel'),
        onOk() {
          const target = that.dataSource.filter((item) => id === item.id)[0];
          if (target) {
            const newData = [...that.dataSource];
            Object.assign(target, that.cacheData.filter((item) => id === item.id)[0]);
            delete target.editable;
            that.dataSource = newData;
          }
        }
      });
    },
    deleteByCode(id) {
      const that = this;
      this.$confirm({
        title: that.$t('common.confirmTitle'),
        content: that.$t('dictionary.deleteModal'),
        okText: that.$t('common.button.delete'),
        cancelText: that.$t('common.button.cancel'),
        onOk() {
          const target = that.dataSource.filter((item) => id === item.id)[0];
          dictionaryService
            .deleteDictionary(target.id)
            .then(() => {
              that.$message.success(that.$t('dictionary.deleteSuccess'));
              that.findAllDictionary();
            });
        },
        onCancel() {
        }
      });
    },
    deleteOptionById(id) {
      const that = this;
      this.$confirm({
        title: that.$t('common.confirmTitle'),
        content: that.$t('dictionaryOptions.deleteModal'),
        okText: that.$t('common.button.delete'),
        cancelText: that.$t('common.button.cancel'),
        onOk() {
          dictionaryService
            .deleteDictionaryOption(id)
            .then((n) => {
              that.findAllDictionary();
              that.$message.success(that.$t('dictionary.deleteSuccess'));
              const target = that.innerDataSource.find((item) => item.id === n.id);
              const index = that.innerDataSource.indexOf(target);
              that.innerDataSource.splice(index, 1);
            });
        }
      });
    }
  }
};
</script>

<style scoped>
  .System{
    font-size: 20px;
    color: #333333;
    font-family: Raleway Bold;
    line-height: 26px;
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
  .header-wrapper {
    background: #D1E3FC;
    padding: 20px;
  }
  .root {
    margin: 0 !important;
    padding: 0 !important;
  }
  .table-wrapper {
    margin: 20px;
    padding: 15px;
    background: white;
  }
  .editable-row-operations a {
    margin-right: 8px;
  }
  .mate-value-style {
    text-overflow: ellipsis;
    overflow: hidden;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 30;
  }
  /deep/ .ant-table-thead > tr > th {
    background: #F0F5FC;
  }
</style>
