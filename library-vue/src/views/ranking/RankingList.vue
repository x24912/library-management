<template>
  <div>
    <div class="page-header">
      <h2>热门排行</h2>
      <div class="header-actions">
        <div class="search-box">
          <input v-model="keyword" placeholder="搜索书名/统计月份" @keyup.enter="handleSearch" />
          <button class="btn btn-primary" @click="handleSearch">搜索</button>
        </div>
        <router-link to="/ranking/add" class="btn btn-primary">+ 添加</router-link>
      </div>
    </div>

    <table v-if="list.length">
      <thead>
        <tr><th>书名</th><th>月借阅</th><th>总借阅</th><th>统计月份</th><th>操作</th></tr>
      </thead>
      <tbody>
        <tr v-for="item in list" :key="item.id">
          <td>{{ item.bookTitle }}</td>
          <td>{{ item.monthlyBorrowTimes }}</td>
          <td>{{ item.totalBorrowTimes }}</td>
          <td>{{ item.statisticsMonth }}</td>
          <td>
            <router-link :to="'/ranking/edit/' + item.id" class="btn btn-sm btn-default">编辑</router-link>
            <button class="btn btn-sm btn-danger" @click="handleDelete(item.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-else class="loading">暂无数据</div>

    <Pagination
      v-if="total > 0"
      :current="page"
      :total="total"
      :limit="limit"
      @change="handlePageChange"
    />
  </div>
</template>

<script>
import { rankingApi } from '../../api/index.js'
import Pagination from '../../components/Pagination.vue'

export default {
  name: 'ranking\\RankingList',
  components: { Pagination },
  data() {
    return {
      list: [],
      keyword: '',
      page: 1,
      limit: 10,
      total: 0
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    async fetchList() {
      try {
        const res = await rankingApi.list({ page: this.page, limit: this.limit, keyword: this.keyword })
        this.list = res.data.data || []
        this.total = res.data.total || 0
      } catch (e) {
        alert('获取列表失败')
      }
    },
    handleSearch() {
      this.page = 1
      this.fetchList()
    },
    handlePageChange(page) {
      this.page = page
      this.fetchList()
    },
    async handleDelete(id) {
      if (!confirm('确认删除？')) return
      try {
        await rankingApi.delete(id)
        alert('删除成功')
        this.fetchList()
      } catch (e) {
        alert('删除失败')
      }
    }
  }
}
</script>

<style scoped>
.header-actions {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}
.search-box {
  display: flex;
  gap: 6px;
}
.search-box input {
  padding: 6px 10px;
  border: 1px solid #d9d9d9;
  font-size: 13px;
  outline: none;
  width: 200px;
}
.search-box input:focus {
  border-color: #1890ff;
}
</style>
