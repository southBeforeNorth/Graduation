export default {
  computed: {
    rules() {
      return {
        rate: [
          {
            required: true,
            message: this.$t('order.validate.rate')
          }
        ],
        comment: [
          {
            required: true,
            message: this.$t('order.validate.comment')
          }
        ]
      };
    }
  }
};
