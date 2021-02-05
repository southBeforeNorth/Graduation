export default {
  data() {
    return {
      merchantColumns: [
        'merchantName',
        'contactPerson',
        'phone',
        'active',
        'laseModifiedBy',
        'lastModifiedTime'
      ]
    };
  },
  computed: {
    columns() {
      const merchantColumns = [
        {
          title: this.$t('merchant.manage.merchantName'),
          dataIndex: 'merchantName',
          width: '18%',
          scopedSlots: { customRender: 'merchantName' },
          sorter: (a, b) => a.merchantName.length - b.merchantName.length
        },
        {
          title: this.$t('merchant.manage.contactPerson'),
          dataIndex: 'contactPerson',
          width: '10%',
          scopedSlots: { customRender: 'contactPerson' },
          sorter: (a, b) => a.contactPerson.length - b.contactPerson.length
        },
        {
          title: this.$t('merchant.manage.phone'),
          dataIndex: 'phone',
          width: '15%',
          scopedSlots: { customRender: 'phone' }
        },
        {
          title: this.$t('merchant.manage.active'),
          dataIndex: 'active',
          width: '10%',
          scopedSlots: { customRender: 'active' }
        },
        {
          title: this.$t('merchant.manage.lastModifiedBy'),
          dataIndex: 'lastModifiedBy',
          width: '10%',
          scopedSlots: { customRender: 'lastModifiedBy' }
        },
        {
          title: this.$t('merchant.manage.lastModifiedTime'),
          dataIndex: 'lastModifiedTime',
          width: '15%',
          scopedSlots: { customRender: 'lastModifiedTime' }
        },
        {
          title: this.$t('common.action'),
          dataIndex: 'action',
          width: '12%',
          scopedSlots: { customRender: 'action' }
        }
      ];
      return merchantColumns;
    }
  }
};
