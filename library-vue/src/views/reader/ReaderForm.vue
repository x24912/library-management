<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑读者' : '添加读者' }}</h2>
      <router-link to="/reader" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>姓名</label><input v-model="form.fullName" placeholder="请输入姓名" /></div>
      <div class="form-group"><label>学号</label><input v-model="form.studentId" placeholder="请输入学号" /></div>
      <div class="form-group"><label>班级</label><input v-model="form.className" placeholder="请输入班级" /></div>
      <div class="form-group"><label>最大可借数量</label><input v-model.number="form.maxBorrowCount" type="number" min="0" /></div>
      <div class="form-group"><label>已借数量</label><input v-model.number="form.borrowedCount" type="number" min="0" /></div>
      <div class="form-group"><label>联系电话</label><input v-model="form.contactNumber" placeholder="请输入联系电话" /></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/reader" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { readerApi } from '../../api/index.js'

export default {
  name: 'ReaderForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, fullName: '', studentId: '', className: '', maxBorrowCount: 5, borrowedCount: 0, contactNumber: '' }
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
        const res = await readerApi.getById(id)
        this.form = res.data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await readerApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await readerApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/reader')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>