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
    <div class="btn-group">
      <el-button
        class="btn-plus"
        type="primary"
        icon="el-icon-plus"
        @click="add"
      >{{ $t("dataSource.btnTxt") }}</el-button>
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
        <el-table-column :label="$t('dataSource.table.operate')" fixed="right" width="200">
          <template slot-scope="scope">
            <el-tooltip
              :content="!scope.row.activated ? $t('dataSource.table.operateConnect'): $t('dataSource.table.operateConnected')"
              class="item"
              effect="dark"
              placement="top"
            >
              <el-button
                :type="scope.row.activated ? 'success' : 'primary'"
                icon="el-icon-link"
                size="small"
                @click="handleConnect(scope.row)"
              />
            </el-tooltip>
            <el-tooltip
              :content="$t('dataSource.table.operateDel')"
              class="item"
              effect="dark"
              placement="top"
            >
              <el-button
                size="small"
                type="danger"
                icon="el-icon-delete"
                @click="handlerDel(scope.row)"
              />
            </el-tooltip>
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
      :title="$t('dataSource.addDialog.title')"
      :visible.sync="regustDialogVisible"
      width="1010px"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="170px">
        <el-form-item :label="$t('dataSource.addDialog.name')" prop="name">
          <el-input :placeholder="$t('dataSource.rules.name')" v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item :label="$t('dataSource.addDialog.driver')" prop="driver">
          <el-select
            v-model="form.driver"
            :placeholder="$t('dataSource.rules.driver')"
            style="width: 100%;"
            allow-create
            default-first-option
            filterable>
            <el-option
              v-for="each in availableDriverClasses"
              :label="each"
              :key="each"
              :value="each">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('dataSource.addDialog.url')" prop="url">
          <el-input
            :placeholder="$t('dataSource.rules.url')"
            v-model="form.url"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('dataSource.addDialog.username')" prop="username">
          <el-input
            :placeholder="$t('dataSource.rules.username')"
            v-model="form.username"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('dataSource.addDialog.password')">
          <el-input
            :placeholder="$t('dataSource.rules.password')"
            v-model="form.password"
            autocomplete="off"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="regustDialogVisible = false">{{ $t("dataSource.addDialog.btnCancelTxt") }}</el-button>
        <el-button
          type="primary"
          @click="connectTest('form')"
        >{{ $t("dataSource.addDialog.btnConnectTestTxt") }}</el-button>
        <el-button
          type="primary"
          @click="onConfirm('form')"
        >{{ $t("dataSource.addDialog.btnConfirmTxt") }}</el-button>
      </div>
    </el-dialog>

  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'DataSource',
  data() {
    return {
      regustDialogVisible: false,
      availableDriverClasses: [],
      column: [
        {
          label: this.$t('dataSource').addDialog.name,
          prop: 'name'
        },
        {
          label: this.$t('dataSource').addDialog.driver,
          prop: 'driver'
        },
        {
          label: this.$t('dataSource').addDialog.url,
          prop: 'url'
        },
        {
          label: this.$t('dataSource').addDialog.username,
          prop: 'username'
        },
        {
          label: this.$t('dataSource').addDialog.password,
          prop: 'password'
        }
      ],
      form: {
        name: '',
        driver: '',
        url: '',
        username: '',
        password: ''
      },
      rules: {
        name: [
          {
            required: true,
            message: this.$t('dataSource').rules.name,
            trigger: 'change'
          }
        ],
        driver: [
          {
            required: true,
            message: this.$t('dataSource').rules.driver,
            trigger: 'change'
          }
        ],
        url: [
          {
            required: true,
            message: this.$t('dataSource').rules.url,
            trigger: 'change'
          }
        ],
        username: [
          {
            required: true,
            message: this.$t('dataSource').rules.username,
            trigger: 'change'
          }
        ],
        password: [
          {
            required: true,
            message: this.$t('dataSource').rules.password,
            trigger: 'change'
          }
        ]
      },
      tableData: [],
      cloneTableData: [],
      currentPage: 1,
      pageSize: 10,
      total: null
    }
  },
  created() {
    this.loadAvailableDriverClasses()
    this.load()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    loadAvailableDriverClasses() {
      API.availableDriverClasses().then(res => {
        this.availableDriverClasses = res.model
      })
    },
    handleCurrentChange(val) {
      const data = clone(this.cloneTableData)
      this.tableData = data.splice(val - 1, this.pageSize)
    },
    load() {
      API.load().then(res => {
        const data = res.model
        this.total = data.length
        this.cloneTableData = clone(res.model)
        this.tableData = data.splice(0, this.pageSize)
      })
      // this.getRegCenterActivated()
    },
    getRegCenterActivated() {
      API.getRegCenterActivated().then(res => {
        this.setRegCenterActivated(res.model.name)
      })
    },
    handleConnect(row) {
      if (row.activated) {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').connected,
          type: 'success'
        })
      } else {
        const params = {
          name: row.name
        }
        API.connect(params).then(res => {
          if (res.model) {
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('common').notify.conSucMessage,
              type: 'success'
            })
          } else {
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('common').notify.conFailMessage,
              type: 'error'
            })
          }
          this.load()
        })
      }
    },
    handlerDel(row) {
      const params = {
        name: row.name
      }
      API.delete(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.delSucMessage,
          type: 'success'
        })
        this.load()
      })
    },
    onConfirm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          API.add(this.form).then(res => {
            this.regustDialogVisible = false
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('common').notify.addSucMessage,
              type: 'success'
            })
            this.load()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    connectTest(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          API.connectTest(this.form).then(res => {
            if (res.model) {
              this.$notify({
                title: this.$t('common').notify.title,
                message: this.$t('common').notify.conSucMessage,
                type: 'success'
              })
            } else {
              this.$notify({
                title: this.$t('common').notify.title,
                message: this.$t('common').notify.conFailMessage,
                type: 'error'
              })
            }
          })
        } else {
          return false
        }
      })
    },
    add() {
      this.regustDialogVisible = true
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
