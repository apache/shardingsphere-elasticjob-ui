/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { expect } from 'vitest'
import { shallowMount } from '@vue/test-utils'
import { createI18n } from 'vue-i18n'
import Head from '../../../src/components/Head/index.vue'
import Language from '../../../src/lang/index'
import store from '../../../src/store'

// language setting init
const navLang = navigator.language
const localLang = navLang === 'zh-CN' || navLang === 'en-US' ? navLang : false
const lang = window.localStorage.getItem('language') || localLang || 'zh-CN'

// language setting
const i18n = createI18n({
  locale: 'en-US', // matching original test
  messages: Language,
  legacy: true // Enable legacy API support for Options API
})

describe('Head/index.vue', () => {
  it('Head Does the component exist？', () => {
    const wrapper = shallowMount(Head, {
      global: {
        plugins: [store, i18n]
      }
    })
    expect(wrapper.exists()).toBe(true)
  })

  it('setData()', async () => {
    const wrapper = shallowMount(Head, {
      global: {
        plugins: [store, i18n]
      }
    })
    await wrapper.setData({ isCollapse: true })
    expect(wrapper.vm.isCollapse).toBe(true)
  })

  it('togger()', async () => {
    const wrapper = shallowMount(Head, {
      global: {
        plugins: [store, i18n]
      }
    })
    const headIcon = wrapper.find('.icon-item')
    if (headIcon.exists()) {
      await headIcon.trigger('click')
      expect(wrapper.vm.isCollapse).toBe(true)
    }
  })
})
