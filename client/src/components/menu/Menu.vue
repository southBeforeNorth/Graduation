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
      <a-sub-menu key="sub1" v-if="type === 'user'">
        <span slot="title"><a-icon type="shopping-cart" />
          <span>{{this.$t('menu.label.userOrderName')}}</span>
        </span>
        <template v-for="item in manageUserOrder">
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
      <a-sub-menu key="sub2" v-if="type !=='user'">
        <span slot="title"><a-icon type="appstore" />
          <span>{{this.$t('menu.label.sportGroundManage')}}</span>
        </span>
        <template v-for="item in manageSportGroundOption">
          <a-menu-item :key="item.key">
            <router-link
              :to="item.value"
              tag="a"
            >
              <span v-if="type === 'merchant'">
                {{ $t('menu.label.' + item.key) }}
              </span>
              <span v-else>
                {{ $t('menu.label.manager.' + item.key) }}
              </span>
            </router-link>
          </a-menu-item>
        </template>
      </a-sub-menu>
      <a-sub-menu key="sub5" v-if="type ==='manage'">
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
      <a-sub-menu key="sub4" v-if="type === 'manage'">
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
      <a-sub-menu key="sub4" v-if="type === 'merchant'">
        <span slot="title">
          <a-icon type="setting" />
          <span>
            {{this.$t('menu.label.merchantConfig')}}
          </span>
        </span>
        <template v-for="item in merchantInfo">
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
      <a-sub-menu key="sub4" v-if="type === 'user'">
        <span slot="title">
          <a-icon type="setting" />
          <span>
            {{this.$t('menu.label.userConfig')}}
          </span>
        </span>
        <template v-for="item in userInfo">
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
      manageSportGroundOption: [],
      manageUserOrder: [],
      merchantInfo: [],
      userInfo: []
    };
  },
  mounted() {
    this.getMenuOptions();
  },
  computed: {
    type() {
      return this.$store.state.user.type;
    }
  },
  methods: {
    getMenuOptions() {
      dictionaryService.getMultipleDictionaries(['维护', '用户管理', '场地管理', '我的订单', '商家中心', '个人中心'])
        .then((n) => {
          const manage = n.data.find((taget) => taget.name === '维护');
          const manageUser = n.data.find((taget) => taget.name === '用户管理');
          const manageSportGround = n.data.find((target) => target.name === '场地管理');
          const manageUserOrder = n.data.find((target) => target.name === '我的订单');
          const merchant = n.data.find((target) => target.name === '商家中心');
          const user = n.data.find((target) => target.name === '个人中心');
          if (!lodash.isEmpty(manageSportGround.dictionaryOptions)) {
            this.manageSportGroundOption = lodash.cloneDeep(manageSportGround.dictionaryOptions);
          }
          this.manageUserOrder = lodash.cloneDeep(manageUserOrder.dictionaryOptions);
          this.manageUserOption = lodash.cloneDeep(manageUser.dictionaryOptions);
          this.manageOption = lodash.cloneDeep(manage.dictionaryOptions);
          this.merchantInfo = lodash.cloneDeep(merchant.dictionaryOptions);
          this.userInfo = lodash.cloneDeep(user.dictionaryOptions);
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
