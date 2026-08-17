<template>
  <div>
    <div class="page-header">
      <h2>{{ isEdit ? '编辑逾期罚款' : '添加逾期罚款' }}</h2>
      <router-link to="/fine" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
            <div class="form-group"><label>读者</label><input v-model="form.readerName" placeholder="请输入读者姓名" /></div>
      <div class="form-group"><label>书名</label><input v-model="form.bookTitle" placeholder="请输入书名" /></div>
      <div class="form-group"><label>逾期天数</label><input v-model.number="form.overdueDays" type="number" min="0" /></div>
      <div class="form-group"><label>罚款金额</label><input v-model.number="form.fineAmount" type="number" min="0" step="0.01" /></div>
      <div class="form-group"><label>缴纳状态</label><select v-model="form.paymentStatus"><option value="未缴">未缴</option><option value="已缴">已缴</option></select></div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">保存</button>
        <router-link to="/fine" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { fineApi } from '../../api/index.js'

export default {
  name: 'FineForm',
  data() {
    return {
      isEdit: false,
      form: { id: null, readerName: '', bookTitle: '', overdueDays: 0, fineAmount: 0, paymentStatus: '未缴' }
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
        const res = await fineApi.getById(id)
        this.form = res.data
      } catch (e) {
        alert('获取数据失败')
      }
    },
    async handleSubmit() {
      try {
        if (this.isEdit) {
          await fineApi.update(this.form.id, this.form)
          alert('更新成功')
        } else {
          await fineApi.add(this.form)
          alert('添加成功')
        }
        this.$router.push('/fine')
      } catch (e) {
        alert('操作失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>