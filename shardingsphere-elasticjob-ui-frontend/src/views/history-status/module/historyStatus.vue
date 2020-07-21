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
        :placeholder="$t('historyStatus.searchForm.jobName')"
        v-model="searchForm.jobName"
        clearable>
      </el-input>
      <el-date-picker
        v-model="searchForm.start"
        type="datetime"
        :placeholder="$t('historyStatus.searchForm.startTime')"
        clearable>
      </el-date-picker>
      <el-date-picker
        v-model="searchForm.end"
        type="datetime"
        :placeholder="$t('historyStatus.searchForm.CompleteTime')"
        clearable>
      </el-date-picker>
      <el-select v-model="searchForm.state" clearable :placeholder="$t('historyStatus.searchForm.state')">
        <el-option
          v-for="item in stateItems"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button
        icon="el-icon-search"
        @click="getJobStatus"
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
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'HistoryStatus',
  data() {
    return {
      column: [
        {
          label: this.$t('historyStatus').column.jobName,
          prop: 'jobName'
        },
        {
          label: this.$t('historyStatus').column.shardingItem,
          prop: 'shardingItem'
        },
        {
          label: this.$t('historyStatus').column.state,
          prop: 'state'
        },
        {
          label: this.$t('historyStatus').column.createTime,
          prop: 'creationTime'
        },
        {
          label: this.$t('historyStatus').column.remark,
          prop: 'message'
        }
      ],
      stateItems: [
        {
          value: 'TASK_STAGING',
          label: this.$t('historyStatus').searchForm.stateStaging
        }, {
          value: 'TASK_FAILED',
          label: this.$t('historyStatus').searchForm.stateFailed
        },
        {
          value: 'TASK_FINISHED',
          label: this.$t('historyStatus').searchForm.stateFinished
        }, {
          value: 'TASK_RUNNING',
          label: this.$t('historyStatus').searchForm.stateRunning
        },
        {
          value: 'TASK_ERROR',
          label: this.$t('historyStatus').searchForm.stateError
        }, {
          value: 'TASK_KILLED',
          label: this.$t('historyStatus').searchForm.stateKilled
        }
      ],
      searchForm: {
        jobName: '',
        state: '',
        start: '',
        end: ''
      },
      tableData: [],
      cloneTableData: [],
      currentPage: 1,
      pageSize: 10,
      total: null
    }
  },
  created() {
    this.getJobStatus()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    handleCurrentChange(val) {
      const data = clone(this.cloneTableData)
      this.tableData = data.splice(val - 1, this.pageSize)
    },
    getJobStatus() {
      API.loadStatus(this.searchForm).then(res => {
        const data = res.model
        this.total = data.length
        this.cloneTableData = clone(res.model)
        this.tableData = data.splice(0, this.pageSize)
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
