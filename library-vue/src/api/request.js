import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080/library_management_war/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 响应拦截器：统一错误处理
request.interceptors.response.use(
  response => response,
  error => {
    const msg = error.response?.data?.message || error.message || '请求失败'
    console.error('[API Error]', msg)
    return Promise.reject(error)
  }
)

export default request