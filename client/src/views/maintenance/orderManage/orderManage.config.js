export default {
  data() {
    return {
      OrderColumns: [
        'sportGroundName',
        'contactName',
        'contactPhone',
        'orderDate',
        'totalPrice',
        'status'
      ]
    };
  },
  computed: {
    columns() {
      const merchantOrderColumns = [
        {
          title: this.$t('order.label.sportGroundName'),
          dataIndex: 'sportGroundName',
          width: '18%',
          scopedSlots: { customRender: 'sportGroundName' },
          sorter: (a, b) => a.sportGroundName.length - b.sportGroundName.length
        },
        {
          title: this.$t('order.label.contactName'),
          dataIndex: 'contactName',
          width: '10%',
          scopedSlots: { customRender: 'contactName' },
          sorter: (a, b) => a.contactName.length - b.contactName.length
        },
        {
          title: this.$t('order.label.contactPhone'),
          dataIndex: 'contactPhone',
          width: '15%',
          scopedSlots: { customRender: 'contactPhone' }
        },
        {
          title: this.$t('order.label.orderDate'),
          dataIndex: 'orderDate',
          width: '10%',
          scopedSlots: { customRender: 'orderDate' }
        },
        {
          title: this.$t('order.label.totalPrice'),
          dataIndex: 'totalPrice',
          width: '10%',
          scopedSlots: { customRender: 'totalPrice' }
        },
        {
          title: this.$t('order.label.status'),
          dataIndex: 'status',
          width: '10%',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: this.$t('common.action'),
          dataIndex: 'action',
          width: '15%',
          scopedSlots: { customRender: 'action' }
        }
      ];

      const userOrderColumns = [
        {
          title: this.$t('order.label.sportGroundName'),
          dataIndex: 'sportGroundName',
          width: '18%',
          scopedSlots: { customRender: 'sportGroundName' },
          sorter: (a, b) => a.sportGroundName.length - b.sportGroundName.length
        },
        {
          title: this.$t('order.label.merchantName'),
          dataIndex: 'merchantName',
          width: '10%',
          scopedSlots: { customRender: 'merchantName' },
          sorter: (a, b) => a.merchantName.length - b.merchantName.length
        },
        {
          title: this.$t('order.label.contactMerchant'),
          dataIndex: 'contactMerchant',
          width: '15%',
          scopedSlots: { customRender: 'contactMerchant' }
        },
        {
          title: this.$t('order.label.orderDate'),
          dataIndex: 'orderDate',
          width: '10%',
          scopedSlots: { customRender: 'orderDate' }
        },
        {
          title: this.$t('order.label.totalPrice'),
          dataIndex: 'totalPrice',
          width: '10%',
          scopedSlots: { customRender: 'totalPrice' }
        },
        {
          title: this.$t('order.label.status'),
          dataIndex: 'status',
          width: '10%',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: this.$t('common.action'),
          dataIndex: 'action',
          width: '15%',
          scopedSlots: { customRender: 'action' }
        }
      ];

      const manageOrderColumns = [
        {
          title: this.$t('order.label.sportGroundName'),
          dataIndex: 'sportGroundName',
          width: '18%',
          scopedSlots: { customRender: 'sportGroundName' },
          sorter: (a, b) => a.sportGroundName.length - b.sportGroundName.length
        },
        {
          title: this.$t('order.label.contactName'),
          dataIndex: 'contactName',
          width: '15%',
          scopedSlots: { customRender: 'contactName' },
          sorter: (a, b) => a.contactName.length - b.contactName.length
        },
        {
          title: this.$t('order.label.merchantName'),
          dataIndex: 'merchantName',
          width: '10%',
          scopedSlots: { customRender: 'merchantName' },
          sorter: (a, b) => a.merchantName.length - b.merchantName.length
        },
        {
          title: this.$t('order.label.contactMerchant'),
          dataIndex: 'contactMerchant',
          width: '15%',
          scopedSlots: { customRender: 'contactMerchant' }
        },
        {
          title: this.$t('order.label.orderDate'),
          dataIndex: 'orderDate',
          width: '10%',
          scopedSlots: { customRender: 'orderDate' }
        },
        {
          title: this.$t('order.label.totalPrice'),
          dataIndex: 'totalPrice',
          width: '10%',
          scopedSlots: { customRender: 'totalPrice' }
        },
        {
          title: this.$t('order.label.status'),
          dataIndex: 'status',
          width: '10%',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: this.$t('common.action'),
          dataIndex: 'action',
          width: '15%',
          scopedSlots: { customRender: 'action' }
        }
      ];

      if (this.$store.state.user.type === 'user') {
        return userOrderColumns;
      }

      if (this.$store.state.user.type === 'merchant') {
        return merchantOrderColumns;
      }

      if (this.$store.state.user.type === 'manage') {
        return manageOrderColumns;
      }
    }
  }
};
