<template>
<div ref="replayModal">
  <a-modal
    :get-container="()=>this.$refs.replayModal"
    v-model="visibleScoped"
    :title="modelTitle"
    :width="1400"
    :footer="null"
    @cancel="()=>{
           this.$refs.sportGroundForm.clearValidate();
           this.visibleScoped = false;
           }"
  >
    <template>
      <a-form-model
        ref="sportGroundForm"
        :label-col="{span: 2}"
        :wrapper-col="{span: 20}"
        :model="sportGroundForm"
        :rules="rules"
      >
        <a-form-model-item
          :label="$t('sportGround.model.label.name')"
          :wrapper-col="{span: 8}"
          prop="name"
        >
          <a-input
            :disabled="!editable"
            :placeholder="$t('sportGround.model.placeholder.name')"
            v-model="sportGroundForm.name"
          >
            <a-icon slot="prefix" type="bank" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>

        <a-form-model-item
          :label="$t('sportGround.model.label.type')"
          :wrapper-col="{span: 8}"
          prop="type"
        >
          <a-select
            v-model="sportGroundForm.type"
            :disabled="!editable"
            :placeholder="$t('categoryModel.placeholder.carrier')"
          >
            <a-select-option
              v-for="item in typeList"
              :key="item.id"
              :value="item.value"
            >
              {{ item.value }}
            </a-select-option>
          </a-select>
        </a-form-model-item>

        <a-form-model-item
          :wrapper-col="{span: 8}"
          :label="$t('merchant.register.label.phone')"
          prop="phone"
        >
          <a-input
            :disabled="!editable"
            :placeholder="$t('merchant.register.placeHolder.phone')"
            v-model="sportGroundForm.phone"
          >
            <a-icon slot="prefix" type="phone" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item
          :wrapper-col="{span: 8}"
          :label="$t('merchant.register.label.city')"
          prop="city"
        >
          <a-cascader :options="cities"
                      :disabled="!editable"
                      :value="this.sportGroundForm.city"
                      :placeholder="$t('sportGround.model.placeholder.city')"
                      @change="onChange" />

        </a-form-model-item>

        <a-form-model-item
          :wrapper-col="{span: 14}"
          :label="$t('merchant.register.label.detailedAddress')"
          prop="detailedAddress"
        >
          <a-textarea
            v-model="sportGroundForm.detailedAddress"
            :disabled="!editable"
            :auto-size="{ minRows: 3, maxRows: 5 }"
            :placeholder="$t('sportGround.model.placeholder.detailedAddress')"
          >

          </a-textarea>
        </a-form-model-item>
        <a-form-model-item
          :label="$t('sportGround.model.label.weeks')"
          prop="weeks"
        >
          <a-checkbox-group
            :disabled="!editable"
            v-model="sportGroundForm.weeks"
            :options="weekList"/>
        </a-form-model-item>
        <a-form-model-item
          :wrapper-col="{span: 4}"
          :label="$t('sportGround.model.label.price')"
          prop="price"
        >
          <a-input-number size="large"
                          :disabled="!editable"
                          v-model="sportGroundForm.price"
                          :min="0"
                          :max="100000"
                          :default-value="0"
                          @change="changePrice"
          />
          {{this.$t('sportGround.model.label.priceValue')}}
        </a-form-model-item>
        <a-form-model-item
          :label="$t('sportGround.model.label.time')"
          prop="time"
        >
          <a-select
            mode="multiple"
            :placeholder="$t('sportGround.model.placeholder.time')"
            :value="sportGroundForm.time"
            style="width: 60%"
            @change="handleChange"
          >
            <a-select-option v-for="item in timeList"
                             :key="item.id"
                             :disabled="!editable"
                             :value="item.key">
              {{ item.value }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item
          :label="$t('sportGround.model.label.area')"
        >
          <a-table
            :pagination="false"
            :data-source="area"
            :row-key="record => record.sequence"
            :columns="columns"
            bordered
          >
            <template
              v-for="(col, index) in columns"
              :slot="col.dataIndex"
              slot-scope="text, record"
            >
              <editable-cell :text.sync="record[col.dataIndex]"
                             :key="index"
                             v-if="col.dataIndex !== 'operation' && editable"
                             @change="onCellChange(col.dataIndex, record.sequence, $event)" />
              <template v-else>
                {{text}}
              </template>
            </template>

            <template slot="operation" slot-scope="text, record">
              <a-popconfirm
                :disabled="!editable"
                title="Sure to delete?"
                @confirm="() => onDelete(record.sequence)"
              >
                <a href="javascript:;">
                  {{$t('common.button.delete')}}
                </a>
              </a-popconfirm>
            </template>
          </a-table>
          <a
            v-if="editable"
            style="float: right"
            @click="addAreaName"
          >
            <a-icon
              class="add-icon"
              type="title"
            />
            <span
              style="color: #0181C0"
            >{{ this.$t('sportGround.model.label.add') }}</span>
          </a>
        </a-form-model-item>
        <a-form-model-item
          :wrapper-col="{span: 14}"
          :label="$t('sportGround.model.label.picture')"
          prop=""
        >
          <a-upload
            list-type="picture-card"
            :file-list="fileList"
            :disabled="!editable"
            @preview="handlePreview"
            :before-upload="beforeUpload"
            @change="handleChangePicture"
          >
            <div v-if="fileList.length < 4">
              <a-icon type="plus" />
              <div class="ant-upload-text">
                Upload
              </div>
            </div>
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
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
                        v-if="this.selectedSportGroundInfo === null">
                {{ $t('merchant.register.label.submit') }}
              </a-button>
              <a-button type="primary" @click="updateForm()"
                        v-if="this.selectedSportGroundInfo !== null">
                {{ $t('merchant.register.label.update') }}
              </a-button>
              <a-button style="margin-left: 10px"
                        @click="resetForm">
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
import city from '@/views/feture/merchant/city.values';
import rules from '@/views/maintenance/sportGround/sportGroundModel.rules';
import lodash from 'lodash';
import dictionaryService from '@/service/dictionary';
import moment from 'moment';
import sportGroundService from '@/service/sportGround';
import sportGroundPictureService from '@/service/sportGroundPicture';
import EditableCell from './EditableCell.vue';

export default {
  name: 'SportGroundModel',
  mixins: [city, rules],
  components: {
    EditableCell
  },
  props: {
    editable: {
      type: Boolean,
      default: false
    },
    selectedSportGroundInfo: {
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
      previewVisible: false,
      previewImage: '',
      fileList: [],
      moment,
      weekList: [],
      timeList: [],
      area: [],
      sportGroundForm: {
        name: null,
        type: null,
        phone: null,
        price: 0,
        city: [],
        detailedAddress: null,
        weeks: [],
        time: []
      },
      typeList: [],
      //
      cities: city,
      id: null,
      merchantNameList: []
      //
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
    columns() {
      const col = [];
      col.push(
        {
          title: this.$t('sportGround.model.label.areaName'),
          dataIndex: 'areaName',
          width: '5%',
          scopedSlots: { customRender: 'areaName' }
        }
      );
      if (!lodash.isEmpty(this.sportGroundForm.time)) {
        this.sportGroundForm.time.forEach((item) => {
          const temp = {
            title: this.$t(`sportGround.model.table.${item}`),
            dataIndex: item,
            width: '2%',
            scopedSlots: { customRender: item }
          };
          col.push(temp);
        });
      }
      col.push({
        title: this.$t('sportGround.model.label.operation'),
        dataIndex: 'operation',
        width: '2%',
        scopedSlots: { customRender: 'operation' }
      });
      return col;
    },
    modelTitle() {
      if (this.selectedSportGroundInfo === null) {
        return this.$t('merchant.register.label.add');
      }
      if (this.editable === true) {
        return this.$t('merchant.register.label.edit');
      }
      return this.$t('merchant.register.label.view');
    }
  },
  watch: {
    selectedSportGroundInfo(newInfo) {
      if (!lodash.isEmpty(newInfo)) {
        this.id = newInfo.id;
        this.sportGroundForm.name = newInfo.name;
        this.sportGroundForm.type = newInfo.type;
        this.sportGroundForm.phone = newInfo.phone;
        this.sportGroundForm.price = newInfo.price;
        this.sportGroundForm.detailedAddress = newInfo.detailedAddress;
        this.sportGroundForm.city = newInfo.city.split('/');
        this.sportGroundForm.weeks = newInfo.weeks.split(';');
        this.sportGroundForm.time = newInfo.timeArea.split(';');
        this.area = newInfo.areas;
        const base64 = 'data:image/png;base64,';
        this.fileList = newInfo.pictures.map(
          (f) => ({
            uid: f.id,
            id: f.id,
            name: f.fileName,
            status: 'done',
            originFileObj: this.base64ToFile(base64 + f.fileContent, f.fileName),
            thumbUrl: base64 + f.fileContent
          })
        );
      } else {
        this.id = null;
        this.resetForm();
        this.$refs.sportGroundForm.clearValidate();
      }
    }
  },
  mounted() {
    this.getDictionary();
  },
  methods: {
    beforeUpload(file) {
      const allowType = 'gif;jpg;jpeg;tif;bmp;png';
      const isAllowType = allowType.includes(file.name.substr(file.name.lastIndexOf('.') + 1, file.name.length));
      if (!isAllowType) {
        this.$message.warning(this.$t('格式不正确'));
      }
      const isLt5M = file.size / 1024 / 1024 < 5;
      if (!isLt5M) {
        this.$message.warning(this.$t('图片大小应该小于5M'));
      }
      if (isAllowType && isLt5M) {
        // eslint-disable-next-line no-param-reassign
        file.status = 'done';
      } else {
        // eslint-disable-next-line no-param-reassign
        file.status = 'error';
      }
      return false;
    },
    base64ToFile(urlData, fileName) {
      const arr = urlData.split(',');
      const mime = arr[0].match(/:(.*?);/)[1];
      const bytes = atob(arr[1]); // 解码base64
      let n = bytes.length;
      const ia = new Uint8Array(n);
      while (n--) {
        ia[n] = bytes.charCodeAt(n);
      }
      return new File([ia], fileName, { type: mime });
    },
    getBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
      });
    },
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      if (!file.url && !file.preview) {
        // eslint-disable-next-line no-param-reassign
        file.preview = await this.getBase64(file.originFileObj);
      }
      this.previewImage = file.thumbUrl || file.preview;
      this.previewVisible = true;
    },
    handleChangePicture({ fileList }) {
      this.fileList = fileList;
      console.log(this.fileList);
    },
    uploadFile() {
      console.log(this.fileList);
      const files = this.fileList.filter((o) => o.status !== 'error')
        .map((n) => n.originFileObj);
      const formData = new FormData();
      if (files.length > 0) {
        files.forEach((n) => {
          formData.append('files', n);
        });
      } else {
        formData.append('files', files[0]);
      }
      console.log(formData);
      return sportGroundPictureService.uploadSportGroundPicture(formData);
    },
    resetForm() {
      this.sportGroundForm.name = null;
      this.sportGroundForm.type = null;
      this.sportGroundForm.phone = null;
      this.sportGroundForm.price = 0;
      this.sportGroundForm.detailedAddress = null;
      this.sportGroundForm.city = [];
      this.sportGroundForm.weeks = lodash.cloneDeep(this.weekList);
      this.sportGroundForm.time = [];
      this.area = [];
      this.fileList = [];
    },
    handleChange(selectedItems) {
      this.sportGroundForm.time = selectedItems;
    },
    changePrice(value) {
      this.sportGroundForm.price = value;
      console.log(this.sportGroundForm.price);
    },
    onCellChange(col, key, value) {
      const target = this.area.find((item) => item.sequence === key);
      if (target) {
        target[col] = value;
      }
      console.log(this.area);
    },
    onDelete(key) {
      this.area = lodash.cloneDeep(this.area.filter((n) => n.sequence !== key));
      let sequence = 1;
      this.area.forEach((n) => {
        // eslint-disable-next-line no-param-reassign
        n.sequence = sequence;
        sequence++;
      });
    },
    addAreaName() {
      let target = {
        sequence: this.area.length + 1
      };
      this.columns.forEach((n) => {
        const attribute = n.dataIndex;
        let temp = null;
        if (attribute === 'areaName') {
          temp = {
            [attribute]: `默认场号${ this.area.length}`
          };
        }
        if (attribute !== 'areaName' && attribute !== 'operation') {
          temp = {
            [attribute]: this.sportGroundForm.price
          };
        }
        target = Object.assign(target, temp);
      });
      this.area.push(target);
    },
    getDictionary() {
      dictionaryService.getMultipleDictionaries(['运动场类型', '星期', '时间段']).then((n) => {
        const type = n.data.find((target) => target.name === '运动场类型');
        if (!lodash.isEmpty(type)) {
          this.typeList = lodash.cloneDeep(type.dictionaryOptions);
        }
        const time = n.data.find((target) => target.name === '时间段');
        if (!lodash.isEmpty(time)) {
          this.timeList = lodash.cloneDeep(time.dictionaryOptions);
        }
        const week = n.data.find((target) => target.name === '星期');
        if (!lodash.isEmpty(week)) {
          this.weekList = [];
          week.dictionaryOptions.forEach((target) => {
            this.weekList.push(target.value);
            this.sportGroundForm.weeks.push(target.value);
          });
        }
      });
    },
    updateForm() {
      const that = this;
      this.$refs.sportGroundForm.validate().then(() => {
        this.uploadFile().then((res) => {
          if (res.success) {
            const sportGround = {
              name: that.sportGroundForm.name,
              type: that.sportGroundForm.type,
              phone: that.sportGroundForm.phone,
              city: that.sportGroundForm.city.join('/'),
              detailedAddress: that.sportGroundForm.detailedAddress,
              weeks: that.sportGroundForm.weeks.join(';'),
              price: that.sportGroundForm.price,
              timeArea: that.sportGroundForm.time.join(';'),
              areas: that.area,
              pictures: res.data.filter((n) => n.id !== undefined).map((f) => ({ id: f.id }))
            };
            sportGroundService.updateSportGround(that.id, sportGround).then((n) => {
              if (n.success) {
                that.visibleScoped = false;
                that.$emit('onClose');
                that.$message.success(this.$t('sportGround.model.validate.updateSuccess'));
                return;
              }
              that.$message.error(this.$t('sportGround.model.validate.updateError'));
            });
          }
        });
      });
    },
    submitForm() {
      const that = this;
      this.$refs.sportGroundForm.validate().then(() => {
        this.uploadFile().then((res) => {
          if (res.success) {
            const sportGround = {
              name: that.sportGroundForm.name,
              type: that.sportGroundForm.type,
              phone: that.sportGroundForm.phone,
              city: that.sportGroundForm.city.join('/'),
              detailedAddress: that.sportGroundForm.detailedAddress,
              weeks: that.sportGroundForm.weeks.join(';'),
              price: that.sportGroundForm.price,
              timeArea: that.sportGroundForm.time.join(';'),
              areas: that.area,
              pictures: res.data.filter((n) => n.id !== undefined).map((f) => ({ id: f.id }))
            };
            sportGroundService.createSportGround(sportGround).then((n) => {
              if (n.success) {
                that.visibleScoped = false;
                that.$emit('onClose');
                that.$message.success(this.$t('sportGround.model.validate.submitSuccess'));
                return;
              }
              that.$message.error(this.$t('sportGround.model.validate.submitError'));
            });
          }
        });
      });
    },
    onChange(value) {
      this.sportGroundForm.city = [];
      value.forEach((item) => {
        // eslint-disable-next-line operator-assignment
        this.sportGroundForm.city.push(item);
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
  .upload-list-inline >>> .ant-upload-list-item {
    float: left;
    width: 200px;
    margin-right: 8px;
  }
  .upload-list-inline >>> .ant-upload-animate-enter {
    animation-name: uploadAnimateInlineIn;
  }
  .upload-list-inline >>> .ant-upload-animate-leave {
    animation-name: uploadAnimateInlineOut;
  }
</style>
