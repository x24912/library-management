<template>
  <div>
    <div class="page-header">
      <h2>热门排行</h2>
      <span class="tip">按借阅记录实时统计</span>
    </div>

    <table v-if="list.length">
      <thead>
        <tr><th>排名</th><th>书名</th><th>借阅次数</th></tr>
      </thead>
      <tbody>
        <tr v-for="(item, idx) in list" :key="idx">
          <td>{{ idx + 1 }}</td>
          <td>{{ item.bookTitle }}</td>
          <td>{{ item.totalBorrowTimes }}</td>
        </tr>
      </tbody>
    </table>

    <div v-else class="loading">暂无数据</div>
  </div>
</template>

<script>
import { rankingApi } from '../../api/index.js'

export default {
  name: 'RankingList',
  data() {
    return { list: [] }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    async fetchList() {
      try {
        this.list = (await rankingApi.list({ limit: 50 })) || []
      } catch (e) {
        alert('获取排行失败')
      }
    }
  }
}
</script>

<style scoped>
.tip {
  color: #999;
  font-size: 13px;
}
</style>
