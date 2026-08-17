<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑借阅记录' : '添加借阅记录' }}</h2>
      <router-link to="/borrow" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>书名</label><input v-model="form.bookTitle" placeholder="请输入书名" /></div>
      <div class="form-group"><label>读者姓名</label><input v-model="form.readerName" placeholder="请输入读者姓名" /></div>
      <div class="form-group"><label>借阅日期</label><input v-model="form.borrowDate" type="date" /></div>
      <div class="form-group"><label>应还日期</label><input v-model="form.dueDate" type="date" /></div>
      <div class="form-group"><label>实际归还日期</label><input v-model="form.actualReturnDate" type="date" /></div>
      <div class="form-group"><label>状态</label><select v-model="form.status"><option value="借出">借出</option><option value="已还">已还</option><option value="逾期">逾期</option></select></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/borrow" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { borrowApi } from '../../api/index.js'

export default {
  name: 'BorrowForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, bookTitle: '', readerName: '', borrowDate: '', dueDate: '', actualReturnDate: '', status: '借出' }
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
        const data = await borrowApi.getById(id)
        this.form = data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await borrowApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await borrowApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/borrow')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>