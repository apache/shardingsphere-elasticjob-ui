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

import axios from 'axios'
import { ElMessage as Message } from 'element-plus'
import Language from '../lang/index'
import C from './conf'

axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8'
axios.defaults.withCredentials = true

function ajax(url, type, options, config) {
  return new Promise((resolve, reject) => {
    axios({
      method: type,
      url: config ? C[config.host] + url : C.HOST + url,
      timeout: 10000,
      headers: {
        'Access-Token': window.localStorage.getItem('Access-Token') || ''
      },
      params: type === 'get' ? options : null,
      data: options
    })
      .then(result => {
        const data = result.data
        const success = data.success
        if (success) {
          resolve(data)
          return
        }

        if (!success) {
          if (data.errorCode === 403) {
            const store = window.localStorage
            store.removeItem('Access-Token')
            store.removeItem('username')
            location.href = '#/login'
            return
          }
          reject(data)
          Message({
            message: data.errorMsg,
            type: 'error',
            duration: 2 * 1000
          })
          return
        }
      })
      .catch(error => {
        const lang = window.localStorage.getItem('language') || 'en-US'
        const langData = Language[lang]
        let errorMessage = ''

        try {
          const errorMsg = error.message || ''

          // 判断错误类型并获取对应的国际化提示
          if (error.code === 'ECONNABORTED' || errorMsg.includes('timeout')) {
            // 超时错误
            errorMessage = langData.common.networkError.timeout
          } else if (errorMsg === 'Network Error' || !error.response) {
            // 网络连接错误
            errorMessage = langData.common.networkError.network
          } else if (error.response) {
            // HTTP 状态码错误
            const status = error.response.status
            switch (status) {
              case 400:
                errorMessage = langData.common.networkError.badRequest
                break
              case 401:
                errorMessage = langData.common.networkError.unauthorized
                break
              case 403:
                errorMessage = langData.common.networkError.forbidden
                break
              case 404:
                errorMessage = langData.common.networkError.notFound
                break
              case 500:
              case 502:
              case 503:
              case 504:
                errorMessage = langData.common.networkError.serverError
                break
              default:
                errorMessage = langData.common.networkError.unknown
            }
          } else {
            errorMessage = langData.common.networkError.unknown
          }
        } catch (e) {
          // 如果获取国际化提示失败，使用原始错误信息
          errorMessage = error.message || error.toString()
        }

        Message({
          message: errorMessage,
          type: 'error',
          duration: 3 * 1000
        })
      })
  })
}

const config = {
  get(url, options, config) {
    return new Promise((resolve, reject) => {
      ajax(url, 'get', options, config).then(
        data => {
          resolve(data)
        },
        error => {
          reject(error)
        }
      )
    })
  },

  post(url, options, config) {
    return new Promise((resolve, reject) => {
      ajax(url, 'post', options, config).then(
        data => {
          resolve(data)
        },
        error => {
          reject(error)
        }
      )
    })
  },

  put(url, options) {
    return new Promise((resolve, reject) => {
      ajax(url, 'put', options).then(
        data => {
          resolve(data)
        },
        error => {
          reject(error)
        }
      )
    })
  },

  delete(url, options) {
    return new Promise((resolve, reject) => {
      ajax(url, 'delete', options).then(
        data => {
          resolve(data)
        },
        error => {
          reject(error)
        }
      )
    })
  }
}

export default config
