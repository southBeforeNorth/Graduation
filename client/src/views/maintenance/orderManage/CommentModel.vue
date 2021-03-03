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
        <a-form-model
          ref="commentModel"
          :label-col="{span: 6}"
          :wrapper-col="{span: 16}"
          :model="form"
          :rules="rules"
        >
          <a-form-model-item
            :label="$t('order.label.comment')"
            prop="comment"
          >
            <a-textarea
              :disabled="!editable"
              :placeholder="$t('order.warningText.comment')"
              :auto-size="{ minRows: 3, maxRows: 5 }"
              v-model="form.comment"
            >
              <a-icon slot="prefix" type="bank" style="color:rgba(0,0,0,.25)" />
            </a-textarea>
          </a-form-model-item>
        <a-form-model-item
          :label="$t('order.label.rate')"
          prop="rate"
        >
          <a-rate :disabled="!editable" v-model="form.rate" />
        </a-form-model-item>
         <a-form-model-item
           label=" "
           :colon="false"
         >
           <a-upload
             :disabled="!editable"
             style="margin: auto"
             list-type="picture-card"
             :file-list="fileList"
             @preview="handlePreview"
             :before-upload="beforeUpload"
             @change="handleChangePicture"
           >
             <div v-if="fileList.length < 4">
               <a-icon type="plus" />
               <div class="ant-upload-text">
                 {{$t('order.warningText.upload')}}
               </div>
             </div>
           </a-upload>
           <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
             <img alt="example" style="width: 100%" :src="previewImage" />
           </a-modal>
         </a-form-model-item>
          <a-row
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
                          v-if="this.commentInfo === null">
                  {{ $t('order.warningText.createComment') }}
                </a-button>
                <a-button type="primary" @click="deleteForm()"
                          v-if="this.commentInfo !== null"
                >
                  {{ $t('order.warningText.deleteComment') }}
                </a-button>
                <a-button style="margin-left: 10px"
                          v-if="this.commentInfo === null"
                          @click="()=>{
                        this.$refs.commentModel.resetFields();
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
import pictureService from '@/service/picture';
import orderInfo from '@/service/orderInfo';
import lodash from 'lodash';
import rules from './commentModel.rules';

export default {
  name: 'CommentModel',
  mixins: [rules],
  props: {
    editable: {
      type: Boolean,
      default: false
    },
    orderId: {
      type: String,
      default: null
    },
    commentInfo: {
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
      id: null,
      previewVisible: false,
      previewImage: '',
      fileList: [],
      form: {
        comment: null,
        rate: null
      }
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
      if (this.editable === true) {
        return this.$t('order.warningText.createComment');
      }
      return this.$t('merchant.register.label.view');
    }
  },
  watch: {
    commentInfo(newInfo) {
      if (!lodash.isEmpty(newInfo)) {
        this.id = newInfo.id;
        this.form.comment = newInfo.comment;
        this.form.rate = newInfo.rate;
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
        this.form.comment = null;
        this.form.rate = null;
        this.fileList = [];
        this.$refs.commentModel.clearValidate();
      }
    }
  },
  methods: {
    deleteForm() {
      const params = {
        orderId: this.orderId,
        commentId: this.id
      };
      orderInfo.deleteCommentById(params).then((result) => {
        if (result.success) {
          this.visibleScoped = false;
          this.id = null;
          this.form.comment = null;
          this.form.rate = null;
          this.fileList = [];
          this.$emit('onClose');
          this.$message.success(this.$t('order.warningText.deleteSuccess'));
          return;
        }
        this.$message.error(this.$t('order.warningText.deleteError'));
      });
    },
    uploadFile() {
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
      if (files.length === 0) {
        this.$message.warning(this.$t('order.validate.picture'));
        return;
      }
      return pictureService.uploadSportGroundPicture(formData);
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
    handleChangePicture({ fileList }) {
      this.fileList = fileList;
    },
    submitForm() {
      const that = this;
      this.$refs.commentModel.validate().then(() => {
        this.uploadFile(that.orderId).then((res) => {
          if (res.success) {
            const params = {
              rate: this.form.rate,
              comment: this.form.comment,
              pictures: res.data.filter((n) => n.id !== undefined).map((f) => ({ id: f.id }))
            };
            orderInfo.createComment(this.orderId, params).then((result) => {
              if (result.success) {
                that.visibleScoped = false;
                that.$emit('onClose');
                that.$message.success(this.$t('order.warningText.commentSuccess'));
                return;
              }
              that.$message.error(this.$t('order.warningText.commentError'));
            });
          }
        });
      });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-modal-header {
    background: #D1E3FC;
  }
</style>
