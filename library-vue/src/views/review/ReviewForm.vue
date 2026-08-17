<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑评价' : '添加评价' }}</h2>
      <router-link to="/review" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>书名</label><input v-model="form.bookTitle" placeholder="请输入书名" /></div>
      <div class="form-group"><label>评价人</label><input v-model="form.reviewerName" placeholder="请输入评价人" /></div>
      <div class="form-group"><label>评分（1-5）</label><input v-model.number="form.rating" type="number" min="1" max="5" /></div>
      <div class="form-group"><label>评价内容</label><textarea v-model="form.reviewContent" placeholder="请输入评价内容"></textarea></div>
      <div class="form-group"><label>评价时间</label><input v-model="form.reviewTime" type="date" /></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/review" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { reviewApi } from '../../api/index.js'

export default {
  name: 'ReviewForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, bookTitle: '', reviewerName: '', rating: 5, reviewContent: '', reviewTime: '' }
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
        const res = await reviewApi.getById(id)
        this.form = res.data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await reviewApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await reviewApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/review')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>