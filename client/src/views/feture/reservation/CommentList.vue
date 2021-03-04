<template>
    <div>
      <a-row>
        <a-col span="15" push="2">
          <a-descriptions
            style="margin: auto"
            :column="1"
            :title="$t('sportGroundDetail.label.comment')">
          </a-descriptions>
        </a-col>
      </a-row>
      <a-row>
        <a-col span="15" push="2">
        <a-list
            item-layout="vertical"
            size="large"
            :pagination="pagination"
            :data-source="listData"
        >
         <a-list-item
           slot="renderItem"
           key="item.title"
           slot-scope="item"
         >
          <a-comment>
            <a slot="author">{{item.name}}</a>
            <a-avatar
              slot="avatar"
              :src="item.header"
              :alt="item.name"
            />
            <p slot="content">
              {{item.content}}
            </p>
            <a-tooltip slot="datetime" :title="item.time.format('YYYY-MM-DD HH:mm:ss')">
              <span>{{item.time.fromNow()}}</span>
            </a-tooltip>
          </a-comment>
           <a-upload
             list-type="picture-card"
             :file-list="item.pictures"
             :disabled="true"
             @preview="handlePreview"
             @change="handleChangePicture"
           >
           </a-upload>
           <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
             <img alt="example" style="width: 100%" :src="previewImage" />
           </a-modal>
         </a-list-item>
          </a-list>
        </a-col>
      </a-row>
    </div>
</template>

<script>
import moment from 'moment';
import orderInfoService from '@/service/orderInfo';
import userService from '@/service/user';

export default {
  name: 'CommentList',
  data() {
    return {
      listData: [],
      pagination: {
        size: 'normal',
        current: 1,
        total: 1,
        pageSize: 7,
        showSizeChanger: true,
        onShowSizeChange: this.onShowSizeChange,
        onChange: this.pageChange,
        pageSizeOptions: ['7', '15', '20', '40']
      },
      previewVisible: false,
      moment
    };
  },
  mounted() {
    this.initData(0, this.pagination.pageSize, 0);
  },
  methods: {
    pageChange(page, pageSize) {
      this.initData(page - 1, pageSize, 0);
    },
    onShowSizeChange(page, pageSize) {
      this.pageChange(1, pageSize);
      this.pagination.current = 1;
      this.pagination.pageSize = pageSize;
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
    },
    initData(page, pageSize, start) {
      const params = {
        page, pageSize, start
      };
      const id = this.$store.state.globalArea.sportGroundId;
      orderInfoService.getCommentByPageById(id, params).then((res) => {
        if (res.success) {
          this.listData = [];
          res.data.content.forEach((target) => {
            this.getUserInfoById(target.userId).then((user) => {
              const base64 = 'data:image/png;base64,';
              const temp = {
                name: user.name,
                time: moment(target.comment.createdTime),
                header: base64 + user.header.fileContent,
                content: target.comment.comment,
                pictures: target.comment.pictures.map(
                  (f) => ({
                    uid: f.id,
                    id: f.id,
                    name: f.fileName,
                    status: 'done',
                    originFileObj: this.base64ToFile(base64 + f.fileContent, f.fileName),
                    thumbUrl: base64 + f.fileContent
                  })
                )
              };
              this.listData.push(temp);
            });
          });
        }
      });
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
    getUserInfoById(id) {
      return userService.getUserByStringId(id).then((res) => {
        if (res.success) {
          return res.data;
        }
      });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-descriptions-title{
    font-size: 24px;
    color: #1890f0;
  }
</style>
