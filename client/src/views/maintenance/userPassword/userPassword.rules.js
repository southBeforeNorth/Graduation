export default {
  computed: {
    rules() {
      return {
        oldPassword: [
          {
            required: true,
            message: this.$t('merchantPassword.validate.oldPassword')
          }
        ],
        newPassword: [
          {
            required: true,
            message: this.$t('merchantPassword.validate.newPassword')
          },
          { min: 6, max: 16, message: this.$t('register.validate.passwordLength') }
        ],
        rePassword: [
          {
            required: true,
            message: this.$t('merchantPassword.validate.rePassword')
          },
          { validator: this.validateRePassword, trigger: 'blur' }
        ]
      };
    }
  },
  methods: {
    validateRePassword(rule, values, callback) {
      if (values !== this.form.newPassword) {
        callback(this.$t('register.validate.rePasswordContent'));
      }
      callback();
    }
  }
};
