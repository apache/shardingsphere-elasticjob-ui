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
  getAllServersBriefInfo: (params = {}) => API.get(`/api/servers/getAllServersBriefInfo`, params),
  getServersTotalCount: (params = {}) => API.get(`/api/servers/count`, params),

  disableServer: (params = {}) => API.post('/api/servers/' + params.serverIp + '/disable'),
  enableServer: (params = {}) => API.post('/api/servers/' + params.serverIp + '/enable'),
  shutdownServer: (params = {}) => API.post('/api/servers/' + params.serverIp + '/shutdown'),
  removeServer: (params = {}) => API.delete('/api/servers/' + params.serverIp + ''),

  getJobs: (params = {}) => API.get('/api/servers/' + params.serverIp + '/jobs'),
  disableServerJob: (params) => API.post('/api/servers/' + params.serverIp + '/jobs/' + params.jobName + '/disable'),
  enableServerJob: (params) => API.post('/api/servers/' + params.serverIp + '/jobs/' + params.jobName + '/enable'),
  shutdownServerJob: (params) => API.post('/api/servers/' + params.serverIp + '/jobs/' + params.jobName + '/shutdown'),
  removeServerJob: (params) => API.delete('/api/servers/' + params.serverIp + '/jobs/' + params.jobName + '')

}
