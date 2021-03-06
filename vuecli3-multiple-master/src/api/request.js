import axios from 'axios'
import { Message } from 'element-ui'
import { setcookie } from '../api/cookiesex'
import 'element-ui/lib/theme-chalk/index.css'


axios.defaults.withCredentials=true;//让ajax携带cookie
///可以夾帶cors herad 來實現跨網域... ?
axios.interceptors.request.use(
  config => {
    return config
  },
  err => {
    Message.error({ message: '请求超时!' })
    return Promise.resolve(err)
  }
)
axios.interceptors.response.use(
  data => {
    if (data.status && data.status == 200 && data.data.status == 'error') {
      Message.error({ message: data.data.msg })

      return
    }
    /// 供spring boot 測試用途
    // console.log("test");
    // if(data.config.url == "http://localhost:9990/getSession"){
    // if(data.data.code ==200)
    //   Message.error({ message: '權限驗證成功', type: 'success' });
    // else
    //   Message.error({ message: '權限已經失效', type: 'error' });}
    //if (data.data != '') Message.error({ message: data.data, type: 'success' })
    return data
  },
  err => {
    if (err.response.status == 504 || err.response.status == 404) {
      Message.error({ message: '服务器被吃了⊙﹏⊙∥' })
    } else if (err.response.status == 403) {
      Message.error({ message: '权限不足,请联系管理员!' })
    } else {
      Message.error({ message: '未知错误!' })
    }
    return Promise.resolve(err)
  }
)

let base = 'http://localhost:8084'
//let base = 'http://0bfda5c0.ngrok.io'
//server 1 let base = 'http://localhost:9990/'
//server 2 let base = ''
//server 3 let base = ''
//test method json address http://localhost:9990/httpMethod2?id=10
export const postRequest = (url, params) => {
  console.log(`${base}${url}`)
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [
      function(data) {
        let ret = ''
        ret = JSON.stringify(data)
        // JSON.stringify BY @RequestBody 必須加上這行
        for (let it in data) {
          ret +=
            encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        console.log(ret)
        console.log(data)
        return ret
      }
    ],
    headers: {
      // 'Content-Type': 'application/x-www-form-urlencoded'

      'Content-Type': 'application/json'
    }
  })
}
export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [
      function(data) {
        let ret = ''
        for (let it in data) {
          ret +=
            encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return ret
      }
    ],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
export const deleteRequest = url => {
  return axios({
    method: 'delete',
    url: `${base}${url}`
  })
}
export const getRequest = (url, params) => {
 // console.log(params.id)
  return axios({
    method: 'get',
    url: `${base}${url}`,
    params
  })
}
