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
      this.userNameList.forEach((n) => {
        if (n.toLocaleUpperCase() === values.toLocaleUpperCase()) {
          callback(this.$t('register.validate.reName'));
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
