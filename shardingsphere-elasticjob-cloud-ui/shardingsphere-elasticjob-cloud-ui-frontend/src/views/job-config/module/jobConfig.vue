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
            class="btn-plus"
            type="primary"
            icon="el-icon-plus"
            @click="handleAdd">{{ $t("jobConfig.labelInfo.addBtnText") }}</el-button>
        </el-col>
        <el-col :span="14">
          &nbsp;
        </el-col>
        <el-col :span="6">
          <el-input
            v-model="searchForm.jobName"
            placeholder="Search"
            clearable
            autocomplete="off"
            @clear="search"
            @change="search">
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="search"></el-button>
          </el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <div class="table-wrap">
      <el-table
        :data="tableData"
        stripe
        border
        style="width: 100%">
        <el-table-column
          v-for="(item, index) in column"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :width="item.width">
          <template slot-scope="scope">
            <span>{{ scope.row[item.prop] }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('jobConfig.labelInfo.operate')"
          fixed="right"
          width="300">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                size="mini"
                type="info"
                plain
                @click="handleDetail(scope.row)">{{ $t("jobConfig.actionText.detail") }}</el-button>
              <el-button
                size="mini"
                type="primary"
                plain
                @click="handleModify(scope.row)">{{ $t("jobConfig.actionText.modify") }}</el-button>
              <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                plain
                @click="handlerRemove(scope.row)">{{ $t("jobConfig.actionText.remove") }}</el-button>
              <el-button
                v-if="scope.row.disabled"
                size="mini"
                type="success"
                plain
                @click="handleEnable(scope.row)">{{ $t("jobConfig.actionText.enable") }}</el-button>
              <el-button
                v-if="!scope.row.disabled"
                size="mini"
                type="warning"
                plain
                @click="handleDisable(scope.row)">{{ $t("jobConfig.actionText.disable") }}</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          :total="total"
          :current-page="currentPage"
          background
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
    <el-dialog
      :title="editTitle"
      :visible.sync="modifyDialogVisible"
      width="1010px">
      <el-form ref="editForm" :model="editForm" :rules="rules" label-width="40px">

        <el-form-item prop="jobName">
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.jobName') }}
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="editForm.jobName"
              :disabled="editForm.editMode"
              :placeholder="$t('jobConfig.rules.jobName')"
              autocomplete="off"/>
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
              v-model="editForm.cron"
              :placeholder="$t('jobConfig.rules.cron')"
              autocomplete="off"/>
          </el-col>
        </el-form-item>

        <el-form-item prop="appName">
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.appName') }}
          </el-col>
          <el-col :span="7">
            <el-input
              v-model="editForm.appName"
              :disabled="editForm.editMode"
              :placeholder="$t('jobConfig.rules.appName')"
              autocomplete="off"/>
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
              v-model="editForm.jobExecutionType"
              :placeholder="$t('jobConfig.rules.jobExecutionType')">
              <el-option
                v-for="item in jobExecutionTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item prop="shardingTotalCount">
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.shardingTotalCount') }}
          </el-col>
          <el-col :span="8">
            <el-input-number
              :placeholder="$t('jobConfig.labelInfo.shardingTotalCount')"
              v-model="editForm.shardingTotalCount"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.jobParameter') }}
          </el-col>
          <el-col :span="8">
            <el-input
              :placeholder="$t('jobConfig.labelInfo.jobParameter')"
              v-model="editForm.jobParameter"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

        <el-form-item prop="cpuCount">
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.cpuCount') }}
          </el-col>
          <el-col :span="8">
            <el-input-number
              :placeholder="$t('jobConfig.labelInfo.cpuCount')"
              :precision="3"
              :step="0.001"
              v-model="editForm.cpuCount"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.memoryMB') }}
          </el-col>
          <el-col :span="8">
            <el-input-number
              :placeholder="$t('jobConfig.labelInfo.memoryMB')"
              :min="1"
              v-model="editForm.memoryMB"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.failover') }}
          </el-col>
          <el-col :span="8">
            <el-checkbox v-model="editForm.failover">{{ $t('jobConfig.labelInfo.failover') }} </el-checkbox>
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.misfire') }}
          </el-col>
          <el-col :span="8">
            <el-checkbox v-model="editForm.misfire">{{ $t('jobConfig.labelInfo.misfire') }} </el-checkbox>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.applicationContext') }}
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="editForm.applicationContext"
              :placeholder="$t('jobConfig.rules.applicationContext')"
              type="textarea"
              autocomplete="off"
            />
          </el-col>
          <el-col :span="4">
            {{ $t('jobConfig.labelInfo.shardingItemParameters') }}
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="editForm.shardingItemParameters"
              :placeholder="$t('jobConfig.rules.shardingItemParameters')"
              type="textarea"
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
              :placeholder="$t('jobConfig.labelInfo.beanName')"
              v-model="editForm.beanName"
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
              v-model="editForm.description"
              :placeholder="$t('jobConfig.rules.description')"
              type="textarea"
              autocomplete="off"
            />
          </el-col>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modifyDialogVisible = false">{{ $t('btn.cancel') }}</el-button>
        <el-button
          type="primary"
          @click="onEditConfirm('editForm')"
        >{{ $t('btn.confirm') }}</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'JobConfig',
  data() {
    return {
      modifyDialogVisible: false,
      column: [
        {
          label: this.$t('jobConfig').labelInfo.jobName,
          prop: 'jobName'
        },
        {
          label: this.$t('jobConfig').labelInfo.appName,
          prop: 'appName'
        },
        {
          label: this.$t('jobConfig').labelInfo.shardingTotalCount,
          prop: 'shardingTotalCount'
        },
        {
          label: this.$t('jobConfig').labelInfo.cron,
          prop: 'cron'
        }
      ],
      jobExecutionTypeOptions: [{
        value: 'DAEMON',
        label: 'DAEMON'
      }, {
        value: 'TRANSIENT',
        label: 'TRANSIENT'
      }],
      statusColor: {
        OK: 'success',
        DISABLED: 'warning',
        CRASHED: 'info',
        SHARDING_FLAG: 'primary'
      },
      searchForm: {
        jobName: ''
      },
      editForm: {
        editMode: true,
        jobName: '',
        appName: '',
        jobExecutionType: 'DAEMON',
        cron: '',
        shardingTotalCount: 1,
        jobParameter: '',
        cpuCount: 0.001,
        memoryMB: 1,
        beanName: '',
        description: '',
        props: {},
        failover: false,
        misfire: false,
        streamingProcess2: false,
        shardingItemParameters: ''
      },
      rules: {
        jobName: [
          {
            required: true,
            message: this.$t('jobConfig').rules.jobName,
            trigger: 'change'
          }
        ],
        appName: [
          {
            required: true,
            message: this.$t('jobConfig').rules.appName,
            trigger: 'change'
          }
        ],
        shardingTotalCount: [
          {
            required: true,
            message: this.$t('jobConfig').rules.shardingTotalCount,
            trigger: 'change'
          }
        ],
        cron: [
          {
            required: true,
            message: this.$t('jobConfig').rules.cron,
            trigger: 'change'
          }
        ],
        cpuCount: [
          {
            required: true,
            message: this.$t('jobConfig').rules.cpuCount,
            trigger: 'change'
          }
        ],
        memoryMB: [
          {
            required: true,
            message: this.$t('jobConfig').rules.memoryMB,
            trigger: 'change'
          }
        ]
      },
      editTitle: this.$t('jobConfig').labelInfo.editTitle,
      tableData: [],
      cloneTableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    this.search()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    handleCurrentChange(val) {
      const data = clone(this.cloneTableData)
      this.currentPage = val
      this.tableData = data.splice(this.pageSize * (val - 1), this.pageSize)
    },
    getJobConfig() {
      var params = {
      }
      API.getJobConfig(params).then(res => {
        const data = Array.prototype.filter.call(res.model, this.filterSearchData)
        this.total = data.length
        this.cloneTableData = clone(data)
        this.tableData = data.splice(this.pageSize * (this.currentPage - 1), this.pageSize)
      })
    },
    filterSearchData(model) {
      if (!this.searchForm.jobName) {
        return true
      }
      if (!model) {
        return true
      }
      return model.jobName && model.jobName.toLowerCase().includes(this.searchForm.jobName.toLowerCase())
    },
    handleAdd() {
      const model = {
        editMode: false,
        jobName: '',
        appName: '',
        jobExecutionType: 'DAEMON',
        cron: '',
        shardingTotalCount: 1,
        jobParameter: '',
        cpuCount: 0.001,
        memoryMB: 1,
        beanName: '',
        description: '',
        props: {},
        failover: false,
        misfire: false,
        streamingProcess2: false,
        shardingItemParameters: ''
      }
      this.editTitle = this.$t('jobConfig').labelInfo.addTitle
      this.editForm = model
      this.modifyDialogVisible = true
    },
    handleModify(row) {
      const params = {
        jobName: row.jobName
      }
      //
      API.getJobDetail(params).then(res => {
        const data = res.model
        data.props = data.props || {}
        if (data.props['streaming.process'] === 'true') {
          data.streamingProcess2 = true
        } else if (!data.props['streaming.process'] || data.props['streaming.process'] === 'false') {
          data.streamingProcess2 = false
        } else {
          data.streamingProcess2 = true
        }
        this.editTitle = this.$t('jobConfig').labelInfo.editTitle
        data.editMode = true
        this.editForm = data
        this.modifyDialogVisible = true
      })
    },
    handleDetail(row) {
      const params = {
        jobName: row.jobName
      }
      localStorage.setItem('/job-config/job-detail/jobName', params.jobName)
      this.$router.push({ path: '/job-config/job-detail', params: params })
    },
    handleEnable(row) {
      const params = {
        jobName: row.jobName
      }
      API.enableJob(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    handleDisable(row) {
      const params = {
        jobName: row.jobName
      }
      API.disableJob(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    handlerRemove(row) {
      const params = {
        jobName: row.jobName
      }
      API.removeJob(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.delSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    onEditConfirm(formName) {
      const params = {
        appName: this.editForm.appName
      }
      if (!this.editForm.cron) {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('jobConfig').rules.cron,
          type: 'error'
        })
        return
      }

      this.$refs[formName].validate(valid => {
        if (valid) {
          API.getAppDetail(params).then(res => {
            const data = res.model
            if (!data) {
              this.$notify({
                title: this.$t('common').notify.title,
                message: this.$t('common').notify.appNotRegisterMessage + ':' + params.appName,
                type: 'error'
              })
              return false
            } else {
              this.onEditConfirmReal(formName)
            }
          })
        } else {
          if (!params.appName) {
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('jobConfig').rules.appName,
              type: 'error'
            })
          }
          return false
        }
      })
    },
    onEditConfirmReal(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // fixData
          const data = clone(this.editForm)
          data.props = data.props || {}
          if (data.streamingProcess2) {
            data.props['streaming.process'] = 'true'
          } else {
            data.props['streaming.process'] = 'false'
          }
          //
          if (data.editMode) {
            API.updateJobConfig(data).then(res => {
              this.modifyDialogVisible = false
              this.$notify({
                title: this.$t('common').notify.title,
                message: this.$t('common').notify.editSucMessage,
                type: 'success'
              })
              this.search()
            })
          } else {
            API.addJobConfig(data).then(res => {
              this.modifyDialogVisible = false
              this.$notify({
                title: this.$t('common').notify.title,
                message: this.$t('common').notify.addSucMessage,
                type: 'success'
              })
              this.search()
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    search() {
      this.getJobConfig()
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
.el-form .el-col{
  padding-left: 4px;
}
</style>
