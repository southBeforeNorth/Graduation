export default {
  data() {
    return {
      userColumns: [
        'name',
        'phone',
        'birthDay',
        'sex',
        'laseModifiedBy',
        'lastModifiedTime'
      ]
    };
  },
  computed: {
    columns() {
      const userColumns = [
        {
          title: this.$t('userManage.label.name'),
          dataIndex: 'name',
          width: '18%',
          scopedSlots: { customRender: 'name' },
          sorter: (a, b) => a.name.length - b.name.length
        },
        {
          title: this.$t('userManage.label.birthDay'),
          dataIndex: 'birthDay',
          width: '15%',
          scopedSlots: { customRender: 'birthDay' },
          sorter: (a, b) => a.birthDay - b.birthDay
        },
        {
          title: this.$t('userManage.label.phone'),
          dataIndex: 'phone',
          width: '10%',
          scopedSlots: { customRender: 'phone' }
        },
        {
          title: this.$t('userManage.label.sex'),
          dataIndex: 'sex',
          width: '10%',
          scopedSlots: { customRender: 'sex' }
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
      return userColumns;
    }
  }
};
