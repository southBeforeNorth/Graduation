<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System">{{ $t('uploadHeader.label.name') }}</span>
    </div>
    <div class="table-wrapper">
      <a-row>
        <a-col push="10" style="margin-top: 40px">
      <a-upload
        style="margin: auto"
        list-type="picture-card"
        :file-list="fileList"
        @preview="handlePreview"
        :before-upload="beforeUpload"
        @change="handleChangePicture"
      >
        <div v-if="fileList.length < 1">
          <a-icon type="plus" />
          <div class="ant-upload-text">
            {{$t('uploadHeader.button.upload')}}
          </div>
        </div>
      </a-upload>
      <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
        <img alt="example" style="width: 100%" :src="previewImage" />
      </a-modal>
        </a-col>
      </a-row>
      <a-row>
        <a-col push="10" style="margin-top: 50px">
          <a-button type="primary" style="margin-left: 7px" @click="submit">
            {{$t('uploadHeader.button.submit')}}
          </a-button>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import pictureService from '@/service/picture';
import merchantService from '@/service/merchant';
import userService from '@/service/user';

export default {
  name: 'Header',
  data() {
    return {
      previewVisible: false,
      previewImage: '',
      fileList: []
    };
  },
  mounted() {
    this.initData();
  },
  computed: {
    type() {
      return this.$store.state.user.type;
    }
  },
  methods: {
    initData() {
      if (this.type === 'merchant') {
        merchantService.getMerchantById().then((res) => {
          if (res.success) {
            this.initFileList([res.data.header]);
          }
        });
      } else {
        userService.getUserById().then((res) => {
          if (res.success) {
            this.initFileList([res.data.header]);
          }
        });
      }
    },
    initFileList(pictures) {
      this.fileList = [];
      const base64 = 'data:image/png;base64,';
      this.fileList = pictures.map(
        (f) => ({
          uid: f.id,
          id: f.id,
          name: f.fileName,
          status: 'done',
          originFileObj: this.base64ToFile(base64 + f.fileContent, f.fileName),
          thumbUrl: base64 + f.fileContent
        })
      );
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
    submit() {
      this.uploadFile().then((res) => {
        if (res.success) {
          console.log(res);
          if (this.type === 'merchant') {
            merchantService.uploadMerchantHeader(res.data[0].id).then((n) => {
              if (n.success) {
                this.initFileList([n.data.header]);
                const base64 = 'data:image/png;base64,';
                this.$store.commit('SET_HEADER', base64 + n.data.header.fileContent);
                this.$message.success(this.$t('uploadHeader.label.uploadSuccess'));
              }
            });
          }

          if (this.type === 'user') {
            userService.uploadUserHeader(res.data[0].id).then((n) => {
              if (n.success) {
                this.initFileList([n.data.header]);
                const base64 = 'data:image/png;base64,';
                this.$store.commit('SET_HEADER', base64 + n.data.header.fileContent);
                this.$message.success(this.$t('uploadHeader.label.uploadSuccess'));
              }
            });
          }
          return;
        }
        this.$message.error(this.$t('uploadHeader.label.uploadError'));
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
  .form-with-table-wrapper {
    background: rgb(240, 245, 252);
    margin: 20px;
  }
  .table-wrapper {
    margin: 20px;
    padding: 15px;
    background: white;
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
