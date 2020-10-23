/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

export default {
  common: {
    home: '主页',
    menuData: [
      {
        title: '应用',
        child: [
          /* {
            title: '注册中心配置',
            href: '/registry-center'
          },*/
          {
            title: '配置',
            href: '/app-config'
          }
        ]
      },
      {
        title: '作业',
        child: [
          {
            title: '配置',
            href: '/job-config'
          },
          {
            title: '状态',
            href: '/job-status'
          }
        ]
      },
      {
        title: '作业历史',
        child: [
          {
            title: '历史dashboard',
            href: '/history-dashboard'
          },
          {
            title: '作业历史轨迹',
            href: '/history-trace'
          },
          {
            title: '作业运行状态',
            href: '/history-status'
          }
        ]
      }
    ],
    connect: '已连接',
    connection: '连接',
    del: '删除',
    notify: {
      title: '提示',
      addSucMessage: '添加成功',
      editSucMessage: '修改成功',
      actionSucMessage: '操作成功',
      conSucMessage: '连接成功',
      conFailMessage: '连接失败',
      delSucMessage: '删除成功',
      delFailMessage: '删除失败',
      updateCompletedMessage: '更新成功',
      updateFaildMessage: '更新失败',
      appNotRegisterMessage: '应用未注册',
      confirmDelOperator: '确认删除'
    },
    loginOut: '退出登录',
    dropdownList: [
      {
        title: '中文',
        command: 'Chinese'
      },
      {
        title: 'English',
        command: 'English'
      }
    ]
  },
  login: {
    btnTxt: '登录',
    labelUserName: '用户名',
    labelPassword: '密码'
  },
  btn: {
    submit: '提交',
    reset: '重置',
    cancel: '取消',
    confirm: '确定'
  },
  input: {
    pUserName: '请输入用户名',
    pPaasword: '请输入密码'
  },
  registryCenter: {
    btnTxt: '添加',
    registDialog: {
      title: '添加注册中心',
      editTitle: '编辑注册中心',
      name: '注册中心名称',
      centerType: '注册中心类型',
      address: '注册中心地址',
      orchestrationName: '数据治理实例',
      namespaces: '命名空间',
      digest: '登录凭证',
      btnConfirmTxt: '确定',
      btnCancelTxt: '取消'
    },
    table: {
      operate: '操作',
      operateConnect: '连接',
      operateConnected: '已连接',
      operateDel: '删除',
      operateEdit: '编辑'
    },
    rules: {
      name: '请输入注册中心名称',
      centerType: '请选择注册中心类型',
      namespaces: '请输入命名空间',
      address: '请选输入注册中心地址',
      orchestrationName: '请输入数据治理实例名称',
      digest: '请输入登录凭证'
    }
  },
  appConfig: {
    btnTxt: '添加',
    addDialog: {
      title: '添加应用',
      appName: '应用名称',
      script: '启动脚本',
      cpu: 'CPU核数',
      memory: '占用内存(MB)',
      sampling: '作业事件采样次数(仅Daemon)',
      cacheEnable: '是否在本地缓存应用',
      appURL: '应用所在路径'
    },
    dialog: {
      editTitle: '修改应用',
      detailTitle: '应用详情'
    },
    table: {
      appName: '应用名称',
      appUrl: '应用所在路径',
      script: '启动脚本',
      operate: '操作',
      operateDetail: '详情',
      operateModify: '修改',
      operateDel: '删除',
      operateDisable: '失效',
      operateEnable: '生效'
    },
    rules: {
      appName: '请输入应用名称',
      appURL: '请输入应用所在路径',
      bootstrapScript: '请输入启动脚本',
      cpuCount: '请输入CPU核数',
      memoryMB: '请输入内存占用',
      eventTraceSamplingCount: '请输入作业事件采样次数'
    },
    tips: {
      disableSuccess: '失效成功',
      enableSuccess: '生效成功'
    }
  },
  jobConfig: {
    labelInfo: {
      addBtnText: '添加',
      jobName: '作业名称',
      appName: '应用名称',
      shardingTotalCount: '作业分片总数',
      cron: 'Cron表达式',
      description: '作业描述信息',
      status: '状态',
      jobExecutionType: '执行类型',
      jobParameter: '自定义参数',
      cpuCount: 'CPU核数',
      memoryMB: '单片作业内存(MB)',
      failover: '支持自动失效转移',
      misfire: '支持错过重执行',
      applicationContext: 'Spring配置文件相对路径及名称',
      shardingItemParameters: '分片序列号/参数对照表',
      scriptCommandLine: 'SCRIPT类型作业命令行执行脚本',
      streamingProcess: '是否流式处理数据',
      beanName: '实体名称',
      addTitle: '添加作业',
      editTitle: '修改作业',
      operate: '操作'
    },
    rules: {
      jobName: '请输入作业名称',
      appName: '请输入应用名称',
      jobExecutionType: '请选择执行类型',
      shardingTotalCount: '请输入作业分片总数',
      applicationContext: 'META-INF\\applicationContext.xml',
      shardingItemParameters: '0=a,1=b,2=c',
      cron: '请输入Cron表达式',
      cpuCount: '请输入CPU核数',
      memoryMB: '请输入单片作业内存(MB)',
      description: '请输入作业描述信息',
      status: '请选择作业状态'
    },
    statusText: {
      OK: '正常',
      DISABLED: '已失效',
      CRASHED: '已下线',
      PENDING: '等待运行',
      SHARDING_FLAG: '分片待调整'
    },
    actionText: {
      modify: '修改',
      detail: '详情',
      trigger: '触发',
      disable: '失效',
      enable: '生效',
      shutdown: '终止',
      remove: '删除'
    }
  },
  jobStatus: {
    tab: {
      running: '运行任务',
      ready: '待运行任务',
      failover: '待失效转移任务'
    },
    labelInfo: {
      id: '任务主键',
      taskName: '任务名称',
      jobName: '作业名称',
      severIp: '服务器IP',
      type: '执行类型',
      shardingItems: '分片项',
      times: '剩余执行次数',
      originalTaskId: '原任务主键',
      operate: '操作'
    }
  },
  historyTrace: {
    column: {
      jobName: '作业名称',
      taskId: '任务主键',
      serverIp: '服务器IP',
      executeSource: '执行来源',
      executeResult: '执行结果',
      failureCause: '失败原因',
      startTime: '开始时间',
      completeTime: '完成时间'
    },
    searchForm: {
      jobName: '请输入作业名称',
      taskId: '请输入任务主键',
      serverIp: '请输入服务器IP',
      startTime: '请输入开始时间',
      CompleteTime: '请输入完成时间',
      executeResult: '请选择执行结果',
      executeSuccess: '成功',
      executeFailed: '失败'
    }
  },
  historyStatus: {
    column: {
      jobName: '作业名称',
      taskId: '任务主键',
      serverIp: '服务器IP',
      executeSource: '执行来源',
      shardingItem: '分片项',
      executeType: '执行类型',
      state: '状态',
      createTime: '创建时间',
      remark: '备注'
    },
    searchForm: {
      jobName: '请输入作业名称',
      taskId: '请输入任务主键',
      serverIp: '请输入服务器IP',
      executeSource: '请选择执行来源',
      executeType: '请选择执行类型',
      startTime: '请输入创建开始时间',
      CompleteTime: '请输入创建结束时间',
      state: '选择状态',
      stateStaging: '等待运行',
      stateFailed: '运行失败',
      stateFinished: '已完成',
      stateRunning: '运行中',
      stateError: '启动失败',
      stateKilled: '主动终止'
    }
  },
  historyDashboard: {
    successAndFailCount: '作业成功/失败数',
    jobType: '作业分类',
    jobExecutionTypeJob: '作业执行类型',
    jobTaskRunningCount: '作业/任务运行数',
    currentJobsCount: '接入平台作业数',
    jobInfoForOneMinute: '一分钟作业情况',
    jobInfoForOneHour: '一小时作业情况',
    jobInfoForOneWeek: '一周作业情况',
    jobSuccessCount: '作业成功数',
    jobFailureCount: '作业失败数',
    taskRunningCount: '任务运行数',
    jobRunningCount: '作业运行数',
    success: '成功',
    failed: '失败'
  }
}
