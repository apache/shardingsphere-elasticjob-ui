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
  <el-row class="box-card">
    <el-form :model="searchForm" class="demo-form-inline">
      <el-form-item>
        <el-col :span="4">
          <el-button
            type="info"
            icon="el-icon-arrow-left"
            @click="goBack">Back</el-button>
        </el-col>
        <el-col :span="14">
          &nbsp;
        </el-col>
        <el-col :span="6">
          <el-input
            v-model="searchForm.jobName"
            placeholder="Reload"
            clearable
            autocomplete="off"
            @clear="search"
            @change="search">
            <i
              slot="prefix"
              class="el-input__icon el-icon-search"></i>
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="search"></el-button>
          </el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <div class="table-wrap">
      <el-form
        ref="detailModel"
        :model="detailModel"
        label-width="40px">

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.jobName') }}
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="detailModel.jobName"
              :disabled="viewMode"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.cron') }}
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="7">
            <el-input
              :disabled="viewMode"
              v-model="detailModel.cron"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.appName') }}
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="detailModel.appName"
              :disabled="viewMode"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.jobExecutionType') }}
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="7">
            <el-select
              v-model="detailModel.jobExecutionType"
              :disabled="viewMode">
              <el-option
                v-for="item in jobExecutionTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.shardingTotalCount') }}
          </el-col>
          <el-col :span="8">
            <el-input-number
              :disabled="viewMode"
              v-model="detailModel.shardingTotalCount"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.jobParameter') }}
          </el-col>
          <el-col :span="8">
            <el-input
              :disabled="viewMode"
              v-model="detailModel.jobParameter"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.cpuCount') }}
          </el-col>
          <el-col :span="8">
            <el-input-number
              v-model="detailModel.cpuCount"
              :disabled="viewMode"
              :precision="3"
              :step="0.001"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.memoryMB') }}
          </el-col>
          <el-col :span="8">
            <el-input-number
              v-model="detailModel.memoryMB"
              :disabled="viewMode"
              :min="1"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.failover') }}
          </el-col>
          <el-col :span="8">
            <el-checkbox
              v-model="detailModel.failover"
              :disabled="viewMode">{{ $t('jobConfig.labelInfo.failover') }}</el-checkbox>
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.misfire') }}
          </el-col>
          <el-col :span="8">
            <el-checkbox
              v-model="detailModel.misfire"
              :disabled="viewMode">{{ $t('jobConfig.labelInfo.misfire') }}</el-checkbox>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.applicationContext') }}
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="detailModel.applicationContext"
              :disabled="viewMode"
              type="textarea"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.shardingItemParameters') }}
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="detailModel.shardingItemParameters"
              :disabled="viewMode"
              type="textarea"
              title="分片序列号和参数用等号分隔，多个键值对用逗号分隔，类似map。分片序列号从0开始，不可大于或等于作业分片总数。如：0=a,1=b,2=c"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.beanName') }}
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="detailModel.beanName"
              :disabled="viewMode"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.streamingProcess') }}
          </el-col>
          <el-col :span="8">
            <el-checkbox
              v-model="detailModel.streamingProcess"
              :disabled="viewMode"
              title="DATAFLOW类型作业，是否流式处理数据如果流式处理数据, 则fetchData不返回空结果将持续执行作业，如果非流式处理数据, 则处理数据完成后作业结束。"
            >
              {{ $t('jobConfig.labelInfo.streamingProcess') }}
            </el-checkbox>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.scriptCommandLine') }}
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="detailModel.scriptCommandLine"
              :disabled="viewMode"
              title="SCRIPT类型作业命令行执行脚本"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.description') }}
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="detailModel.description"
              :disabled="viewMode"
              type="textarea"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

      </el-form>
    </div>
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../../job-config/api'
export default {
  name: 'OperationJobs',
  data() {
    return {
      viewMode: true,
      jobName: '',
      searchForm: {
        jobName: ''
      },
      detailModel: {},
      jobExecutionTypeOptions: [{
        value: 'DAEMON',
        label: 'DAEMON'
      }, {
        value: 'TRANSIENT',
        label: 'TRANSIENT'
      }]
    }
  },
  created() {
    this.jobName = this.$route.params.jobName ||
      localStorage.getItem('/job-config/job-detail/jobName')
    if (!this.jobName) {
      this.goBack()
      return
    }
    this.search()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    goBack() {
      localStorage.removeItem('/job-config/job-detail/jobName')
      this.$router.replace({ path: '/job-config' })
    },
    getJobDetail() {
      var params = {
        jobName: this.jobName
      }
      API.getJobDetail(params).then(res => {
        const data = clone(res.model)
        data.props = data.props || {}
        if (data.props['streaming.process'] === 'true') {
          data.streamingProcess = true
        } else if (!data.props['streaming.process'] || data.props['streaming.process'] === 'false') {
          data.streamingProcess = false
        } else {
          data.streamingProcess = true
        }

        this.detailModel = data
      })
    },
    search() {
      this.getJobDetail()
    }
  }
}
</script>
<style lang='scss' scoped>
.btn-group {
  margin-bottom: 20px;
}
.pagination {
  float: right;
  margin: 10px -10px 10px 0;
}
</style>
