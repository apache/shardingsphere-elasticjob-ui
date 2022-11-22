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

    <div>
      <el-dialog
        :title="dumpInfo.title"
        :visible.sync="dumpConfigDialogVisible"
        :close-on-click-modal="true"
        :modal="true"
        :show-close="true"
        :center="true"
        style="text-align:center">
        <span style="padding-right: 5vh;">
          <span style="font-weight: bolder;">{{ $t("operationServers.dumpText.jobName") }}：</span>
          <span >{{ jobName }}</span>
        </span>
        <span >
          <span style="font-weight: bolder;">{{ $t("operationServers.dumpText.dumpPort") }}：</span>
          <el-input-number
            ref="dumPort"
            v-model="dumpPort"
            :min="1025"
            :max="65535"
            autocomplete="off"
          />
          <input ref="jobName" type="hidden">
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dumpConfigDialogVisible = false">{{ $t('operationServers.dumpText.cancel') }}</el-button>
          <el-button type="primary" @click="handleDump">{{ $t('operationServers.dumpText.dumpBtn') }}</el-button>
        </span>
      </el-dialog>

      <!--dump dialog-->
      <el-dialog
        :title="dumpInfo.title"
        :visible.sync="dialogVisible"
        :close-on-click-modal="true"
        :modal="true"
        :show-close="true"
        :center="true"
        style="height: 80%;">
        <span style="white-space: pre-wrap;" class="dumpContent">{{ dumpContent }}</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="copy">{{ $t('operationServers.dumpText.copy') }}</el-button>
        </span>
      </el-dialog>
    </div>

    <el-form :model="searchForm" class="demo-form-inline">
      <el-form-item>
        <el-col :span="4">
          <el-button type="info" icon="el-icon-arrow-left" @click="goBack">Back</el-button>
        </el-col>
        <el-col :span="14">
          <el-link type="info" disabled> {{ $t('operationServers.labelInfo.serverIp') }}:</el-link>
          <el-link type="info" disabled>{{ serverIp || "-" }}</el-link>
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
          :prop="columnJobName.prop"
          :label="columnJobName.label"
          :width="columnJobName.width">
          <template slot-scope="scope">
            <span>{{ scope.row.jobName }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :prop="columnInstanceCount.prop"
          :label="columnInstanceCount.label"
          :width="columnInstanceCount.width">
          <template slot-scope="scope">
            <span>{{ scope.row.instanceCount }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :prop="columnStatus.prop"
          :label="columnStatus.label"
          :width="columnStatus.width">
          <template slot-scope="scope">
            <el-button
              v-if="'OK'===scope.row.status && scope.row.instanceCount"
              :type="statusColor.OK"
              size="mini"
              plain>
              {{ $t("operationServers.statusText.OK") }}
            </el-button>
            <el-button
              v-if="'DISABLED'===scope.row.status && scope.row.instanceCount"
              :type="statusColor.DISABLED"
              size="mini"
              plain>
              {{ $t("operationServers.statusText.DISABLED") }}
            </el-button>
            <el-button
              v-if="'SHARDING_FLAG'===scope.row.status && scope.row.instanceCount"
              :type="statusColor.SHARDING_FLAG"
              size="mini"
              plain>
              {{ $t("operationServers.statusText.SHARDING_FLAG") }}
            </el-button>
            <el-button
              v-if="'PENDING'===scope.row.status && scope.row.instanceCount"
              :type="statusColor.PENDING"
              size="mini"
              plain>
              {{ $t("operationServers.statusText.PENDING") }}
            </el-button>
            <el-button
              v-if="!scope.row.instanceCount || 'CRASHED'===scope.row.status"
              :type="statusColor.CRASHED"
              size="mini"
              plain>
              {{ $t("operationServers.statusText.CRASHED") }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('operationServers.labelInfo.operate')"
          fixed="right"
          width="300">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                v-if="scope.row.instanceCount && 'DISABLED'===scope.row.status"
                size="mini"
                type="success"
                plain
                @click="handleEnable(scope.row)">{{ $t("operationServers.actionText.enable") }}</el-button>
              <el-button
                v-if="scope.row.instanceCount && 'OK'===scope.row.status"
                size="mini"
                type="warning"
                plain
                @click="handleDisable(scope.row)">{{ $t("operationServers.actionText.disable") }}</el-button>
              <el-button
                v-if="scope.row.instanceCount"
                size="mini"
                type="danger"
                plain
                @click="handleShutdown(scope.row)">{{ $t("operationServers.actionText.shutdown") }}</el-button>
              <el-button
                v-if="scope.row.instanceCount"
                :disabled="isGuest"
                size="mini"
                type="danger"
                plain
                @click="dumpDialog(scope.row)">{{ $t("operationServers.actionText.dump") }}</el-button>
              <el-button
                v-if="!scope.row.instanceCount"
                size="mini"
                type="danger"
                plain
                @click="handleRemove(scope.row)">{{ $t("operationServers.actionText.remove") }}</el-button>
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
import API from '../../operation-servers/api'

export default {
  name: 'OperationServers',
  data() {
    return {
      dumpConfigDialogVisible: false,
      dialogVisible: false,
      jobName: '',
      dumpPort: 9888,
      dumpContent: '',
      serverIp: '',
      columnJobName: {
        label: this.$t('operationServers').labelInfo.jobName,
        prop: 'jobName'
      },
      columnInstanceCount: {
        label: this.$t('operationServers').labelInfo.instanceCount,
        prop: 'instanceCount'
      },
      columnStatus: {
        label: this.$t('operationServers').labelInfo.status,
        prop: 'status'
      },
      dumpInfo: {
        title: this.$t('operationServers').dumpText.title,
        jobName: this.$t('operationServers').dumpText.jobName,
        dumpPort: this.$t('operationServers').dumpText.dumpPort,
        dumpBtn: this.$t('operationServers').dumpText.dumpBtn,
        cancel: this.$t('operationServers').dumpText.cancel,
        copy: this.$t('operationServers').dumpText.copy
      },
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
    this.serverIp = this.$route.params.serverIp ||
      localStorage.getItem('/operation-servers/status-detail/serverIp')
    if (!this.serverIp) {
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
      this.$router.push({ path: '/operation-servers' })
    },
    getJobs() {
      var params = {
        serverIp: this.serverIp
      }
      API.getJobs(params).then(res => {
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
        serverIp: this.serverIp,
        jobName: row.jobName
      }
      API.enableServerJob(params).then(res => {
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
        serverIp: this.serverIp,
        jobName: row.jobName
      }
      API.disableServerJob(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    handleShutdown(row) {
      const params = {
        serverIp: this.serverIp,
        jobName: row.jobName
      }
      API.shutdownServerJob(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    dumpDialog(row) {
      this.dumpPort = 9888
      this.dumpConfigDialogVisible = true
      this.jobName = row.jobName
    },
    handleDump() {
      const params = {
        serverIp: this.serverIp,
        dumpPort: this.$refs.dumPort.value,
        jobName: this.jobName
      }
      API.dumpServerJob(params).then(res => {
        this.dumpConfigDialogVisible = false
        this.dialogVisible = true
        // alert(res.model)
        // this.dumpContent=res.model.replace(/\n/g, '<br>')
        this.dumpContent = res.model
      })
    },
    handleRemove(row) {
      const params = {
        serverIp: this.serverIp,
        jobName: row.jobName
      }
      API.removeServerJob(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
        this.search()
      })
    },
    search() {
      this.getJobs()
    },

    copy() {
      const text = document.querySelector('.dumpContent').innerText
      const target = document.createElement('textarea')
      target.id = 'creatDom'
      target.value = text
      document.body.appendChild(target)
      target.select()
      try {
        document.execCommand('Copy')
        console.log('copy success.')
        const creatDom = document.getElementById('creatDom')
        creatDom.parentNode.removeChild(creatDom)
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.actionSucMessage,
          type: 'success'
        })
      } catch (e) {
        console.log('copy failure.')
      }
      this.dialogVisible = false
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
