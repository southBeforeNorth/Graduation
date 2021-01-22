export default {
  data() {
    return {
      dictionaryColumns: [
        'name',
        'description',
        'laseModifiedBy',
        'lastModifiedTime'
      ],
      dictionaryOptionColumns: [
        'key',
        'value',
        'optionDescription',
        'sequence',
        'default'
      ]
    };
  },
  computed: {
    columns() {
      const dictionaryColumns = [
        {
          title: this.$t('dictionary.name'),
          dataIndex: 'name',
          width: '15%',
          scopedSlots: { customRender: 'name' },
          sorter: (a, b) => a.name.length - b.name.length
        },
        {
          title: this.$t('dictionary.description'),
          dataIndex: 'description',
          width: '35%',
          scopedSlots: { customRender: 'description' },
          sorter: (a, b) => a.description.length - b.description.length
        },
        {
          title: this.$t('dictionary.lastModifiedBy'),
          dataIndex: 'lastModifiedBy',
          width: '15%',
          scopedSlots: { customRender: 'lastModifiedBy' }
        },
        {
          title: this.$t('dictionary.lastModifiedTime'),
          dataIndex: 'lastModifiedTime',
          width: '15%',
          scopedSlots: { customRender: 'lastModifiedTime' },
          sorter: (a, b) => a.lastModifiedTime - b.lastModifiedTime
        },
        {
          title: this.$t('common.action'),
          dataIndex: 'action',
          width: '12%',
          scopedSlots: { customRender: 'action' }
        }
      ];
      return dictionaryColumns;
    },
    innerColumns() {
      const dictionaryOptionColumns = [
        {
          title: this.$t('dictionaryOptions.key'),
          dataIndex: 'key',
          width: '15%',
          scopedSlots: { customRender: 'key' },
          sorter: (a, b) => a.key.length - b.key.length
        },
        {
          title: this.$t('dictionaryOptions.value'),
          dataIndex: 'value',
          width: '15%',
          scopedSlots: { customRender: 'value' }
        },
        {
          title: this.$t('dictionaryOptions.description'),
          dataIndex: 'description',
          width: '20%',
          scopedSlots: { customRender: 'optionDescription' }
        },
        {
          title: this.$t('dictionaryOptions.sequence'),
          dataIndex: 'sequence',
          width: '8%',
          scopedSlots: { customRender: 'sequence' },
          sorter: (a, b) => a.sequence - b.sequence
        },
        {
          title: this.$t('dictionaryOptions.default'),
          dataIndex: 'default',
          width: '12%',
          scopedSlots: { customRender: 'default' }
        },
        {
          title: this.$t('common.action'),
          dataIndex: 'action',
          width: '12%',
          scopedSlots: { customRender: 'action' }
        }
      ];
      return dictionaryOptionColumns;
    }
  }
};
