<template>
  <div class="login-page">
    <div class="login-card">
      <h2>📚 图书借阅管理系统</h2>
      <p class="subtitle">请登录管理员账号</p>
      <div class="form-group">
        <label>用户名</label>
        <input v-model="form.username" placeholder="请输入用户名" @keyup.enter="handleLogin" />
      </div>
      <div class="form-group">
        <label>密码</label>
        <input v-model="form.password" type="password" placeholder="请输入密码" @keyup.enter="handleLogin" />
      </div>
      <button class="btn btn-primary login-btn" :disabled="loading" @click="handleLogin">
        {{ loading ? '登录中...' : '登 录' }}
      </button>
      <p class="tip">默认账号：admin / 123456</p>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '../stores/auth'

export default {
  name: 'LoginView',
  data() {
    return {
      form: { username: '', password: '' },
      loading: false
    }
  },
  methods: {
    async handleLogin() {
      if (!this.form.username || !this.form.password) {
        alert('请输入用户名和密码')
        return
      }
      this.loading = true
      try {
        const auth = useAuthStore()
        await auth.login(this.form.username.trim(), this.form.password)
        this.$router.push('/')
      } catch (e) {
        // 错误信息已由拦截器弹出
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-card {
  width: 360px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 32px 28px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}
.login-card h2 {
  margin: 0 0 4px;
  font-size: 18px;
  text-align: center;
  color: #1890ff;
}
.subtitle {
  text-align: center;
  color: #999;
  font-size: 13px;
  margin: 0 0 24px;
}
.login-btn {
  width: 100%;
  margin-top: 8px;
  padding: 8px 0;
  font-size: 14px;
}
.tip {
  text-align: center;
  color: #bbb;
  font-size: 12px;
  margin-top: 16px;
}
</style>
