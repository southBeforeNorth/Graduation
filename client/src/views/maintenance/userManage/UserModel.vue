<template>
<div ref="replayModal">
  <a-modal
    :get-container="()=>this.$refs.replayModal"
    v-model="visibleScoped"
    :title="modelTitle"
    :width="700"
    :footer="null"
    @cancel="()=>{
           this.$refs.userModel.clearValidate();
           this.visibleScoped = false;
           }"
  >
    <template>
      <a-form-model
        ref="userModel"
        :label-col="{span: 6}"
        :wrapper-col="{span: 16}"
        :model="form"
        :rules="rules"
      >
        <a-form-model-item
          :label="$t('register.label.name')"
          prop="name"
        >
          <a-input
            :disabled="!editable"
            :placeholder="$t('register.placeHolder.name')"
            v-model="form.name"
          >
            <a-icon slot="user" type="bank" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>

        <a-form-model-item
          :label="$t('register.label.phone')"
          prop="phone"
        >
          <a-input
            :disabled="!editable"
            :placeholder="$t('register.placeHolder.phone')"
            v-model="form.phone"
          >
            <a-icon slot="phone" type="user" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>

        <a-form-model-item
          :label="$t('register.label.password')"
          prop="password"
        >
          <a-input-password
            :disabled="!editable"
            :placeholder="$t('register.placeHolder.password')"
            v-model="form.password"
          >
            <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
          </a-input-password >
        </a-form-model-item>
        <a-form-model-item
          :label="$t('register.label.sex')"
          prop="sex"
        >
          <a-radio-group
            v-model="form.sex">
            <a-radio value="男" >
              <a-icon type="man" />
              {{ $t('register.label.boy') }}
            </a-radio>
            <a-radio value="2">
              <a-icon type="woman" />
              {{ $t('register.label.girl') }}
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item
          :label="$t('register.label.birthDay')"
          prop="birthDay"
        >
          <a-date-picker
            v-model="form.birthDay"
            type="date"
            :disabled-date="disabledDate"
            :placeholder="$t('register.placeHolder.birthDay')"
            style="width: 100%;"
          />
        </a-form-model-item>
        <a-row
          v-if="editable"
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
                        v-if="this.selectedUserInfo === null">
                {{ $t('merchant.register.label.submit') }}
              </a-button>
              <a-button type="primary" @click="updateForm()"
                        v-if="this.selectedUserInfo !== null">
                {{ $t('merchant.register.label.update') }}
              </a-button>
              <a-button style="margin-left: 10px"
                        @click="()=>{
                        this.$refs.userModel.resetFields();
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
import userService from '@/service/user';
import rules from '@/views/maintenance/userManage/userModel.rules';
import lodash from 'lodash';
import moment from 'moment';

export default {
  name: 'UserModel',
  mixins: [rules],
  props: {
    editable: {
      type: Boolean,
      default: false
    },
    selectedUserInfo: {
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
      form: {
        name: null,
        password: null,
        birthDay: null,
        sex: null,
        phone: null
      },
      id: null,
      user: null,
      userNameList: []
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
      if (this.selectedUserInfo === null) {
        return this.$t('merchant.register.label.add');
      }
      if (this.editable === true) {
        return this.$t('merchant.register.label.edit');
      }
      return this.$t('merchant.register.label.view');
    }
  },
  watch: {
    selectedUserInfo(newInfo) {
      if (!lodash.isEmpty(newInfo)) {
        this.user = newInfo;
        this.id = newInfo.id;
        this.form.name = newInfo.name;
        this.form.phone = newInfo.phone;
        this.form.sex = newInfo.sex;
        this.form.birthDay = moment(newInfo.birthDay);
        this.form.password = newInfo.password;
      } else {
        this.user = null;
        this.id = null;
        this.form.name = null;
        this.form.sex = null;
        this.form.phone = null;
        this.form.birthDay = null;
        this.form.password = null;
        this.$refs.userModel.clearValidate();
      }
    }
  },
  mounted() {
    this.getUserNameList();
  },
  methods: {
    disabledDate(current) {
      return moment().diff(current) < 0;
    },
    updateForm() {
      const that = this;
      this.$refs.userModel.validate().then(() => {
        const user = {
          name: this.form.name,
          password: this.form.password,
          birthDay: this.form.birthDay.valueOf(),
          sex: this.form.sex,
          phone: this.form.phone
        };
        userService.updateUserByManager(this.id, user).then((n) => {
          if (n.success) {
            that.visibleScoped = false;
            that.$emit('onClose');
            that.$message.success(this.$t('userManage.warningText.updateSuccess'));
            return;
          }
          that.$message.error(this.$t('userManage.warningText.updateError'));
        });
      });
    },
    submitForm() {
      const that = this;
      this.$refs.userModel.validate().then(() => {
        const user = {
          name: this.form.name,
          password: this.form.password,
          birthDay: this.form.birthDay.valueOf(),
          sex: this.form.sex,
          phone: this.form.phone
        };
        userService.createUser(user).then((n) => {
          if (n.success) {
            that.visibleScoped = false;
            that.$emit('onClose');
            that.$message.success(this.$t('userManage.warningText.submitSuccess'));
            return;
          }
          that.$message.error(this.$t('userManage.warningText.submitError'));
        });
      });
    },
    getUserNameList() {
      userService.getUserNameList().then((n) => {
        if (n.success) {
          this.userNameList = n.data;
        }
      });
    }
  }
};
</script>

<style scoped>
  /deep/ .ant-modal-header {
    background: #D1E3FC;
  }
  /deep/ .ant-input {
    height: 40px;
  }
</style>
