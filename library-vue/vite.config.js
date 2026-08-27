import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig({
  plugins: [vue(), vueDevTools()],
  resolve: {
    alias: { '@': fileURLToPath(new URL('./src', import.meta.url)) }
  },
  server: {
    port: 3000,
    proxy: {
      // 开发环境代理到后端（SSM 部署于 Tomcat 8080）
      '/api': {
        target: 'http://localhost:8080/library_management_war',
        changeOrigin: true
      }
    }
  }
})
