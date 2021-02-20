<template>
  <div
    class="menu-wrapper"
  >
    <a-menu
      class="menu"
      mode="inline"
      :open-keys="openKeys"
      @openChange="onOpenChange"
    >
      <a-sub-menu key="sub1">
        <span slot="title"><a-icon type="mail" /><span>Navigation One</span></span>
        <a-menu-item key="1">
          Option 1
        </a-menu-item>
        <a-menu-item key="2">
          Option 2
        </a-menu-item>
        <a-menu-item key="3">
          Option 3
        </a-menu-item>
        <a-menu-item key="4">
          Option 4
        </a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="sub2">
        <span slot="title"><a-icon type="appstore" />
          <span>{{this.$t('menu.label.sportGroundManage')}}</span>
        </span>
        <template v-for="item in manageSportGroundOption">
          <a-menu-item :key="item.key">
            <router-link
              :to="item.value"
              tag="a"
            >
              {{ $t('menu.label.' + item.key) }}
            </router-link>
          </a-menu-item>
        </template>
      </a-sub-menu>
      <a-sub-menu key="sub5">
        <span slot="title">
          <a-icon type="user" />
          <span>{{this.$t('menu.label.userManage')}}</span>
        </span>
        <template v-for="item in manageUserOption">
          <a-menu-item :key="item.key">
            <router-link
              :to="item.value"
              tag="a"
            >
              {{ $t('menu.label.' + item.key) }}
            </router-link>
          </a-menu-item>
        </template>
      </a-sub-menu>
      <a-sub-menu key="sub4">
        <span slot="title">
          <a-icon type="setting" />
          <span>{{this.$t('menu.label.manage')}}</span>
        </span>
        <template v-for="item in manageOption">
        <a-menu-item :key="item.key">
          <router-link
            :to="item.value"
            tag="a"
          >
            {{ $t('menu.label.' + item.key) }}
          </router-link>
        </a-menu-item>
        </template>
      </a-sub-menu>
    </a-menu>
  </div>
</template>

<script>
import dictionaryService from '@/service/dictionary';
import lodash from 'lodash';

export default {
  name: 'Menu',
  data() {
    return {
      rootSubmenuKeys: ['sub1', 'sub2', 'sub4'],
      openKeys: [],
      manageOption: [],
      manageUserOption: [],
      manageSportGroundOption: []
    };
  },
  mounted() {
    this.getMenuOptions();
  },
  methods: {
    getMenuOptions() {
      dictionaryService.getMultipleDictionaries(['维护', '用户管理', '场地管理']).then((n) => {
        const manage = n.data.find((taget) => taget.name === '维护');
        const manageUser = n.data.find((taget) => taget.name === '用户管理');
        const manageSportGround = n.data.find((target) => target.name === '场地管理');
        if (!lodash.isEmpty(manageSportGround.dictionaryOptions)) {
          this.manageSportGroundOption = lodash.cloneDeep(manageSportGround.dictionaryOptions);
        }
        this.manageUserOption = lodash.cloneDeep(manageUser.dictionaryOptions);
        this.manageOption = lodash.cloneDeep(manage.dictionaryOptions);
      });
    },
    onOpenChange(openKeys) {
      const latestOpenKey = openKeys.find((key) => this.openKeys.indexOf(key) === -1);
      if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
        this.openKeys = openKeys;
      } else {
        this.openKeys = latestOpenKey ? [latestOpenKey] : [];
      }
    }
  }
};
</script>

<style scoped>
  .menu-wrapper {
    max-width: 256px;
    font-weight: 500;
    font-style: normal;
    line-height: 32px;
  }
  .menu {
    border: 1px solid #eee;
    color: #00A1D8;
  }
</style>
