<template>
  <div>
  <div style="text-align: center">
    <a-input-search
      allow-clear
      v-model="name"
      :placeholder="$t('home.body.search')"
      :enter-button="$t('home.body.searchButton')"
      size="large"
      @search="searchSportGround"
    />
  </div>
  <a-divider/>
  <div>
  <a-list
    style="width: 60%; margin: auto;"
    item-layout="vertical"
    size="large"
    :pagination="pagination"
    :data-source="listData">
    <a-list-item
      slot="renderItem"
      key="item.title"
      slot-scope="item">
      <template  slot="actions">
        <span >
          <a-icon @click="toDetailPage(item.href, item.id)"
                  type="message" style="margin-right: 8px" />
          {{ item.comment }}
        </span>
        <a-rate :default-value="item.rate" allow-half :disabled="true"/>
      </template>
      <a slot="extra" @click="toDetailPage(item.href, item.id)">
      <img
        width="272"
        height="150px"
        alt="logo"
        :src="item.extra"
      />
      </a>
      <a-list-item-meta
        :description="$t('homeBodyList.label.address')+item.address">
        <a
          slot="title"
          style="font-weight: bolder; font-size: x-large"
          @click="toDetailPage(item.href, item.id)"
        >
          {{ item.title }}
        </a>
      </a-list-item-meta>
     {{$t('homeBodyList.label.price')+item.content+$t('homeBodyList.label.priceTime')}}
    </a-list-item>
  </a-list>
  </div>
  </div>
</template>

<script>
import sportGroundService from '@/service/sportGround';
import orderInfoService from '@/service/orderInfo';

export default {
  name: 'HomeBodyList',
  data() {
    return {
      name: null,
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
      }
    };
  },
  mounted() {
    this.initData(0, this.pagination.pageSize, 0);
  },
  methods: {
    toDetailPage(routName, detailId) {
      this.$store.commit('SET_SPORT_GROUND_ID', detailId);
      const route = this.$router.resolve({ name: routName });
      this.$store.commit('SET_TAB_ACTIVE_KEY', 'key_0');
      window.open(route.href, '_blank');
    },
    pageChange(page, pageSize) {
      this.initData(page - 1, pageSize, 0);
    },
    onShowSizeChange(page, pageSize) {
      this.pageChange(1, pageSize);
      this.pagination.current = 1;
      this.pagination.pageSize = pageSize;
    },
    searchSportGround() {
      this.initData(0, this.pagination.pageSize, 0)
        .then(() => {
          this.pagination.current = 1;
        });
    },
    initData(page, pageSize, start) {
      const name = this.name;
      const params = {
        page, pageSize, start, name, city: name
      };
      sportGroundService.getSportGroundList(params).then((res) => {
        if (res.success) {
          this.setData(res.data.content);
          this.pagination.current = page + 1;
          this.pagination.total = res.data.totalElements;
        }
      });
    },
    setData(data) {
      const that = this;
      const base64 = 'data:image/png;base64,';
      this.listData = [];
      data.forEach((n) => {
        let count = 0;
        this.getCommentCount(n).then((res) => {
          count = res.count;
          that.listData.push({
            href: 'reservation',
            id: n.id,
            title: n.name,
            extra: base64 + n.pictures[0].fileContent,
            address: n.city.split('/').join('') + n.detailedAddress,
            content: n.price,
            comment: count,
            rate: res.rate
          });
        });
      });
    },
    getCommentCount(target) {
      let count = 0;
      let rate = 0;
      return orderInfoService.getCommentListById(target.id)
        .then((res) => {
          if (res.success) {
            count = res.data.length;
            res.data.forEach((n) => {
              rate += n.comment.rate;
            });
            rate /= count;
          }
          return { count, rate: Math.round(rate) };
        });
    }
  }
};
</script>

<style scoped>

  /deep/ .ant-list-item-meta-description{
    color: #333333;
  }
</style>
