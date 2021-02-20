export default {
  data() {
    return {
      sportGroundColumns: [
        'name',
        'phone',
        'type',
        'laseModifiedBy',
        'lastModifiedTime'
      ]
    };
  },
  computed: {
    columns() {
      const sportGroundColumns = [
        {
          title: this.$t('sportGround.model.label.name'),
          dataIndex: 'name',
          width: '18%',
          scopedSlots: { customRender: 'name' },
          sorter: (a, b) => a.name.length - b.name.length
        },
        {
          title: this.$t('sportGround.model.label.phone'),
          dataIndex: 'phone',
          width: '15%',
          scopedSlots: { customRender: 'phone' }
        },
        {
          title: this.$t('sportGround.model.label.type'),
          dataIndex: 'type',
          width: '15%',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: this.$t('common.label.lastModifiedBy'),
          dataIndex: 'lastModifiedBy',
          width: '10%',
          scopedSlots: { customRender: 'lastModifiedBy' }
        },
        {
          title: this.$t('common.label.lastModifiedTime'),
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
      return sportGroundColumns;
    }
  }
};
