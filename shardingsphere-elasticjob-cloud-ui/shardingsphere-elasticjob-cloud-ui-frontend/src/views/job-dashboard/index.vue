<!--
  - Licensed to the Apache Software Foundation (ASF) under one or more
  - contributor license agreements.  See the NOTICE file distributed with
  - this work for additional information regarding copyright ownership.
  - The ASF licenses this file to You under the Apache License, Version 2.0
  - (the "License"); you may not use this file except in compliance with
  - the License.  You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
<el-row class="job-dashboard">
  <el-row :gutter="16" class="row">
    <el-col :span="16">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>Job success / failure</span>
        </div>
        <el-row>
          <el-col :span="8" class="col">
            <h2>一分钟作业情况</h2>
            <v-chart :options="lastMinute"/>
          </el-col>
          <el-col :span="8" class="col">
            <h2>一小时作业情况</h2>
            <v-chart :options="lastHour"/>
          </el-col>
          <el-col :span="8" class="col">
            <h2>一周作业情况</h2>
            <v-chart :options="lastWeek"/>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="8">
     <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>作业分类</span>
      </div>
      <el-row>
        <el-col class="col">
          <h2>执行分类</h2>
          <v-chart :options="executionType"/>
        </el-col>
      </el-row>
     </el-card>
    </el-col>
  </el-row>
  <el-row class="row">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>作业成功/失败数</span>
      </div>
      <el-row>
        <v-chart :options="line"/>
      </el-row>
    </el-card>
  </el-row>
  <el-row class="row">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>作业/任务运行数</span>
      </div>
      <el-row>
        <v-chart :options="line"/>
      </el-row>
    </el-card>
  </el-row>
  <el-row class="row">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>接入平台作业数</span>
      </div>
      <el-row>
        <v-chart :options="register"/>
      </el-row>
    </el-card>
  </el-row>
</el-row>
</template>

<script>
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/pie'
import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/legend'
import moment from 'moment'

import API from './api'
export default {
  name: 'JobDashboard',
  components: {
    'v-chart': ECharts
  },
  data () {
    return {
      lastMinute: {},
      lastHour: {},
      lastWeek: {},
      executionType: {},
      register: {},
      running: {},
      result: {}
  },
  created() {
    this.getTasksPeriod()
    this.getJobsExecutionType()
    this.getJobsRegister()
    this.getJobsRunning()
    this.getTasksResults()
  },
  methods: {
    getTasksResults() {
      const params = {
        since: 'last24hours'
      }
      API.getTasksResults(params).then(res => {
        const xAxis = []
        const series1 = []
        const series2 = []
        res.model && res.model.forEach(item => {
          xAxis.push(moment(item.statisticsTime).format('MM-DD'))
          series1.push(item.successCount)
          series2.push(item.failedCount)
        })
        this.result = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['successCount', 'failedCount']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: 'successCount',
            type: 'line',
            data: series1
          },{
            name: 'failedCount',
            type: 'line',
            data: series2
          }]
        }
      })
    }
    getJobsRunning() {
      const params = {
        since: 'lastWeek'
      }
      API.getJobsRunning(params).then(res => {
        const xAxis = []
        const series = []
        res.model && res.model.forEach(item => {
          xAxis.push(moment(item.statisticsTime).format('MM-DD'))
          series.push(item.runningCount)
        })
        this.running = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['runningCount']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: 'runningCount',
            type: 'line',
            data: series
          }]
        }
      })
    }
    getJobsRegister() {
      API.getJobsRegister().then(res => {
        const xAxis = []
        const series = []
        res.model && res.model.forEach(item => {
          xAxis.push(moment(item.statisticsTime).format('MM-DD'))
          series.push(item.registeredCount)
        })
        this.register = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['registeredCount']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: 'registeredCount',
            type: 'line',
            data: series
          }]
        }
      })
    },
    getJobsExecutionType() {
      API.getJobsExecutionType().then(res => {
        const { model } = res
        this.executionType = {
          series: [
            {
              name: 'jobtype',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                {value: model.transientJobCount, name: 'TRANSI'},
                {value: model.daemonJobCount, name: 'DAEMON'}
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
      })
    },
    getTasksPeriod() {
      API.getTasksPeriod('lastMinute').then(res => {
        const { model } = res
        this.lastMinute = {
          series: [
            {
              name: 'lastMinute',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                {value: model.successCount, name: 'Success'},
                {value: model.failedCount, name: 'Failed'}
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
      })
      API.getTasksPeriod('lastHour').then(res => {
        const { model } = res
        this.lastHour = {
          series: [
            {
              name: 'lastHour',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                {value: model.successCount, name: '成功'},
                {value: model.failedCount, name: '失败'}
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
      })
      API.getTasksPeriod('lastWeek').then(res => {
        const { model } = res
        this.lastWeek = {
          series: [
            {
              name: 'lastWeek',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                {value: model.successCount, name: '成功'},
                {value: model.failedCount, name: '失败'}
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.job-dashboard {
  padding: 0 16px;
  h2 {
    font-size: 14px;
  }
  .col {
    text-align: center;
  }
  .row {
    margin-top: 16px;
  }
}
.echarts {
  height: 300px;
  width: 100%;
}
</style>
