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

import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/registry-center',
    component: () => import('@/views/registry-center'),
    hidden: true,
    name: 'Registry center'
  },
  {
    path: '/app-config',
    component: () => import('@/views/app-config'),
    hidden: true,
    name: 'App config'
  },
  {
    path: '/job-config',
    component: () => import('@/views/job-config'),
    hidden: true,
    name: 'Job config'
  },
  {
    path: '/job-config/job-detail',
    component: () => import('@/views/job-detail'),
    hidden: true,
    name: 'Job detail'
  },
  {
    path: '/job-status',
    component: () => import('@/views/job-status'),
    hidden: true,
    name: 'Job status'
  },
  {
    path: '/history-trace',
    component: () => import('@/views/history-trace'),
    hidden: true,
    name: 'History trace'
  },
  {
    path: '/history-status',
    component: () => import('@/views/history-status'),
    hidden: true,
    name: 'History status'
  },
  {
    path: '/history-dashboard',
    component: () => import('@/views/job-dashboard'),
    hidden: true,
    name: 'Job dashboard'
  }
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
