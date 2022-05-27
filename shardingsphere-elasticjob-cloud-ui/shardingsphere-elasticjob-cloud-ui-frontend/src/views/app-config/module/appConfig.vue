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
                plain
                @click="handleDetail(scope.row)">{{ $t("appConfig.table.operateDetail") }}</el-button>
              <el-button
                size="mini"
                type="primary"
                plain
                @click="handleModify(scope.row)">{{ $t("appConfig.table.operateModify") }}</el-button>
              <el-button
                size="mini"
                type="primary"
                plain
                @click="handleDelete(scope.row)">{{ $t("appConfig.table.operateDel") }}</el-button>
              <el-button
                size="mini"
                type="primary"
                plain
                @click="handleDisable(scope.row)">{{ scope.row.disabled ? $t("appConfig.table.operateEnable") : $t("appConfig.table.operateDisable") }}</el-button>
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
      :title="$t('appConfig.addDialog.title')"
      :visible.sync="addDialogVisible"
      width="1010px"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="210px">
        <el-form-item :label="$t('appConfig.addDialog.appName')" prop="appName">
          <el-input :placeholder="$t('appConfig.rules.appName')" v-model="form.appName" autocomplete="off" />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.script')" prop="bootstrapScript">
          <el-input
            :placeholder="'bin/start.sh'"
            v-model="form.bootstrapScript"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.cpu')" prop="cpuCount">
          <el-input
            :placeholder="$t('appConfig.rules.cpuCount')"
            v-model="form.cpuCount"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.memory')" prop="memoryMB">
          <el-input
            :placeholder="$t('appConfig.rules.memoryMB')"
            v-model="form.memoryMB"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.sampling')" prop="eventTraceSamplingCount">
          <el-input
            :placeholder="$t('appConfig.rules.eventTraceSamplingCount')"
            v-model="form.eventTraceSamplingCount"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.appURL')" prop="appURL">
          <el-input
            :placeholder="$t('appConfig.rules.appURL')"
            v-model="form.appURL"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.cacheEnable')" prop="cacheEnable">
          <el-checkbox v-model="form.appCacheEnable"></el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">{{ $t("btn.cancel") }}</el-button>
        <el-button
          type="primary"
          @click="onConfirm('form')"
        >{{ $t("btn.submit") }}</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="isEdit ? $t('appConfig.dialog.editTitle') : $t('appConfig.dialog.detailTitle')"
      :visible.sync="editDialogVisible"
      width="1010px"
    >
      <el-form ref="editForm" :model="editForm" :rules="rules" label-width="210px">
        <el-form-item :label="$t('appConfig.addDialog.appName')" prop="appName">
          <el-input :placeholder="$t('appConfig.rules.appName')" v-model="editForm.appName" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.script')" prop="bootstrapScript">
          <el-input
            :placeholder="'bin/start.sh'"
            v-model="editForm.bootstrapScript"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.cpu')" prop="cpuCount">
          <el-input
            :placeholder="$t('appConfig.rules.cpuCount')"
            v-model="editForm.cpuCount"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.memory')" prop="memoryMB">
          <el-input
            :placeholder="$t('appConfig.rules.memoryMB')"
            v-model="editForm.memoryMB"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.sampling')" prop="eventTraceSamplingCount">
          <el-input
            :placeholder="$t('appConfig.rules.eventTraceSamplingCount')"
            v-model="editForm.eventTraceSamplingCount"
            :disabled="!isEdit"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.appURL')" prop="appURL">
          <el-input
            :placeholder="$t('appConfig.rules.appURL')"
            v-model="editForm.appURL"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item :label="$t('appConfig.addDialog.cacheEnable')" prop="cacheEnable">
          <el-checkbox v-model="editForm.appCacheEnable" :disabled="!isEdit"></el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">{{ $t("btn.cancel") }}</el-button>
        <el-button
          v-if="isEdit"
          type="primary"
          @click="edit('editForm')"
        >{{ $t("btn.submit") }}</el-button>
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
      editDialogVisible: false,
      isEdit: false,
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
        appName: '',
        appURL: '',
        bootstrapScript: '',
        cpuCount: '1',
        memoryMB: '128',
        eventTraceSamplingCount: '0',
        appCacheEnable: true
      },
      editForm: {},
      rules: {
        appName: [
          {
            required: true,
            message: this.$t('appConfig').rules.appName,
            trigger: 'change'
          }
        ],
        bootstrapScript: [
          {
            required: true,
            message: this.$t('appConfig').rules.bootstrapScript,
            trigger: 'change'
          }
        ],
        cpuCount: [
          {
            required: true,
            message: this.$t('appConfig').rules.cpuCount,
            trigger: 'change'
          }
        ],
        memoryMB: [
          {
            required: true,
            message: this.$t('appConfig').rules.memoryMB,
            trigger: 'change'
          }
        ],
        eventTraceSamplingCount: [
          {
            required: true,
            message: this.$t('appConfig').rules.eventTraceSamplingCount,
            trigger: 'change'
          }
        ],
        appURL: [
          {
            required: true,
            message: this.$t('appConfig').rules.appURL,
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
          API.addApp(this.form).then(res => {
            this.addDialogVisible = false
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('common').notify.addSucMessage,
              type: 'success'
            })
            this.getApp()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    add() {
      this.addDialogVisible = true
    },
    handleDetail(row) {
      this.isEdit = false
      this.editForm = row
      this.editDialogVisible = true
    },
    handleModify(row) {
      this.isEdit = true
      this.editForm = row
      this.editDialogVisible = true
    },
    edit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          API.update(this.editForm).then(res => {
            this.editDialogVisible = false
            this.$notify({
              title: this.$t('common').notify.title,
              message: this.$t('common').notify.editSucMessage,
              type: 'success'
            })
            this.getApp()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleDelete(row) {
      API.delete({ 'appName': row.appName }).then(res => {
        this.$notify({
          title: this.$t('common').notify.title,
          message: this.$t('common').notify.delSucMessage,
          type: 'success'
        })
        this.getApp()
      })
    },
    handleDisable(row) {
      if (row.disabled) {
        API.enable({ 'appName': row.appName }).then(res => {
          this.$notify({
            title: this.$t('common').notify.title,
            message: this.$t('appConfig').tips.enableSuccess,
            type: 'success'
          })
          this.getApp()
        })
      } else {
        API.disable({ 'appName': row.appName }).then(res => {
          this.$notify({
            title: this.$t('common').notify.title,
            message: this.$t('appConfig').tips.disableSuccess,
            type: 'success'
          })
          this.getApp()
        })
      }
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
