export default {
  login: {
    warningText: {
      top: '登录',
      success: '登录成功',
      error: '用户名或密码错误，请重新输入'
    },
    label: {
      name: '昵称',
      password: '密码'
    },
    placeHolder: {
      name: '请输入昵称',
      password: '密码'
    },
    button: {
      login: '点击登录',
      register: '点击注册'
    }
  },
  register: {
    warningText: {
      top: '注册',
      success: '注册成功，请登录',
      error: '注册失败',
      submit: '点击注册',
      reset: '重置',
      validate: '请输入验证码',
      validateError: '验证码输入错误，请重新输入'
    },
    validate: {
      name: '请输入昵称',
      reName: '此昵称已被占用',
      nameMin: '昵称过短',
      nameMax: '昵称过长',
      password: '请输入密码',
      passwordLength: '密码长度应该在6-16',
      rePassword: '请重新校验密码',
      rePasswordContent: '密码输入不一致，请重新确认',
      birthDay: '请选择出生日期',
      sex: '请选择性别',
    },
    label: {
      name: '昵称',
      password: '密码',
      rePassword: '重复密码',
      birthDay: '生日',
      sex: '性别',
      boy: '男',
      girl: '女'
    },
    placeHolder: {
      name: '请输入昵称',
      password: '密码 （6-16个字符，区分大小写）',
      rePassword: '请重复输入验证密码',
      birthDay: '请选择出生日期'
    }
  },
  common: {
    action: '操作',
    view: '查看',
    confirmTitle: '提示',
    preview: '预览',
    successText: {
      successFully: '成功!',
      save: '保存成功！',
      create: '添加成功！',
      delete: '删除成功!',
      update: '更新成功!',
      assign: '分配成功！',
      operate: '操作成功！'
    },
    button: {
      ok: '确认',
      cancel: '取消',
      delete: '删除',
      save: '保存',
      edit: '编辑',
      add: '添加',
      submit: '提交',
      update: '更新',
      withdraw: '撤销',
      approve: '批准',
      return: '驳回',
      close: '关闭页面',
      ignore: '忽略',
      copyAsNew: '复制',
      copyFromExisting: '复制请求',
      view: '查看',
      search: '搜索',
      reset: '重置',
      preview: '预览'
    },
    warningText: {
      cancelEdit: '你确定要取消这次修改?',
      delete: '你确定要删除这一项？',
      add: '请先保存或者取消新建的项'
    },
    validateMessage: {
      input: '您必须输入一个非空值',
      select: '您必须选择一个非空值'
    },
    messageBoxTitle: {
      error: '错误'
    }
  },
  dictionary: {
    addButton: '新建字典',
    name: '字典名',
    description: '描述',
    classification: '类别',
    createdBy: '创建人',
    createdTime: '创建时间',
    lastModifiedBy: '修改人',
    lastModifiedTime: '修改时间',
    createDictionary: '添加字典',
    inputMessage: '必须填入非空值',
    warningText: '字典名必须唯一',
    createSuccess: '添加成功！',
    deleteSuccess: '删除成功!',
    cancelModal: '确定取消该修改？',
    updateSuccess: '更新成功!',
    deleteModal: '确定要删除该字典？',
    dictionaryEmptyText: '字典主键不能为空!',
    validateOverLength: '值的长度必须小于255!',
    addMultipleDictionaryModel: '请先保存或取消先添加的字典栏！'
  },
  dictionaryOptions: {
    key: '选项名',
    value: '选项值',
    metaValue: '元值',
    description: '描述',
    sequence: '顺序',
    default: '是否默认',
    optionEmptyText: '选项名不能为空!',
    warningText: '选项名必须唯一!',
    validateOverLength: '值的长度必须小于等于5!',
    deleteModal: '确定定要删除该字典属性?',
    addMultipleOptionModel: '请先保存或取消先添加的字典属性栏！'
  }
};
