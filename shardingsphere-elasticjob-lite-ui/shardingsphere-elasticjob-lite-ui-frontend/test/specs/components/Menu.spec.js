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
import { expect } from 'chai'
import { shallowMount, createLocalVue, mount } from '@vue/test-utils'
import VueI18n from 'vue-i18n'
import Vuex from 'vuex'
import Menu from '../../../src/components/Menu/index.vue'
import Language from '../../../src/lang/index'
import store from '../../../src/store'
import router from '../../../src/router'

const localVue = createLocalVue()

localVue.use(VueI18n)
localVue.use(Vuex)

// language setting init
const navLang = navigator.language
const localLang = navLang === 'zh-CN' || navLang === 'en-US' ? navLang : false
const lang = window.localStorage.getItem('language') || localLang || 'zh-CN'
localVue.config.lang = lang

// language setting
const locales = Language
const mergeZH = locales['zh-CN']
const mergeEN = locales['en-US']

const i18n = new VueI18n({
  locale: 'en-US',
  messages: {
    'zh-CN': mergeZH,
    'en-US': mergeEN
  }
})

describe('Menu/index.vue', () => {
  it('Menu Does the component exist？', () => {
    const wrapper = shallowMount(Menu, {
      localVue,
      i18n,
      store,
      router,
      propsData: {
        isCollapse: false
      }
    })
    expect(wrapper.isVueInstance()).to.be.true
  })

  it('setData()', () => {
    const wrapper = shallowMount(Menu, {
      localVue,
      i18n,
      store,
      router
    })
    wrapper.setData({ isCollapse: true })
    expect(wrapper.vm.isCollapse).to.equal(true)
  })
})
