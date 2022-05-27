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
          <el-button type="info" icon="el-icon-arrow-left" @click="goBack">Back</el-button>
        </el-col>
        <el-col :span="14">
          <el-link type="info" disabled> {{ $t('operationJobs.labelInfo.jobName') }}:</el-link>
          <el-link type="info" disabled>{{ jobName || "-" }}</el-link>
        </el-col>
        <el-col :span="6">
          <el-input
            v-model="searchForm.jobName"
            placeholder="Search"
            clearable
            autocomplete="off"
            @clear="search"
            @change="search" >
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="search"></el-button>
          </el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <div class="btn-group pull-right" style="float: right;">

    </div>
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
          :width="item.width"
        >
          <template slot-scope="scope">
            <span v-if="'item'===item.prop || 'serverIp'===item.prop || 'instanceId'===item.prop">{{ scope.row[item.prop] }}</span>
            <span v-if="'failover'===item.prop">{{ scope.row.failover? scope.row.failover : "-" }}</span>
            <el-button
              v-if="'status'===item.prop"
              :type="statusColor[scope.row.status]"
              size="mini"
              plain>
              {{ $t("operationJobs.statusText."+scope.row[item.prop]) }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('operationJobs.table.operate')"
          fixed="right"
          width="100">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                v-if="'DISABLED'===scope.row.status"
                size="mini"
                type="success"
                plain
                @click="handleEnable(scope.row)">{{ $t("operationJobs.actionText.enable") }}</el-button>
              <el-button
                v-if="'PENDING'===scope.row.status"
                size="mini"
                type="warning"
                plain
                @click="handleDisable(scope.row)">{{ $t("operationJobs.actionText.disable") }}</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'OperationJobs',
  data() {
    return {
      jobName: '',
      column: [
        {
          label: this.$t('operationJobs').labelInfo.item,
          prop: 'item'
        },
        {
          label: this.$t('operationJobs').labelInfo.serverIp,
          prop: 'serverIp'
        },
        {
          label: this.$t('operationJobs').labelInfo.instanceId,
          prop: 'instanceId'
        },
        {
          label: this.$t('operationJobs').labelInfo.status,
          prop: 'status'
        },
        {
          label: this.$t('operationJobs').labelInfo.failover,
          prop: 'failover'
        }
      ],
      statusColor: {
        OK: 'success',
        DISABLED: 'warning',
        CRASHED: 'info',
        PENDING: 'info',
        SHARDING_FLAG: 'primary'
      },
      searchForm: {
        jobName: ''
      },
      tableData: [],
      cloneTableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    this.jobName = this.$route.params.jobName ||
      localStorage.getItem('/operation-jobs/status-detail/jobName')
    if (!this.jobName) {
      this.goBack()
      return
    }
    this.search()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    handleCurrentChange(val) {
      const data = clone(this.cloneTableData)
      this.tableData = data.splice(this.pageSize * (val - 1), this.pageSize)
    },
    goBack() {
      this.$router.push({ path: '/operation-jobs' })
    },
    getShardingInfo() {
      var params = {
        jobName: this.jobName
      }
      API.getShardingInfo(params).then(res => {
        const data = Array.prototype.filter.call(res.model, this.filterSearchData)
        this.total = data.length
        this.cloneTableData = clone(data)
        this.tableData = data.splice(0, this.pageSize)
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
    handleEnable(row) {
      const params = {
        jobName: this.jobName,
        item: row.item
      }
      API.enableSharding(params).then(res => {
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
        jobName: this.jobName,
        item: row.item
      }
      API.disableSharding(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    search() {
      this.getShardingInfo()
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
