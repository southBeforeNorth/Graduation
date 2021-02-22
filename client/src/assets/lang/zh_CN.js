export default {
  homeBodyList: {
    label: {
      address: '地址：',
      price: '运动价：',
      priceTime: '元/小时'
    }
  },
  sportGround: {
    manage: {
      name: '场地管理',
      addButton: '添加场地'
    },
    model: {
      placeholder: {
        phone: '请输入联系号码',
        name: '请输入场地名称',
        type: '请选择场地类型',
        weeks: '请选择场地开场日期',
        time: '请选择场地开场时间',
        city: '请选择场地所在城市',
        detailedAddress: '请输入场地所在详细地址',
        description: '请输入场馆简介信息'
      },
      label: {
        operation: '操作',
        phone: '联系号码',
        name: '场地名称',
        type: '场地类型',
        weeks: '可选日期',
        time: '可预订时间',
        area: '场地划分',
        areaName: '时间/场地编号',
        price: '场地费用',
        priceValue: '元/每小时',
        add: '添加场地编号',
        picture: '图片描述',
        description: '场馆简介'
      },
      table: {
        am1: '07:00',
        am2: '08:00',
        am3: '09:00',
        am4: '10:00',
        am5: '11:00',
        pm1: '12:00',
        pm2: '13:00',
      },
      validate: {
        name: '您场地的名称不能为空',
        maxName: '您场地的名称过长',
        minName: '您场地的名称过短',
        type: '请选择您场地的类型',
        phone: '请输入场地联系号码',
        city: '请选择场地所在城市',
        detailedAddress: '请输入场地所在详细地址',
        price: '请输入场地费用',
        submitSuccess: '提交成功',
        submitError: '提交失败',
        updateSuccess: '修改成功',
        updateError: '修改失败'
      }
    }
  },
  merchant: {
    manage: {
      name: '商家用户管理',
      addButton: '新建用户',
      merchantName: '用户名',
      contactPerson: '联系人',
      phone: '联系号码',
      active: '状态',
      lastModifiedBy: '修改人',
      lastModifiedTime: '修改时间',
      allMerchant: '全部用户',
      apply: '正在申请用户'
    },
    register: {
      label: {
        topText: '申请加入场馆',
        merchantName: '场馆名称',
        contactPerson: '联系人',
        phone: '手机号码',
        city: '城市地区',
        detailedAddress: '详细地址',
        active: '激活',
        password: '密码',
        rePassword: '重复密码',
        submit: '立即提交',
        update: '立即修改',
        success: '提交成功',
        successContent: '三个工作日内工作人员将与您联系并确认',
        error: '提交失败',
        validateSuccess: '验证码正确',
        addSuccess: '添加成功',
        updateSuccess: '修改成功',
        updateError: '修改失败',
        add: '添加',
        view: '查看',
        edit: '编辑'
      },
      placeHolder: {
        merchantName: '请输入您场馆的名称',
        contactPerson: '请输入责联系人的名称',
        phone: '请输入负责联系人的联系号码',
        city: '请选择您场馆所在城市地区',
        detailedAddress: '请输入您场馆所在详细地址',
        password: '请新输入一个登录密码',
        rePassword: '请重复输入密码确认'
      },
      validate: {
        merchantName: '您场馆的名字不能为空',
        merchantNameMin: '场馆名字过短',
        merchantNameMax: '场馆名字过长',
        reName: '此场馆名称已存在，请重新输入',
        contactPerson: '负责联系人不能为空',
        phone: '联系号码不能为空',
        phoneFormat: '联系号码格式不正确，请注意修改',
        city: '请选择您场馆所在城市地区',
        detailedAddress: '请输入您场馆的详细地址'
      }
    }
  },
  foot: {
    messageOne: '联系我们：13421704215',
    messageTwo: '地址：广东省珠海市唐家湾镇北京理工大学珠海学院',
    messageTree: '上班时间：周一至周六 10:00-20:00'
  },
  home: {
    navigation: {
      home: '首页',
      space: '场地预约',
      merchant: '商家入驻',
      loge: '场地预约'
    },
    body: {
      search: '请输入场馆关键词',
      searchButton: '立即搜索',
      searchText: '立即运动，秀出你的活力'
    }
  },
  globalHeader: {
    login: '登录',
    signOut: '退出登录',
    register: '注册',
    welcome: '欢迎，',
    manageCenter: '管理中心',
    userCenter: '用户中心',
    merchantCenter: '商家中心'
  },
  menu: {
    label: {
      manage: '维护',
      userManage: '用户管理',
      dictionary: '字典',
      merchantMenu: '商家',
      sportGroundManage: '场地管理',
      sportGround: '场地'
    }
  },
  login: {
    warningText: {
      top: '登录',
      success: '登录成功',
      error: '用户名或密码错误，请重新输入'
    },
    label: {
      user: '我是普通用户',
      merchant: '我是商家',
      userName: '用户昵称',
      merchantName: '商家用户名',
      manageName: '管理员用户名',
      password: '密码'
    },
    validate: {
      userName: '请输入用户昵称',
      merchantName: '请输入商家用户名',
      manageName: '请输入管理员用户名',
    },
    placeHolder: {
      userName: '请输入用户昵称',
      merchantName: '请输入商家用户名',
      manageName: '请输入管理员用户名',
      password: '密码'
    },
    button: {
      login: '点击登录',
      register: '免费注册'
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
    label: {
      lastModifiedBy: '修改人',
      lastModifiedTime: '修改时间'
    },
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
