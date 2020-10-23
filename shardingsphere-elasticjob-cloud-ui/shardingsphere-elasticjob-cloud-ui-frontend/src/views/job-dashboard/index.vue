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
            <span>{{ $t("historyDashboard.successAndFailCount") }}</span>
          </div>
          <el-row>
            <el-col :span="8" class="col">
              <h2>{{ $t("historyDashboard.jobInfoForOneMinute") }}</h2>
              <v-chart :options="lastMinute"/>
            </el-col>
            <el-col :span="8" class="col">
              <h2>{{ $t("historyDashboard.jobInfoForOneHour") }}</h2>
              <v-chart :options="lastHour"/>
            </el-col>
            <el-col :span="8" class="col">
              <h2>{{ $t("historyDashboard.jobInfoForOneWeek") }}</h2>
              <v-chart :options="lastWeek"/>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>{{ $t("historyDashboard.jobType") }}</span>
          </div>
          <el-row>
            <el-col class="col">
              <h2>{{ $t("historyDashboard.jobExecutionTypeJob") }}</h2>
              <v-chart :options="executionType"/>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row class="row">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>{{ $t("historyDashboard.successAndFailCount") }}</span>
        </div>
        <el-row>
          <v-chart :options="result"/>
        </el-row>
      </el-card>
    </el-row>
    <el-row class="row">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>{{ $t("historyDashboard.jobTaskRunningCount") }}</span>
        </div>
        <el-row>
          <v-chart :options="running"/>
        </el-row>
      </el-card>
    </el-row>
    <el-row class="row">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>{{ $t("historyDashboard.currentJobsCount") }}</span>
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
  data() {
    return {
      color: ['rgb(105, 167, 225)', 'rgb(120, 230, 117)'],
      lastMinute: {},
      lastHour: {},
      lastWeek: {},
      executionType: {},
      register: {},
      running: {},
      result: {}
    }
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
        const series1 = []
        const series2 = []
        if (res.model && res.model.length) {
          res.model.forEach(item => {
            series1.push([moment(item.statisticsTime).format('HH:00'), item.successCount])
            series2.push([moment(item.statisticsTime).format('HH:00'), item.failedCount])
          })
        }
        this.result = {
          color: this.color,
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: [this.$t('historyDashboard').jobSuccessCount, this.$t('historyDashboard').jobFailureCount],
            bottom: 0
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '9%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: this.$t('historyDashboard').jobSuccessCount,
            type: 'line',
            data: series1
          }, {
            name: this.$t('historyDashboard').jobFailureCount,
            type: 'line',
            data: series2
          }]
        }
      })
    },
    getJobsRunning() {
      const params = {
        since: 'lastWeek'
      }
      API.getJobsRunning(params).then(res => {
        API.getTasksRunning(params).then(resp => {
          if (resp.model && res.model.length) {
            const jobSeries = []
            const taskSeries = []
            if (res.model && res.model.length) {
              res.model && res.model.forEach(item => {
                jobSeries.push([moment(item.statisticsTime).format('MM-DD'), item.runningCount])
              })
            }
            if (resp.model && resp.model.length) {
              resp.model && resp.model.forEach(item => {
                taskSeries.push([moment(item.statisticsTime).format('MM-DD'), item.runningCount])
              })
            }
            this.running = {
              color: this.color,
              tooltip: {
                trigger: 'axis'
              },
              legend: {
                data: [this.$t('historyDashboard').taskRunningCount, this.$t('historyDashboard').jobRunningCount],
                bottom: 0
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: {
                type: 'category',
                boundaryGap: false
              },
              yAxis: {
                type: 'value'
              },
              series: [{
                name: this.$t('historyDashboard').taskRunningCount,
                type: 'line',
                data: taskSeries
              }, {
                name: this.$t('historyDashboard').jobRunningCount,
                type: 'line',
                data: jobSeries
              }]
            }
          }
        })
      })
    },
    getJobsRegister() {
      API.getJobsRegister().then(res => {
        const series = []
        if (res.model && res.model.length) {
          res.model && res.model.forEach(item => {
            series.push([moment(item.statisticsTime).format('HH:mm'), item.registeredCount])
          })
        }
        this.register = {
          color: this.color,
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: [this.$t('historyDashboard').currentJobsCount],
            bottom: 0
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: this.$t('historyDashboard').currentJobsCount,
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
          color: this.color,
          tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>{c}'
          },
          series: [
            {
              name: 'jobtype',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                { value: model.transientJobCount, name: 'TRANSI', label: { formatter: '{b}:\n' + this.percentage(model.transientJobCount, model.transientJobCount + model.daemonJobCount) }},
                { value: model.daemonJobCount, name: 'DAEMON', label: { formatter: '{b}:\n' + this.percentage(model.daemonJobCount, model.transientJobCount + model.daemonJobCount) }}
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
        model.successCount = 40
        model.failedCount = 60
        this.lastMinute = {
          color: this.color,
          tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>{c}'
          },
          series: [
            {
              name: 'lastMinute',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                { value: model.successCount, name: this.$t('historyDashboard').success, label: { formatter: '{b}:\n' + this.percentage(model.successCount, model.successCount + model.failedCount) }},
                { value: model.failedCount, name: this.$t('historyDashboard').failed, label: { formatter: '{b}:\n' + this.percentage(model.failedCount, model.successCount + model.failedCount) }}
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
          color: this.color,
          tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>{c}'
          },
          series: [
            {
              name: 'lastHour',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                { value: model.successCount, name: this.$t('historyDashboard').success, label: { formatter: '{b}:\n' + this.percentage(model.successCount, model.successCount + model.failedCount) }},
                { value: model.failedCount, name: this.$t('historyDashboard').failed, label: { formatter: '{b}:\n' + this.percentage(model.failedCount, model.successCount + model.failedCount) }}
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
          color: this.color,
          tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>{c}'
          },
          series: [
            {
              name: 'lastWeek',
              type: 'pie',
              radius: '35%',
              center: ['50%', '50%'],
              data: [
                { value: model.successCount, name: this.$t('historyDashboard').success, label: { formatter: '{b}:\n' + this.percentage(model.successCount, model.successCount + model.failedCount) }},
                { value: model.failedCount, name: this.$t('historyDashboard').failed, label: { formatter: '{b}:\n' + this.percentage(model.failedCount, model.successCount + model.failedCount) }}
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
    percentage(value, count) {
      if (value === 0) {
        return '0.0%'
      }
      return Number((value / count) * 100).toFixed(1) + '%'
    }
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
