export default {
  computed: {
    rules() {
      return {
        name: [
          {
            required: true,
            message: this.$t('sportGround.model.validate.name')
          },
          { min: 2, message: this.$t('sportGround.model.validate.minName') },
          { max: 128, message: this.$t('sportGround.model.validate.maxName') }
        ],
        type: [
          {
            required: true,
            message: this.$t('sportGround.model.validate.type')
          }
        ],
        phone: [
          {
            required: true,
            message: this.$t('sportGround.model.validate.phone')
          },
          { validator: this.validatePhone, trigger: 'blur' }
        ],
        city: [
          {
            required: true,
            message: this.$t('sportGround.model.validate.city')
          }
        ],
        detailedAddress: [
          {
            required: true,
            message: this.$t('sportGround.model.validate.detailedAddress')
          }
        ],
        price: [
          {
            required: true,
            message: this.$t('sportGround.model.validate.price')
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
    }
  }
};
