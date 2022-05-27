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
  <el-tabs
    v-model="activeTabName"
    type="card"
    class="box-card"
    @tab-click="handleTabSwitch">
    <el-tab-pane
      :label="$t('jobStatus.tab.running')"
      name="running">
      <div
        class="btn-group pull-right"
        style="float: right;">
        <el-input
          v-model="searchFormRunning.jobName"
          placeholder="Search"
          clearable
          autocomplete="off"
          @clear="searchRunning"
          @change="searchRunning">
          <i
            slot="prefix"
            class="el-input__icon el-icon-search"></i>
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="searchRunning"></el-button>
        </el-input>
      </div>
      <div class="table-wrap">
        <el-table
          :data="tableDataRunning"
          stripe
          border
          style="width: 100%">
          <el-table-column
            v-for="(item, index) in columnRunning"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
          >
            <template slot-scope="scope">
              <span v-if="'status'!==item.prop">{{ scope.row[item.prop] }}</span>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            :total="totalRunning"
            :current-page="currentPageRunning"
            background
            layout="prev, pager, next"
            @current-change="handleCurrentChangeRunning"
          />
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane
      :lazy="lazyInit"
      :label="$t('jobStatus.tab.ready')"
      name="ready">
      <div class="btn-group pull-right" style="float: right;">
        <el-input
          v-model="searchFormReady.jobName"
          placeholder="Search"
          clearable
          autocomplete="off"
          @clear="searchReady"
          @change="searchReady">
          <i
            slot="prefix"
            class="el-input__icon el-icon-search"></i>
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="searchReady"></el-button>
        </el-input>
      </div>
      <div class="table-wrap">
        <el-table
          :data="tableDataReady"
          stripe
          border
          style="width: 100%">
          <el-table-column
            v-for="(item, index) in columnReady"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
          >
            <template slot-scope="scope">
              <span v-if="'status'!==item.prop">{{ scope.row[item.prop] }}</span>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            :total="totalReady"
            :current-page="currentPageReady"
            background
            layout="prev, pager, next"
            @current-change="handleCurrentChangeReady"
          />
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane
      :lazy="lazyInit"
      :label="$t('jobStatus.tab.failover')"
      name="failover">
      <div class="btn-group pull-right" style="float: right;">
        <el-input
          v-model="searchFormFailover.jobName"
          placeholder="Search"
          clearable
          autocomplete="off"
          @clear="searchFailover"
          @change="searchFailover">
          <i
            slot="prefix"
            class="el-input__icon el-icon-search"></i>
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="searchFailover"></el-button>
        </el-input>
      </div>
      <div class="table-wrap">
        <el-table
          :data="tableDataFailover"
          stripe
          border
          style="width: 100%">
          <el-table-column
            v-for="(item, index) in columnFailover"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
          >
            <template slot-scope="scope">
              <span v-if="'status'!==item.prop">{{ scope.row[item.prop] }}</span>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            :total="totalFailover"
            :current-page="currentPageFailover"
            background
            layout="prev, pager, next"
            @current-change="handleCurrentChangeFailover"
          />
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'JobStatus',
  data() {
    return {
      activeTabName: 'running',
      lazyInit: true,
      columnRunning: [
        {
          label: this.$t('jobStatus').labelInfo.id,
          prop: 'id'
        },
        {
          label: this.$t('jobStatus').labelInfo.taskName,
          prop: 'jobName'
        },
        {
          label: this.$t('jobStatus').labelInfo.severIp,
          prop: 'slaveId'
        },
        {
          label: this.$t('jobStatus').labelInfo.type,
          prop: 'type'
        },
        {
          label: this.$t('jobStatus').labelInfo.shardingItems,
          prop: 'shardingItemsTitle'
        }
      ],
      columnReady: [
        {
          label: this.$t('jobStatus').labelInfo.jobName,
          prop: 'jobName'
        },
        {
          label: this.$t('jobStatus').labelInfo.times,
          prop: 'times'
        }
      ],
      columnFailover: [
        {
          label: this.$t('jobStatus').labelInfo.jobName,
          prop: 'jobName'
        },
        {
          label: this.$t('jobStatus').labelInfo.originalTaskId,
          prop: 'originalTaskId'
        },
        {
          label: this.$t('jobStatus').labelInfo.shardingItems,
          prop: 'shardingItemsTitle'
        }
      ],
      searchFormRunning: {
        jobName: ''
      },
      searchFormReady: {
        jobName: ''
      },
      searchFormFailover: {
        jobName: ''
      },
      tableDataRunning: [],
      cloneTableDataRunning: [],
      currentPageRunning: 1,
      pageSizeRunning: 10,
      totalRunning: 0,
      tableDataReady: [],
      cloneTableDataReady: [],
      currentPageReady: 1,
      pageSizeReady: 10,
      totalReady: 0,
      tableDataFailover: [],
      cloneTableDataFailover: [],
      currentPageFailover: 1,
      pageSizeFailover: 10,
      totalFailover: 0
    }
  },
  created() {
    this.searchRunning()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    handleTabSwitch(tab, event) {
      const tabName = tab.name
      if (tabName === 'running') {
        this.searchRunning()
      } else if (tabName === 'ready') {
        this.searchReady()
      } else if (tabName === 'failover') {
        this.searchFailover()
      }
    },
    handleCurrentChangeRunning(val) {
      const data = clone(this.cloneTableDataRunning)
      this.currentPageRunning = val
      this.tableDataRunning = data.splice(this.pageSizeRunning * (val - 1), this.pageSizeRunning)
    },
    handleCurrentChangeReady(val) {
      const data = clone(this.cloneTableDataReady)
      this.currentPageReady = val
      this.tableDataReady = data.splice(this.pageSizeReady * (val - 1), this.pageSizeReady)
    },
    handleCurrentChangeFailover(val) {
      const data = clone(this.cloneTableDataFailover)
      this.currentPageFailover = val
      this.tableDataFailover = data.splice(this.pageSizeFailover * (val - 1), this.pageSizeFailover)
    },
    findAllRunningTasks() {
      var params = {
      }
      API.findAllRunningTasks(params).then(res => {
        const data = Array.prototype.filter.call(res.model, this.filterSearchDataRunning)
        this.totalRunning = data.length
        this.cloneTableDataRunning = clone(data)
        this.tableDataRunning = data.splice(this.pageSizeRunning * (this.currentPageRunning - 1), this.pageSizeRunning)
      })
    },
    findAllReadyTasks() {
      var params = {
      }
      API.findAllReadyTasks(params).then(res => {
        const data = Array.prototype.filter.call(res.model, this.filterSearchDataReady)
        this.totalReady = data.length
        this.cloneTableDataReady = clone(data)
        this.tableDataReady = data.splice(this.pageSizeReady * (this.currentPageReady - 1), this.pageSizeReady)
      })
    },
    findAllFailoverTasks() {
      var params = {
      }
      API.findAllFailoverTasks(params).then(res => {
        const data = Array.prototype.filter.call(res.model, this.filterSearchDataFailover)
        this.totalFailover = data.length
        this.cloneTableDataFailover = clone(data)
        this.tableDataFailover = data.splice(this.pageSizeFailover * (this.currentPageFailover - 1), this.pageSizeFailover)
      })
    },
    filterSearchDataRunning(model) {
      const metaInfo = model.metaInfo || {}
      model.jobName = metaInfo.jobName
      const shardingItems = metaInfo.shardingItems || []
      model.shardingItemsTitle = shardingItems.join(',')
      if (!this.searchFormRunning.jobName) {
        return true
      }
      if (!model) {
        return true
      }
      return model.jobName && model.jobName.toLowerCase().includes(this.searchFormRunning.jobName.toLowerCase())
    },
    filterSearchDataReady(model) {
      if (!this.searchFormReady.jobName) {
        return true
      }
      if (!model) {
        return true
      }
      return model.jobName && model.jobName.toLowerCase().includes(this.searchFormReady.jobName.toLowerCase())
    },
    filterSearchDataFailover(model) {
      const taskInfo = model.taskInfo || {}
      model.jobName = taskInfo.jobName
      const shardingItems = taskInfo.shardingItems || []
      model.shardingItemsTitle = shardingItems.join(',')
      if (!this.searchFormFailover.jobName) {
        return true
      }
      if (!model) {
        return true
      }
      return model.jobName && model.jobName.toLowerCase().includes(this.searchFormFailover.jobName.toLowerCase())
    },
    searchRunning() {
      this.findAllRunningTasks()
    },
    searchReady() {
      this.findAllReadyTasks()
    },
    searchFailover() {
      this.findAllFailoverTasks()
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
