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
  availableDriverClasses: (params = {}) => API.get(`/api/data-source/drivers`, params),
  load: (params = {}) => API.get(`/api/data-source/load`, params),
  delete: (params = {}) => API.delete(`/api/data-source`, params),
  add: (params = {}) => API.post(`/api/data-source/add`, params),
  getRegCenterActivated: (params = {}) => API.get(`/api/data-source/activated`, params),
  connect: (params = {}) => API.post(`/api/data-source/connect`, params),
  connectTest: (params = {}) => API.post(`/api/data-source/connectTest`, params)
}
