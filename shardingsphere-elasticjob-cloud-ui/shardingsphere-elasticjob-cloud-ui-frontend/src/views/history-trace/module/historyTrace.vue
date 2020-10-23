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
    <div class="btn-group" style="">
      <el-input
        :placeholder="$t('historyTrace.searchForm.jobName')"
        v-model="searchForm.jobName"
        clearable>
      </el-input>
      <el-input
        :placeholder="$t('historyStatus.searchForm.taskId')"
        v-model="searchForm.taskId"
        clearable>
      </el-input>
      <el-input
        :placeholder="$t('historyTrace.searchForm.serverIp')"
        v-model="searchForm.ip"
        clearable>
      </el-input>
      <el-date-picker
        :placeholder="$t('historyTrace.searchForm.startTime')"
        v-model="searchForm.startTime"
        type="datetime"
        clearable>
      </el-date-picker>
      <el-date-picker
        :placeholder="$t('historyTrace.searchForm.CompleteTime')"
        v-model="searchForm.endTime"
        type="datetime"
        clearable>
      </el-date-picker>
      <el-select
        :placeholder="$t('historyTrace.searchForm.executeResult')"
        v-model="searchForm.isSuccess"
        clearable
      >
        <el-option
          v-for="item in executeResultItems"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button
        icon="el-icon-search"
        @click="getJobTrace"
      ></el-button>
    </div>
    <div class="table-wrap">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column
          v-for="(item, index) in column"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :width="item.width"
          :formatter = "item.formatter"
        />
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

  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import API from '../api'
export default {
  name: 'HistoryTrace',
  data() {
    return {
      column: [
        {
          label: this.$t('historyTrace').column.jobName,
          prop: 'jobName'
        },
        {
          label: this.$t('historyTrace').column.taskId,
          prop: 'taskId'
        },
        {
          label: this.$t('historyTrace').column.serverIp,
          prop: 'ip'
        },
        {
          label: this.$t('historyTrace').column.executeSource,
          prop: 'source'
        },
        {
          label: this.$t('historyTrace').column.executeResult,
          prop: 'success',
          formatter: function(row, cell, value) {
            return value + ''
          }
        },
        {
          label: this.$t('historyTrace').column.failureCause,
          prop: 'failureCause'
        },
        {
          label: this.$t('historyTrace').column.startTime,
          prop: 'startTime'
        },
        {
          label: this.$t('historyTrace').column.completeTime,
          prop: 'completeTime'
        }
      ],
      executeResultItems: [
        {
          value: true,
          label: this.$t('historyTrace').searchForm.executeSuccess
        }, {
          value: false,
          label: this.$t('historyTrace').searchForm.executeFailed
        }
      ],
      searchForm: {
        jobName: '',
        taskId: '',
        ip: '',
        startTime: '',
        endTime: '',
        isSuccess: ''
      },
      tableData: [],
      cloneTableData: [],
      currentPage: 1,
      pageSize: 10,
      total: null
    }
  },
  created() {
    this.getJobTrace()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    handleCurrentChange(val) {
      const page = {
        per_page: this.pageSize,
        page: val
      }
      API.loadExecution(Object.assign(this.searchForm, page)).then(res => {
        const data = res.model.rows
        this.total = res.model.total
        this.tableData = data
      })
    },
    getJobTrace() {
      API.loadExecution(this.searchForm).then(res => {
        const data = res.model.rows
        this.total = res.model.total
        this.tableData = data
      })
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
.el-input {
  width: 200px;
}
</style>
