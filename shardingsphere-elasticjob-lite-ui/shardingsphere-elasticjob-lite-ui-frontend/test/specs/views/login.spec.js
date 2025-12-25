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
import Login from '../../../src/views/login/index.vue'
import Language from '../../../src/lang/index'
import store from '../../../src/store'
import router from '../../../src/router'

// language setting init
const navLang = navigator.language
const localLang = navLang === 'zh-CN' || navLang === 'en-US' ? navLang : false
const lang = window.localStorage.getItem('language') || localLang || 'zh-CN'

const i18n = createI18n({
  locale: 'en-US',
  messages: Language,
  legacy: true
})

describe('Login/index.vue', () => {
  it('Login Does the pages exist？', () => {
    const wrapper = shallowMount(Login, {
      global: {
        plugins: [store, i18n, router]
      }
    })
    expect(wrapper.exists()).toBe(true)
  })
})
