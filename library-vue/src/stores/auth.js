import { defineStore } from 'pinia'
import { authApi } from '../api'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    username: '',
    initialized: false
  }),
  actions: {
    /** 应用启动时探测登录态（未登录不弹窗，由路由守卫跳转） */
    async check() {
      try {
        this.username = (await authApi.current()) || ''
      } catch (e) {
        this.username = ''
      } finally {
        this.initialized = true
      }
    },
    async login(username, password) {
      await authApi.login({ username, password })
      this.username = username
      this.initialized = true
    },
    async logout() {
      try {
        await authApi.logout()
      } catch (e) {
        // 忽略登出失败
      }
      this.username = ''
    }
  }
})
