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
    <div class="btn-group pull-right" style="float: right;">
      <el-input
        v-model="searchForm.serverIp"
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
            <span v-if="'status'!==item.prop">{{ scope.row[item.prop] }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('operationServers.labelInfo.operate')"
          fixed="right"
          width="300">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                v-if="scope.row.instancesNum"
                size="mini"
                type="info"
                plain
                @click="handleDetail(scope.row)">{{ $t("operationServers.actionText.detail") }}</el-button>
              <el-button
                v-if="scope.row.instancesNum && scope.row.disabledJobsNum"
                size="mini"
                type="success"
                plain
                @click="handleEnable(scope.row)">{{ $t("operationServers.actionText.enable") }}</el-button>
              <el-button
                v-if="0===scope.row.disabledJobsNum && scope.row.instancesNum"
                size="mini"
                type="warning"
                plain
                @click="handleDisable(scope.row)">{{ $t("operationServers.actionText.disable") }}</el-button>
              <el-button
                v-if="scope.row.instancesNum"
                size="mini"
                type="danger"
                plain
                @click="handleShutdown(scope.row)">{{ $t("operationServers.actionText.shutdown") }}</el-button>
              <el-button
                v-if="0===scope.row.instancesNum"
                size="mini"
                type="danger"
                icon="el-icon-delete"
                plain
                @click="handlerRemove(scope.row)">{{ $t("operationServers.actionText.remove") }}</el-button>
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
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'OperationServers',
  data() {
    return {
      column: [
        {
          label: this.$t('operationServers').labelInfo.serverIp,
          prop: 'serverIp'
        },
        {
          label: this.$t('operationServers').labelInfo.instancesNum,
          prop: 'instancesNum'
        },
        {
          label: this.$t('operationServers').labelInfo.jobsNum,
          prop: 'jobsNum'
        },
        {
          label: this.$t('operationServers').labelInfo.disabledJobsNum,
          prop: 'disabledJobsNum'
        }
      ],
      searchForm: {
        serverIp: ''
      },
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
    getAllServersBriefInfo() {
      var params = {
      }
      API.getAllServersBriefInfo(params).then(res => {
        const data = Array.prototype.filter.call(res.model, this.filterSearchData)
        this.total = data.length
        this.cloneTableData = clone(data)
        this.tableData = data.splice(this.pageSize * (this.currentPage - 1), this.pageSize)
      })
    },
    filterSearchData(model) {
      if (!this.searchForm.serverIp) {
        return true
      }
      if (!model) {
        return true
      }
      return model.serverIp && model.serverIp.toLowerCase().includes(this.searchForm.serverIp.toLowerCase())
    },
    handleDetail(row) {
      const params = {
        serverIp: row.serverIp
      }
      localStorage.setItem('/operation-servers/status-detail/serverIp', params.serverIp)
      this.$router.push({ path: '/operation-servers/status-detail', params: params })
    },
    handleEnable(row) {
      const params = {
        serverIp: row.serverIp
      }
      API.enableServer(params).then(res => {
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
        serverIp: row.serverIp
      }
      API.disableServer(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    handleShutdown(row) {
      if (!confirm(this.$t('operationServers').actionConfirm.shutdown)) {
        return
      }
      const params = {
        serverIp: row.serverIp
      }
      API.shutdownServer(params).then(res => {
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
        serverIp: row.serverIp
      }
      API.removeServer(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.delSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    search() {
      this.getAllServersBriefInfo()
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
