import lodash from 'lodash';

export default {
  computed: {
    rules() {
      return {
        name: [
          {
            required: true,
            message: this.$t('register.validate.name')
          },
          { min: 2, message: this.$t('register.validate.nameMin') },
          { max: 64, message: this.$t('register.validate.nameMax') },
          { validator: this.validateName }
        ],
        phone: [
          {
            required: true,
            message: this.$t('merchant.register.validate.phone')
          },
          { validator: this.validatePhone, trigger: 'blur' }
        ],
        birthDay: [
          {
            required: true,
            message: this.$t('register.validate.birthDay')
          }
        ],
        sex: [
          {
            required: true,
            message: this.$t('register.validate.sex')
          }
        ]
      };
    }
  },
  methods: {
    validatePhone(rule, values, callback) {
      const isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
      const isMob = /^0?1[3|4|5|8][0-9]\d{8}$/;
      if (!isPhone.test(values) && !isMob.test(values)) {
        callback(this.$t('merchant.register.validate.phoneFormat'));
      }
      callback();
    },
    validateName(rule, values, callback) {
      let cache = lodash.cloneDeep(this.userNameList);
      if (!lodash.isEmpty(this.user)) {
        cache = cache
          .filter((n) => n !== this.user.name);
      }
      cache.forEach((n) => {
        if (n.toLocaleUpperCase() === values.toLocaleUpperCase()) {
          callback(this.$t('register.validate.reName'));
        }
      });
      callback();
    }
  }
};
