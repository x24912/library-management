<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑分类' : '添加分类' }}</h2>
      <router-link to="/category" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>分类名称</label><input v-model="form.categoryName" placeholder="请输入分类名称" /></div>
      <div class="form-group"><label>可借天数</label><input v-model.number="form.allowedBorrowDays" type="number" min="0" /></div>
      <div class="form-group"><label>描述</label><textarea v-model="form.description" placeholder="请输入描述"></textarea></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/category" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { categoryApi } from '../../api/index.js'

export default {
  name: 'CategoryForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, categoryName: '', allowedBorrowDays: 30, description: '' }
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
        const res = await categoryApi.getById(id)
        this.form = res.data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await categoryApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await categoryApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/category')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>