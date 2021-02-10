export default {
  computed: {
    rules() {
      return {
        name: [
          {
            required: true,
            message: this.computeValidate()
          }
        ],
        password: [
          {
            required: true,
            message: this.$t('register.validate.password')
          }
        ]
      };
    }

  },
  methods: {
    computeValidate() {
      if (this.type === 'user') {
        return this.$t('login.validate.userName');
      }
      if (this.type === 'merchant') {
        return this.$t('login.validate.merchantName');
      }
      if (this.type === 'manage') {
        return this.$t('login.validate.manageName');
      }
      return 'null';
    }
  }
};
