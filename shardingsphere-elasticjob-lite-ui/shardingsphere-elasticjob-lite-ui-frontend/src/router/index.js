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

import { createRouter, createWebHashHistory } from 'vue-router'

export const constantRouterMap = [
  {
    path: '/',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/registry-center',
    component: () => import('@/views/registry-center/index.vue'),
    hidden: true,
    name: 'Registry center'
  },
  {
    path: '/data-source',
    component: () => import('@/views/data-source/index.vue'),
    hidden: true,
    name: 'Data source'
  },
  {
    path: '/operation-jobs',
    component: () => import('@/views/operation-jobs/index.vue'),
    hidden: true,
    name: 'Operation-jobs'
  },
  {
    path: '/operation-jobs/status-detail',
    component: () => import('@/views/operation-jobs-detail/index.vue'),
    hidden: true,
    name: 'Operation-jobs-detail'
  },
  {
    path: '/operation-servers',
    component: () => import('@/views/operation-servers/index.vue'),
    hidden: true,
    name: 'Operation-servers'
  },
  {
    path: '/operation-servers/status-detail',
    component: () => import('@/views/operation-servers-detail/index.vue'),
    hidden: true,
    name: 'Operation-servers-detail'
  },
  {
    path: '/job-help',
    component: () => import('@/views/help/index.vue'),
    hidden: true,
    name: 'Help'
  },
  {
    path: '/history-trace',
    component: () => import('@/views/history-trace/index.vue'),
    hidden: true,
    name: 'History trace'
  },
  {
    path: '/history-status',
    component: () => import('@/views/history-status/index.vue'),
    hidden: true,
    name: 'History status'
  }
]

const router = createRouter({
  history: createWebHashHistory(), // hash mode
  scrollBehavior: () => ({ top: 0 }),
  routes: constantRouterMap
})

export default router
