<template>
  <div>
    <div class="hero">
      <h1>图书借阅管理系统</h1>
      <p>基于 Vue 3 + Java 的前后端分离图书管理系统</p>
    </div>

    <div class="card-grid">
      <router-link to="/book" class="card">
        <h3>图书管理</h3>
        <p>管理所有图书信息，包括书名、作者、ISBN、库存等</p>
      </router-link>

      <router-link to="/reader" class="card">
        <h3>读者管理</h3>
        <p>管理读者信息，包括可借数量和已借数量</p>
      </router-link>

      <router-link to="/borrow" class="card">
        <h3>借阅记录</h3>
        <p>查看和管理所有借阅记录、归还状态</p>
      </router-link>

      <router-link to="/category" class="card">
        <h3>图书分类</h3>
        <p>管理图书分类和各类别的可借天数</p>
      </router-link>

      <router-link to="/fine" class="card">
        <h3>逾期罚款</h3>
        <p>管理逾期罚款记录和缴纳状态</p>
      </router-link>

      <router-link to="/review" class="card">
        <h3>图书评价</h3>
        <p>查看读者对图书的评价和评分</p>
      </router-link>

      <router-link to="/ranking" class="card">
        <h3>热门排行</h3>
        <p>查看图书借阅排行榜</p>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>
