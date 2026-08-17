<template>
  <div id="app-container">
    <nav class="navbar">
      <router-link to="/" class="brand">📚 图书借阅管理系统</router-link>
      <ul class="nav-links">
        <li><router-link to="/book">图书管理</router-link></li>
        <li><router-link to="/reader">读者管理</router-link></li>
        <li><router-link to="/borrow">借阅记录</router-link></li>
        <li><router-link to="/category">图书分类</router-link></li>
        <li><router-link to="/fine">逾期罚款</router-link></li>
        <li><router-link to="/review">图书评价</router-link></li>
        <li><router-link to="/ranking">热门排行</router-link></li>
      </ul>
    </nav>
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style scoped>
.navbar {
  background: #fff;
  border-bottom: 1px solid #eee;
  padding: 0 20px;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  position: sticky;
  top: 0;
  z-index: 100;
}

.brand {
  font-size: 16px;
  font-weight: 600;
  color: #1890ff;
  margin-right: 24px;
  text-decoration: none;
  padding: 10px 0;
}

.nav-links {
  display: flex;
  flex-wrap: wrap;
  list-style: none;
}

.nav-links li a {
  display: block;
  padding: 10px 12px;
  text-decoration: none;
  color: #555;
  font-size: 13px;
}

.nav-links li a:hover,
.nav-links li a.router-link-active {
  color: #1890ff;
}

.content {
  max-width: 1100px;
  margin: 16px auto;
  padding: 0 20px 32px;
}
</style>
