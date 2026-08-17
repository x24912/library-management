<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑图书' : '添加图书' }}</h2>
      <router-link to="/book" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>书名</label><input v-model="form.bookTitle" placeholder="请输入书名" /></div>
      <div class="form-group"><label>作者</label><input v-model="form.author" placeholder="请输入作者" /></div>
      <div class="form-group"><label>ISBN</label><input v-model="form.isbn" placeholder="请输入ISBN" /></div>
      <div class="form-group"><label>分类</label><input v-model="form.category" placeholder="请输入分类" /></div>
      <div class="form-group"><label>库存数量</label><input v-model.number="form.stockQuantity" type="number" min="0" /></div>
      <div class="form-group"><label>存放位置</label><input v-model="form.storageLocation" placeholder="请输入存放位置" /></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/book" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { bookApi } from '../../api/index.js'

export default {
  name: 'BookForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, bookTitle: '', author: '', isbn: '', category: '', stockQuantity: 0, storageLocation: '' }
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
        const data = await bookApi.getById(id)
        this.form = data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await bookApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await bookApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/book')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>
