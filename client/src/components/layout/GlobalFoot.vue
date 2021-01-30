<template>
  <a-layout>
    <a-layout-footer>
      <a-divider></a-divider>
      <a-row>
        <a-col :span="8">
          <div class="logo">
            <router-link
              :to="{ path: '/feature/home/homeBody' }"
            >
              <img src="../../../static/foot.svg"  alt="logo" class="logo-icon"/>
              <span style="font-weight: bolder;
              font-size: 40px; font-family: 华光行草_CNKI">
                {{ $t('home.navigation.loge') }}
              </span>
            </router-link>
          </div>
        </a-col>
        <a-col :span="8" style="padding-top: 15px">
          <a-row>
          <span>{{$t('foot.messageOne')}}</span>
          </a-row>
          <a-row>
          <span>{{$t('foot.messageTwo')}}</span>
          </a-row>
          <a-row>
            <span>{{$t('foot.messageTree')}}</span>
          </a-row>
        </a-col>
        <a-col :span="8">
          <img src="../../../static/foot-p.jpg"  alt="logo" class="logo-p"/>
        </a-col>
      </a-row>
    </a-layout-footer>
  </a-layout>
</template>

<script>
export default {
  name: 'GlobalFoot'
};
</script>
<style scoped>
  /deep/ .ant-layout-footer{
    background: white;
  }
  .logo {
    display: inline;
    font-size: 30px;
    padding:0 10px;
    position:relative;
  }

  .logo-icon {
    margin-left: 5%;
    margin-bottom: 3%;
    height: 100%;
    width: 10%;
    position: relative;
  }
  .logo-p {
    margin-left: 50%;
    margin-bottom: 3%;
    height: 100%;
    width: 20%;
    position: relative;
  }
</style>
