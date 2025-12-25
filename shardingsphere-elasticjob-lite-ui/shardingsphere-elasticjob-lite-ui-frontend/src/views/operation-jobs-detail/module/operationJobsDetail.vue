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
  <div class="box-card">
    <div class="header-section">
      <div class="left-section">
        <el-button link icon="ArrowLeft" class="back-btn" @click="goBack">Back</el-button>
        <span class="divider">|</span>
        <span class="page-title">{{ $t('operationJobs.labelInfo.jobName') }}: <span class="job-name">{{ jobName || "-" }}</span></span>
      </div>
      <div class="right-section">
        <el-input
          v-model="searchForm.jobName"
          :placeholder="$t('common.searchPlaceholder')" 
          clearable
          @clear="search"
          @change="search"
          class="search-input"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
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
          <template #default="scope">
            <span v-if="'item'===item.prop || 'serverIp'===item.prop || 'instanceId'===item.prop">{{ scope.row[item.prop] }}</span>
            <span v-if="'failover'===item.prop">{{ scope.row.failover? scope.row.failover : "-" }}</span>
            <el-button
              v-if="'status'===item.prop"
              :type="statusColor[scope.row.status]"
              size="small"
              plain>
              {{ $t("operationJobs.statusText."+scope.row[item.prop]) }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('operationJobs.table.operate')"
          fixed="right"
          width="140"
          align="center">
          <template #default="scope">
            <el-button
              v-if="'DISABLED'===scope.row.status"
              size="small"
              type="success"
              link
              @click="handleEnable(scope.row)">{{ $t("operationJobs.actionText.enable") }}</el-button>
            <el-button
              v-if="'PENDING'===scope.row.status"
              size="small"
              type="warning"
              link
              @click="handleDisable(scope.row)">{{ $t("operationJobs.actionText.disable") }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
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
          label: this.$t('operationJobs.labelInfo.item'),
          prop: 'item'
        },
        {
          label: this.$t('operationJobs.labelInfo.serverIp'),
          prop: 'serverIp'
        },
        {
          label: this.$t('operationJobs.labelInfo.instanceId'),
          prop: 'instanceId'
        },
        {
          label: this.$t('operationJobs.labelInfo.status'),
          prop: 'status'
        },
        {
          label: this.$t('operationJobs.labelInfo.failover'),
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
          title: this.$t('common.notify.title'),
          message: this.$t('common.notify.actionSucMessage'),
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
          title: this.$t('common.notify.title'),
          message: this.$t('common.notify.actionSucMessage'),
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
.box-card {
  background: #fff;
  border-radius: 4px;
  padding: 24px;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;

  .left-section {
    display: flex;
    align-items: center;

    .back-btn {
      font-size: 14px;
      font-weight: 500;
      color: #606266;
      padding-left: 0;
      
      &:hover {
        color: #409eff;
      }
    }

    .divider {
      color: #DCDFE6;
      margin: 0 12px;
      font-size: 12px;
    }

    .page-title {
      font-size: 14px;
      color: #606266;
      
      .job-name {
        color: #303133;
        font-weight: 600;
        font-size: 16px;
        margin-left: 4px;
      }
    }
  }

  .right-section {
    .search-input {
      width: 240px;
    }
  }
}

.table-wrap {
  :deep(.el-table) {
    th {
      background-color: #F5F7FA;
    }
  }
}

.pagination {
  float: right;
  margin: 20px 0;
}
</style>
