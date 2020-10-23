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
        title: 'APP',
        child: [
          /* {
            title: 'Registry center',
            href: '/registry-center'
          },*/
          {
            title: 'Config',
            href: '/app-config'
          }
        ]
      },
      {
        title: 'Job',
        child: [
          {
            title: 'Config',
            href: '/job-config'
          },
          {
            title: 'Status',
            href: '/job-status'
          }
        ]
      },
      {
        title: 'Job history',
        child: [
          {
            title: 'History dashboard',
            href: '/history-dashboard'
          },
          {
            title: 'Job trace',
            href: '/history-trace'
          },
          {
            title: 'History status',
            href: '/history-status'
          }
        ]
      }
    ],
    connected: 'Connected',
    connection: 'Connection',
    del: 'Delete',
    notify: {
      title: 'Prompt',
      addSucMessage: 'Add Succeeded',
      editSucMessage: 'Edit Succeeded',
      actionSucMessage: 'Operation Succeeded',
      conSucMessage: 'Connection Succeeded',
      conFailMessage: 'Connection Failed',
      delSucMessage: 'Delete Succeeded',
      delFailMessage: 'Delete Failed',
      updateCompletedMessage: 'Update Completed',
      updateFaildMessage: 'Update Faild',
      appNotRegisterMessage: 'App name is unregistered',
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
  appConfig: {
    btnTxt: 'ADD',
    addDialog: {
      title: 'Add app',
      appName: 'App name',
      script: 'Bootstrap script',
      cpu: 'CPU count',
      memory: 'Memory(MB)',
      sampling: 'Event trace sampling count',
      cacheEnable: 'App cache enable',
      appURL: 'App URL'
    },
    dialog: {
      editTitle: 'Modify app',
      detailTitle: 'App detail'
    },
    table: {
      appName: 'App name',
      appUrl: 'App URL',
      script: 'Bootstrap script',
      operate: 'Operate',
      operateDetail: 'Detail',
      operateModify: 'Update',
      operateDisable: 'Disable',
      operateEnable: 'Enable',
      operateDel: 'Del'
    },
    rules: {
      appName: 'Please input app name',
      appURL: 'Please input app url',
      bootstrapScript: 'Please input bootstrap script',
      cpuCount: 'Please input cpu count',
      memoryMB: 'Please input memory',
      eventTraceSamplingCount: 'Please input event trace sampling count'
    },
    tips: {
      disableSuccess: 'Disable Success',
      enableSuccess: 'Enable Success'
    }
  },
  jobConfig: {
    labelInfo: {
      addBtnText: 'ADD',
      jobName: 'Job name',
      appName: 'App name',
      shardingTotalCount: 'Sharding total count',
      cron: 'Crontab',
      description: 'Description',
      status: 'Status',
      jobExecutionType: 'Execution type',
      jobParameter: 'Job parameter',
      cpuCount: 'CPU count',
      memoryMB: 'Memory(MB)',
      failover: 'Failover',
      misfire: 'Misfire',
      applicationContext: 'Application context',
      shardingItemParameters: 'Sharding item parameters',
      scriptCommandLine: 'Script command line',
      streamingProcess: 'Streaming process',
      beanName: 'Bean name',
      addTitle: 'Add job',
      editTitle: 'Update job',
      operate: 'Operate'
    },
    rules: {
      jobName: 'Please enter the Job name',
      appName: 'Please input app name',
      jobExecutionType: 'Please input Execution type',
      shardingTotalCount: 'Please enter the Sharding total count',
      applicationContext: 'Please input META-INF\\applicationContext.xml',
      shardingItemParameters: 'Please input 0=a,1=b,2=c',
      cron: 'Please enter the Crontab',
      cpuCount: 'Please input CPU count',
      memoryMB: 'Please input Memory(MB)',
      description: 'Please enter the Description',
      status: 'Please enter the Status'
    },
    statusText: {
      OK: 'Enabled',
      DISABLED: 'Disabled',
      CRASHED: 'Crashed',
      PENDING: 'Staging',
      SHARDING_FLAG: 'SHARDING_FLAG'
    },
    actionText: {
      modify: 'Update',
      detail: 'Detail',
      trigger: 'Trigger',
      disable: 'Disable',
      enable: 'Enable',
      shutdown: 'Shutdown',
      remove: 'Remove'
    }
  },
  jobStatus: {
    tab: {
      running: 'Running tasks',
      ready: 'Ready jobs',
      failover: 'Failover tasks'
    },
    labelInfo: {
      id: 'Task ID',
      taskName: 'Task name',
      jobName: 'Job name',
      severIp: 'Server IP',
      type: 'Job execution type',
      shardingItems: 'Sharding item',
      times: 'Remaining execution times',
      originalTaskId: 'Original task ID',
      operate: 'Operate'
    }
  },
  historyTrace: {
    column: {
      jobName: 'Job name',
      taskId: 'Task ID',
      serverIp: 'Server IP',
      executeSource: 'Execute source',
      executeResult: 'Execute result',
      failureCause: 'Failure reason',
      startTime: 'Start time',
      completeTime: 'Complete time'
    },
    searchForm: {
      jobName: 'Job name',
      taskId: 'Task ID',
      serverIp: 'Server ip',
      startTime: 'Start time',
      CompleteTime: 'Complete time',
      executeResult: 'Select execute result',
      executeSuccess: 'Success',
      executeFailed: 'Failure'
    }
  },
  historyStatus: {
    column: {
      jobName: 'Job name',
      taskId: 'Task ID',
      serverIp: 'Server IP',
      executeSource: 'Execute source',
      shardingItem: 'Sharding item',
      executeType: 'Execute type',
      state: 'Status',
      createTime: 'Creation time',
      remark: 'Comments'
    },
    searchForm: {
      jobName: 'Job name',
      taskId: 'Task ID',
      serverIp: 'Server IP',
      executeSource: 'Execute source',
      executeType: 'Execute type',
      startTime: 'Creation start time',
      CompleteTime: 'Creation end time',
      state: 'Select state',
      stateStaging: 'Staging',
      stateFailed: 'Failed',
      stateFinished: 'Finished',
      stateRunning: 'Running',
      stateError: 'Error',
      stateKilled: 'Killed'
    }
  },
  historyDashboard: {
    successAndFailCount: 'Success and failure count',
    jobType: 'Job type',
    jobExecutionTypeJob: 'execution type',
    jobTaskRunningCount: 'Job and task running count',
    currentJobsCount: 'Registry jobs count',
    jobInfoForOneMinute: 'Job info for one minute',
    jobInfoForOneHour: 'Job info for one hour',
    jobInfoForOneWeek: 'Job info for one week',
    jobSuccessCount: 'Job success count',
    jobFailureCount: 'Job failure count',
    taskRunningCount: 'Task running count',
    jobRunningCount: 'Job running count',
    success: 'Success',
    failed: 'Failed'
  }
}
