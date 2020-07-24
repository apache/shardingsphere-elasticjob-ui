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
        title: '全局配置',
        child: [
          {
            title: '注册中心配置',
            href: '/registry-center'
          },
          {
            title: '应用配置',
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
  }
}
