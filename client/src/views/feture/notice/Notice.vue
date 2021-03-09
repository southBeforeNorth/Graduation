<template>
  <div>
    <TopPicture/>
    <div>
      <a-divider style="font-size: 38px; font-family: 华光行楷_CNKI; color: slategray">
        {{ $t('notice.title') }}
      </a-divider>
    </div>
    <div style="text-align: center">
        <div class="ql-editor" style="width: 60%; margin: auto;" v-html="content"></div>
    </div>
  </div>
</template>

<script>
import TopPicture from '@/components/topPicture/TopPicture.vue';
import noticeService from '@/service/notice';

export default {
  name: 'Register',
  components: {
    TopPicture
  },
  data() {
    return {
      content: null
    };
  },
  mounted() {
    this.getNotice();
  },
  methods: {
    getNotice() {
      noticeService.getNotice().then((res) => {
        if (res.success) {
          this.content = res.data.content;
        }
      });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-input {
    height: 40px;
  }
  /deep/ .verify-btn {
    height: 40px;
  }

</style>
