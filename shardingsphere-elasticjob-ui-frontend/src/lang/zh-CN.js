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
            title: '事件追踪数据源配置',
            href: '/data-source'
          }
        ]
      },
      {
        title: '作业操作',
        child: [
          {
            title: '作业维度',
            href: '/operation-jobs'
          },
          {
            title: '服务器维度',
            href: '/operation-servers'
          }
        ]
      },
      {
        title: '作业历史',
        child: [
          {
            title: '历史轨迹',
            href: '/history-trace'
          },
          {
            title: '历史状态',
            href: '/history-status'
          }
        ]
      },
      {
        title: '帮助',
        href: '/job-help'
      }
    ],
    connect: '已连接',
    connection: '连接',
    del: '删除',
    notify: {
      title: '提示',
      addSucMessage: '添加成功',
      editSucMessage: '修改成功',
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
      operateConnected: '已激活',
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
  dataSource: {
    btnTxt: '添加',
    addDialog: {
      title: '添加事件追踪数据源',
      name: '数据源名称',
      driver: '数据源驱动',
      url: '数据源连接地址',
      username: '数据源用户名',
      password: '数据源密码',
      btnConfirmTxt: '确定',
      btnCancelTxt: '取消',
      btnConnectTestTxt: '测试连接'
    },
    table: {
      operate: '操作',
      operateConnect: '连接',
      operateConnected: '已连接',
      operateDel: '删除',
      operateEdit: '编辑'
    },
    rules: {
      name: '请输入数据源名称',
      driver: '请输入数据源驱动',
      url: '请输入数据源连接地址',
      username: '请输入数据源用户名',
      password: '请输入数据源密码'
    }
  },
  help: {
    design_concept_title: '设计理念',
    design_concept_info_1: '本控制台和Elastic Job并无直接关系，是通过读取Elastic Job的注册中心数据展现作业状态，或更新注册中心数据修改全局配置。',
    design_concept_info_2: '控制台只能控制作业本身是否运行，但不能控制作业进程的启停，因为控制台和作业本身服务器是完全分布式的，控制台并不能控制作业服务器。',
    major_features_title: '主要功能',
    major_features_info_1: '查看作业以及服务器状态',
    major_features_info_2: '快捷的修改以及删除作业设置',
    major_features_info_3: '启用和禁用作业',
    major_features_info_4: '跨注册中心查看作业',
    major_features_info_5: '查看作业运行轨迹和运行状态',
    unsupported_title: '不支持项',
    unsupported_info: '添加作业。因为作业都是在首次运行时自动添加，使用控制台添加作业并无必要。直接在作业服务器启动包含Elastic Job的作业进程即可。',
  }
}
