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
        :disabled="isGuest"
        class="btn-plus"
        type="primary"
        icon="el-icon-plus"
        @click="add"
      >{{ $t("registryCenter.btnTxt") }}</el-button>
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
        <el-table-column :label="$t('appConfig.table.operate')" fixed="right" width="300">
          <template slot-scope="scope">
            <el-button-group>
            <el-button
              size="mini"
              type="primary"
              :disabled="isGuest"
              @click="handleModify(scope.row)"
              plain>{{ $t("appConfig.table.operateDetail") }}</el-button>
            <el-button
              size="mini"
              type="primary"
              :disabled="isGuest"
              @click="handleModify(scope.row)"
              plain>{{ $t("appConfig.table.operateModify") }}</el-button>
            <el-button
              size="mini"
              type="primary"
              :disabled="isGuest"
              @click="handleModify(scope.row)"
              plain>{{ $t("appConfig.table.operateDel") }}</el-button>
            <el-button
              size="mini"
              type="primary"
              :disabled="isGuest"
              @click="handleModify(scope.row)"
              plain>{{ $t("appConfig.table.operateDisable") }}</el-button>
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
      :title="$t('appConfig.registDialog.title')"
      :visible.sync="addDialogVisible"
      width="1010px"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="170px">
        <el-form-item :label="$t('appConfig.registDialog.name')" prop="name">
          <el-input :placeholder="$t('appConfig.rules.name')" v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item :label="$t('appConfig.registDialog.address')" prop="zkAddressList">
          <el-input
            :placeholder="$t('appConfig.rules.address')"
            v-model="form.zkAddressList"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.registDialog.namespaces')" prop="namespace">
          <el-input
            :placeholder="$t('appConfig.rules.namespaces')"
            v-model="form.namespace"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.registDialog.digest')">
          <el-input
            :placeholder="$t('appConfig.rules.digest')"
            v-model="form.digest"
            autocomplete="off"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="regustDialogVisible = false">{{ $t("appConfig.registDialog.btnCancelTxt") }}</el-button>
        <el-button
          type="primary"
          @click="onConfirm('form')"
        >{{ $t("appConfig.registDialog.btnConfirmTxt") }}</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import clone from 'lodash/clone'
import API from '../api'
export default {
  name: 'AppConfig',
  data() {
    return {
      addDialogVisible: false,
      isGuest: window.localStorage.getItem('isGuest') === 'true',
      column: [
        {
          label: this.$t('appConfig').table.appName,
          prop: 'appName'
        },
        {
          label: this.$t('appConfig').table.appUrl,
          prop: 'appURL'
        },
        {
          label: this.$t('appConfig').table.script,
          prop: 'bootstrapScript'
        }
      ],
      form: {
        name: '',
        zkAddressList: '',
        namespace: '',
        digest: ''
      },
      rules: {
        name: [
          {
            required: true,
            message: this.$t('appConfig').rules.name,
            trigger: 'change'
          }
        ],
        zkAddressList: [
          {
            required: true,
            message: this.$t('appConfig').rules.address,
            trigger: 'change'
          }
        ],
        namespace: [
          {
            required: true,
            message: this.$t('appConfig').rules.namespaces,
            trigger: 'change'
          }
        ],
        instanceType: [
          {
            required: true,
            message: this.$t('appConfig').rules.centerType,
            trigger: 'change'
          }
        ],
        orchestrationName: [
          {
            required: true,
            message: this.$t('appConfig').rules.orchestrationName,
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
    this.getApp()
  },
  methods: {
    ...mapActions(['setRegCenterActivated']),
    handleCurrentChange(val) {
      const data = clone(this.cloneTableData)
      this.tableData = data.splice(val - 1, this.pageSize)
    },
    getApp() {
      API.getApp().then(res => {
        const data = res.model
        this.total = data.length
        this.cloneTableData = clone(res.model)
        this.tableData = data.splice(0, this.pageSize)
      })
      this.getRegCenterActivated()
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
        API.postRegCenterConnect(params).then(res => {
          this.$notify({
            title: this.$t('common').notify.title,
            message: this.$t('common').notify.conSucMessage,
            type: 'success'
          })
          this.getRegCenter()
        })
      }
    },
    handlerDel(row) {
      const params = {
        name: row.name
      }
      API.deleteRegCenter(params).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.delSucMessage,
          type: 'success'
        })
        this.getRegCenter()
      })
    },
    onConfirm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          API.postRegCenter(this.form).then(res => {
            this.addDialogVisible = false
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('common').notify.addSucMessage,
              type: 'success'
            })
            this.getRegCenter()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    add() {
      this.addDialogVisible = true
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
