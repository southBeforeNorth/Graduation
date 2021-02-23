<template>
  <div>
  <div style="text-align: center">
    <a-input-search
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
          <a-icon type="message" style="margin-right: 8px" />
          {{ item.comment }}
        </span>
      </template>
      <img
        slot="extra"
        width="272"
        alt="logo"
        :src="item.extra"
      />
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
        pageSize: 5,
        showSizeChanger: true,
        onShowSizeChange: this.onShowSizeChange,
        onChange: this.pageChange,
        pageSizeOptions: ['5', '10', '20', '40']
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
      const base64 = 'data:image/png;base64,';
      this.listData = [];
      data.forEach((n) => {
        this.listData.push({
          href: 'reservation',
          id: n.id,
          title: n.name,
          extra: base64 + n.pictures[0].fileContent,
          address: n.city.split('/').join('') + n.detailedAddress,
          content: n.price,
          comment: '10'
        });
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
