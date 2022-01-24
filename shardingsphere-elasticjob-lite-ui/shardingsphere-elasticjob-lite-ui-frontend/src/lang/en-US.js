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
    noRegistry: 'No connected registry center',
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
    confirm: 'Confirm',
    add: 'Add',
    remove: 'Remove'
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
  operationJobs: {
    labelInfo: {
      jobName: 'Job name',
      shardingTotalCount: 'Sharding total count',
      cron: 'Crontab',
      description: 'Description',
      status: 'Status',
      jobParameter: 'Job parameter',
      maxTimeDiffSeconds: 'Max time diff seconds',
      reconcileIntervalMinutes: 'Reconcile interval minutes',
      monitorExecution: 'Monitor execution',
      failover: 'Failover',
      misfire: 'Misfire',
      streamingProcess: 'Streaming process',
      shardingItemParameters: 'Sharding item parameters',
      jobShardingStrategyType: 'Job sharding strategy type',
      jobExecutorServiceHandlerType: 'Job thread pool handler type',
      jobErrorHandlerType: 'Job error handler type',
      item: 'Sharding item',
      serverIp: 'Server IP',
      instanceId: 'PID',
      editTitle: 'Update job',
      props: 'Job properties',
      prop: {
        name: 'Property name',
        value: 'Property value'
      }
    },
    table: {
      operate: 'Operation'
    },
    rules: {
      jobName: 'Please enter the Job name',
      shardingTotalCount: 'Please enter the Sharding total count',
      cron: 'Please enter the Crontab',
      description: 'Please enter the Description',
      status: 'Please enter the Status'
    },
    statusText: {
      OK: 'OK',
      DISABLED: 'Disabled',
      CRASHED: 'Crashed',
      PENDING: 'Staging',
      RUNNING: 'Running',
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
    },
    actionConfirm: {
      shutdown: 'Are you sure to shutdown the job?'
    }
  },
  operationServers: {
    labelInfo: {
      serverIp: 'Server IP',
      instancesNum: 'Instance count',
      instanceCount: 'Instance count',
      jobsNum: 'Job num',
      disabledJobsNum: 'Disabled jobs num',
      jobName: 'Job name',
      status: 'Status',
      operate: 'Operation'
    },
    statusText: {
      OK: 'Enabled',
      DISABLED: 'Disabled',
      CRASHED: 'Crashed',
      PENDING: 'Staging',
      SHARDING_FLAG: 'SHARDING_FLAG'
    },
    actionText: {
      detail: 'Detail',
      disable: 'Disable',
      enable: 'Enable',
      shutdown: 'Shutdown',
      dump: 'Dump',
      remove: 'Remove'
    },
    dumpText: {
      title: 'Dump Job',
      jobName: 'Job Name',
      dumpPort: 'Dump Port',
      dumpBtn: 'Dump',
      cancel: 'Cancel',
      copy: 'Copy'
    },
    actionConfirm: {
      shutdown: 'Are you sure to shutdown the server?'
    }
  },
  historyTrace: {
    column: {
      jobName: 'Job name',
      serverIp: 'Server IP',
      shardingItem: 'Sharding item',
      executeResult: 'Execute result',
      failureCause: 'Failure reason',
      startTime: 'Start time',
      completeTime: 'Complete time'
    },
    searchForm: {
      jobName: 'Job name',
      serverIp: 'Server ip',
      startTimeRange: 'Start time range',
      startTimeFrom: 'Start time from',
      startTimeTo: 'Start time to',
      executeResult: 'Select execute result',
      executeSuccess: 'Success',
      executeFailed: 'Failure'
    }
  },
  historyStatus: {
    column: {
      jobName: 'Job name',
      shardingItem: 'Sharding item',
      state: 'Status',
      createTime: 'Creation time',
      remark: 'Comments'
    },
    searchForm: {
      jobName: 'Job name',
      creationTimeRange: 'Creation time range',
      creationTimeFrom: 'Creation time from',
      creationTimeTo: 'Creation time to',
      state: 'Select state',
      stateStaging: 'Staging',
      stateFailed: 'Failed',
      stateFinished: 'Finished',
      stateRunning: 'Running',
      stateError: 'Error',
      stateKilled: 'Killed'
    }
  },
  help: {
    design_concept_title: 'Design concept',
    design_concept_info_1: 'Console is not related to Elastic Job, it just reading data from registry center and showing the status of jobs, or updating data to registry center which will change the configuration.',
    design_concept_info_2: "Console can operate lifecycle for jobs, such as enable and disable, but can not the start and stop job's process, because of console server and job servers are completely distributed, console can not control the job servers.",
    major_features_title: 'Major features',
    major_features_info_1: 'View status of jobs and servers',
    major_features_info_2: 'Quick update and delete jobs',
    major_features_info_3: 'Disable and enable Jobs',
    major_features_info_4: 'Multiple registry centers supported',
    major_features_info_5: 'Trace jobs execute history',
    unsupported_title: 'Unsupported',
    unsupported_info: 'Add job. Because of job is added at first running time automatically, it is unnecessary to add job from console. So just start the job app.'
  }
}
