import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 响应拦截器：后端统一返回 {code, message, data}
// code=200 时直接解包出业务数据，组件无需再写 res.data.data
request.interceptors.response.use(
  response => {
    const body = response.data
    if (body && body.code === 200) {
      return body.data
    }
    const msg = body?.message || '操作失败'
    alert(msg)
    return Promise.reject(new Error(msg))
  },
  error => {
    const status = error.response?.status
    const msg = error.response?.data?.message || error.message || '请求失败'
    // 登录态探测接口未登录时静默处理（由路由守卫跳转登录页）
    if (!(status === 401 && error.config?.url?.includes('/auth/current'))) {
      alert(msg)
    }
    console.error('[API Error]', msg)
    return Promise.reject(error)
  }
)

export default request
