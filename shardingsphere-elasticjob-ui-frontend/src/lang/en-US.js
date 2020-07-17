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
    home: 'Home',
    menuData: [
      {
        title: 'Global settings',
        child: [
          {
            title: 'Registry center',
            href: '/registry-center'
          },
          {
            title: 'Event trace data source',
            href: '/data-source'
          }
        ]
      },
      {
        title: 'Job operation',
        child: [
          {
            title: 'Job dimension',
            href: '/operation-jobs'
          },
          {
            title: 'Server dimension',
            href: '/operation-servers'
          }
        ]
      },
      {
        title: 'Job history',
        child: [
          {
            title: 'Job trace',
            href: '/history-trace'
          },
          {
            title: 'History status',
            href: '/history-status'
          }
        ]
      },
      {
        title: 'Help',
        href: '/job-help'
      }
    ],
    connected: 'Connected',
    connection: 'Connection',
    del: 'Delete',
    notify: {
      title: 'Prompt',
      addSucMessage: 'Add Succeeded',
      editSucMessage: 'Edit Succeeded',
      conSucMessage: 'Connection Succeeded',
      conFailMessage: 'Connection Failed',
      delSucMessage: 'Delete Succeeded',
      delFailMessage: 'Delete Failed',
      updateCompletedMessage: 'Update Completed',
      updateFaildMessage: 'Update Faild',
      confirmDelOperator: 'Confirm delete'
    },
    loginOut: 'Sign Out',
    dropdownList: [
      {
        title: '中文',
        command: 'zh-CN'
      },
      {
        title: 'English',
        command: 'en-US'
      }
    ]
  },
  login: {
    btnTxt: 'Login',
    labelUserName: 'Username',
    labelPassword: 'Password'
  },
  btn: {
    submit: 'Submit',
    reset: 'Reset',
    cancel: 'Cancel',
    confirm: 'Confirm'
  },
  input: {
    pUserName: 'Please enter user name',
    pPaasword: 'Please enter your password'
  },
  registryCenter: {
    btnTxt: 'ADD',
    registDialog: {
      title: 'Add a registry center',
      editTitle: 'Edit registry center',
      name: 'Name',
      centerType: 'Instance Type',
      address: 'Zookeeper address',
      orchestrationName: 'Orchestration Name',
      namespaces: 'Namespace',
      digest: 'Digest',
      btnConfirmTxt: 'Confirm',
      btnCancelTxt: 'Cancel'
    },
    table: {
      operate: 'Operate',
      operateConnect: 'Connect',
      operateConnected: 'Connected',
      operateDel: 'Del',
      operateEdit: 'Edit'
    },
    rules: {
      name: 'Please enter the name of the registration center',
      address: 'Please enter the registration center Address',
      namespaces: 'Please enter a Namespace',
      centerType: 'Please select a Center Type',
      orchestrationName: 'Please enter a Orchestration Name',
      digest: 'Please enter a digest'
    }
  },
  dataSource: {
    btnTxt: 'ADD',
    addDialog: {
      title: 'Add a data source',
      name: 'Name',
      driver: 'Driver',
      url: 'Url',
      username: 'Username',
      password: 'Password',
      btnConfirmTxt: 'Confirm',
      btnCancelTxt: 'Cancel',
      btnConnectTestTxt: 'Test connect'
    },
    table: {
      operate: 'Operate',
      operateConnect: 'Connect',
      operateConnected: 'Connected',
      operateDel: 'Del',
      operateEdit: 'Edit'
    },
    rules: {
      name: 'Please enter the name of the data source',
      driver: 'Please enter the driver of the data source',
      url: 'Please enter the url of the data source',
      username: 'Please enter the username of the data source',
      password: 'Please enter the password of the data source'
    }
  },
  runtimeStatus: {
    serviceNode: 'Service Node',
    slaveDataSourceName: 'Slave DataSource Info',
    dataSource: {
      schema: 'Schema',
      masterDataSourceName: 'Master DataSource Name',
      slaveDataSourceName: 'Slave DataSource Name'
    },
    instance: {
      instanceId: 'Instance Id',
      serverIp: 'Server Ip'
    }
  },
  ruleConfig: {
    form: {
      inputPlaceholder: 'Please enter content'
    },
    schema: {
      title: 'Add Schema',
      name: 'Name',
      ruleConfig: 'Rule Config',
      dataSourceConfig: 'Data Source Config'
    },
    schemaRules: {
      name: 'Please enter the name of the schema',
      ruleConfig: 'Please enter the rule config of the schema',
      dataSourceConfig: 'Please enter the data source config of the schema'
    },
    radioBtn: {
      schema: 'Schema',
      authentication: 'Authentication',
      props: 'Props'
    }
  },
  dataScaling: {
    btnTxt: 'ADD',
    tableList: {
      jobId: 'jobId',
      jobName: 'jobName',
      status: 'status',
      operate: 'operate',
      operateStop: 'stop',
      operateSee: 'see'
    },
    registDialog: {
      title: 'Add a job',
      source: 'Source',
      target: 'Target',
      jobCount: 'JobCount',
      jobCountPlaceholder: 'Please enter jobCount',
      username: 'Username',
      usernamePlaceholder: 'Please enter username',
      password: 'Password',
      passwordPlaceholder: 'Please enter password',
      url: 'Url',
      urlPlaceholder: 'Please enter url',
      btnConfirmTxt: 'Confirm',
      btnCancelTxt: 'Cancel'
    },
    rules: {
      source: 'Please select the source of the registration center',
      target: 'Please select a target',
      serviceUrl: 'ServiceUrl must fill'
    },
    notify: {
      title: 'Prompt',
      conSucMessage: 'Add Succeeded',
      conFailMessage: 'Add Failed',
      delSucMessage: 'Delete Succeeded',
      delFailMessage: 'Delete Failed'
    },
    serviceDialog: {
      title: 'Data Scaling Setting',
      serviceName: 'Service Name',
      serviceUrl: 'Service Url',
      serviceNamePlaceholder: 'Please enter serviceName',
      serviceUrlPlaceholder: 'Please enter serviceUrl'
    }
  },
  clusterState: {
    legendLabel: {
      onLine: 'ONLINE',
      offLine: 'OFFLINE',
      disabled: 'DISABLED',
      unknown: 'UNKNOWN'
    }
  }
}
