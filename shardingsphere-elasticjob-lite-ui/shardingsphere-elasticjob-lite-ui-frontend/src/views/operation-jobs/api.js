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

import API from '@/utils/api'

export default {
  getAllJobsBriefInfo: (params = {}) => API.get(`/api/jobs/getAllJobsBriefInfo`, params),
  getJobsTotalCount: (params = {}) => API.get(`/api/jobs/count`, params),

  triggerJob: (params = {}) => API.post('/api/jobs/' + params.jobName + '/trigger'),
  disableJob: (params = {}) => API.post('/api/jobs/' + params.jobName + '/disable'),
  enableJob: (params = {}) => API.post('/api/jobs/' + params.jobName + '/enable'),
  shutdownJob: (params = {}) => API.post('/api/jobs/' + params.jobName + '/shutdown'),

  getShardingInfo: (params = {}) => API.get('/api/jobs/' + params.jobName + '/sharding'),
  disableSharding: (params) => API.post('/api/jobs/' + params.jobName + '/sharding/' + params.item + '/disable'),
  enableSharding: (params) => API.post('/api/jobs/' + params.jobName + '/sharding/' + params.item + '/enable'),

  getJobConfig: (params = {}) => API.get('/api/jobs/config/' + params.jobName),
  removeJob: (params = {}) => API.delete('/api/jobs/config/' + params.jobName),
  updateJobConfig: (params = {}) => API.put(`/api/jobs/config`, params)

}
