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
  <el-config-provider :locale="locale">
    <div id="app">
      <s-container v-if="token">
        <el-breadcrumb separator="/" class="bread-wrap">
          <el-breadcrumb-item :to="{ path: '/' }">{{ $t('common.home') }}</el-breadcrumb-item>
          <el-breadcrumb-item v-for="each in menus" :key="each">
            {{ each }}
          </el-breadcrumb-item>
        </el-breadcrumb>
        <router-view />
      </s-container>
      <template v-else>
        <router-view />
      </template>
    </div>
  </el-config-provider>
</template>

<script>
import SContainer from '@/components/Container/index.vue'
import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import en from 'element-plus/es/locale/lang/en'
import Language from '@/lang/index'

export default {
  name: 'App',
  components: {
    SContainer,
    ElConfigProvider
  },
  data() {
    return {
      menus: [],
      token: window.localStorage.getItem('Access-Token')
    }
  },
  computed: {
    locale() {
      return this.$i18n.locale === 'zh-CN' ? zhCn : en
    }
  },
  watch: {
    $route(to) {
      this.updateToken()
      this.updateMenus(to.path)
    }
  },
  methods: {
    updateToken() {
      this.token = window.localStorage.getItem('Access-Token')
    },
    updateMenus(path) {
      const lang = this.$i18n.locale
      const menuData = Language[lang].common.menuData
      if (!menuData) return

      for (const parentMenuItem of menuData) {
        if (!parentMenuItem.child) {
          if (parentMenuItem.href === path) {
            this.menus = [parentMenuItem.title]
            return
          } else {
            continue
          }
        }
        for (const childMenuItem of parentMenuItem.child) {
          if (childMenuItem.href === path) {
            this.menus = [parentMenuItem.title, childMenuItem.title]
            return
          }
        }
      }
      this.menus = []
    }
  },
  mounted() {
    this.updateMenus(this.$route.path)
  }
}
</script>
<style lang="scss" scoped>
.bread-wrap {
  margin-bottom: 15px;
}
</style>
