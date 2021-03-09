<template>
  <div class="root">
    <div class="header-wrapper">
      <span class="System">{{ $t('notice.label.title') }}</span>
    </div>
    <div class="table-wrapper">
      <a-row>
        <a-col>
        <quill-editor
        style="height:400px;"
        v-model="content"
        :options="editorOption"
      />
        </a-col>
      </a-row>
     <a-row>
       <a-col style="margin-top: 60px;text-align: center">
       <a-button type="primary"
                 @click="submit"
                 class="editable-add-btn">
       {{$t('notice.button.submit')}}
       </a-button>
       </a-col>
     </a-row>
    </div>
  </div>
</template>

<script>
import noticeService from '@/service/notice';
import lodash from 'lodash';

export default {
  name: 'NoticeManage',
  data() {
    return {
      content: '',
      editorOption: {
        placeholder: '编辑文章内容'
      },
      id: null
    };
  },
  mounted() {
    this.getNotice();
  },
  methods: {
    getNotice() {
      noticeService.getNotice().then((res) => {
        if (res.success) {
          this.id = res.data.id;
          this.content = res.data.content;
        }
      });
    },
    submit() {
      if (lodash.isEmpty(this.content)) {
        this.$message.warning(this.$t('notice.warningText.warning'));
        return;
      }
      const params = {
        id: this.id,
        content: this.content
      };
      noticeService.updateNotice(params).then((res) => {
        if (res.success) {
          this.id = res.data.id;
          this.content = res.data.content;
          this.$message.success(this.$t('notice.warningText.success'));
          return;
        }
        this.$message.error(this.$t('notice.warningText.error'));
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
    font-size: 16px;
    color: #FFFFFF;
    letter-spacing: 0;
    top: -5px;
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
