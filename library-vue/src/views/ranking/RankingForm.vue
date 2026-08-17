<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑排行' : '添加排行' }}</h2>
      <router-link to="/ranking" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>书名</label><input v-model="form.bookTitle" placeholder="请输入书名" /></div>
      <div class="form-group"><label>月借阅次数</label><input v-model.number="form.monthlyBorrowTimes" type="number" min="0" /></div>
      <div class="form-group"><label>总借阅次数</label><input v-model.number="form.totalBorrowTimes" type="number" min="0" /></div>
      <div class="form-group"><label>统计月份</label><input v-model="form.statisticsMonth" placeholder="如：2025-06" /></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/ranking" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { rankingApi } from '../../api/index.js'

export default {
  name: 'RankingForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, bookTitle: '', monthlyBorrowTimes: 0, totalBorrowTimes: 0, statisticsMonth: '' }
    }
  },
  mounted() {
    const id = this.$route.params.id
    if (id) {
      this.isEdit = true
      this.fetchData(id)
    }
  },
  methods: {
    async fetchData(id) {
      try {
        const res = await rankingApi.getById(id)
        this.form = res.data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await rankingApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await rankingApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/ranking')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>