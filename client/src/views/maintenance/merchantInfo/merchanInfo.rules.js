import lodash from 'lodash';

export default {
  computed: {
    rules() {
      return {
        merchantName: [
          {
            required: true,
            message: this.$t('merchant.register.validate.merchantName')
          },
          { min: 2, message: this.$t('merchant.register.validate.merchantNameMin') },
          { max: 128, message: this.$t('merchant.register.validate.merchantNameMax') },
          { validator: this.validateName }
        ],
        contactPerson: [
          {
            required: true,
            message: this.$t('merchant.register.validate.contactPerson')
          }
        ],
        phone: [
          {
            required: true,
            message: this.$t('merchant.register.validate.phone')
          },
          { validator: this.validatePhone, trigger: 'blur' }
        ],
        city: [
          {
            required: true,
            message: this.$t('merchant.register.validate.city')
          }
        ],
        detailedAddress: [
          {
            required: true,
            message: this.$t('merchant.register.validate.detailedAddress')
          }
        ],
        password: [
          {
            required: true,
            message: this.$t('register.validate.password')
          },
          { min: 6, max: 16, message: this.$t('register.validate.passwordLength') }
        ],
        rePassword: [
          {
            required: true,
            message: this.$t('register.validate.rePassword')
          },
          { validator: this.validateRePassword, trigger: 'blur' }
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
      let cache = lodash.cloneDeep(this.merchantNameList);
      if (!lodash.isEmpty(this.merchant)) {
        cache = cache
          .filter((n) => n !== this.merchant.merchantName);
      }
      cache.forEach((n) => {
        if (n.toLocaleUpperCase() === values.toLocaleUpperCase()) {
          callback(this.$t('merchant.register.validate.reName'));
        }
      });
      callback();
    },
    validateRePassword(rule, values, callback) {
      if (values !== this.form.password) {
        callback(this.$t('register.validate.rePasswordContent'));
      }
      callback();
    }
  }
};
