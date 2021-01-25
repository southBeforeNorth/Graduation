export default {
  computed: {
    rules() {
      return {
        name: [
          {
            required: true,
            message: this.$t('register.validate.name')
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
  }
};
