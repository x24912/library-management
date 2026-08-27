<template>
  <div>
    <div class="page-header">
      <h2>借书</h2>
      <router-link to="/borrow" class="btn btn-default">返回列表</router-link>
    </div>
    <div class="form-card">
      <div class="form-group">
        <label>选择图书</label>
        <select v-model="form.bookId">
          <option value="" disabled>请选择图书</option>
          <option v-for="b in books" :key="b.id" :value="b.id">
            {{ b.bookTitle }}（{{ b.author }}，库存 {{ b.stockQuantity }}）
          </option>
        </select>
      </div>
      <div class="form-group">
        <label>选择读者</label>
        <select v-model="form.readerId">
          <option value="" disabled>请选择读者</option>
          <option v-for="r in readers" :key="r.id" :value="r.id">
            {{ r.fullName }}（已借 {{ r.borrowedCount }}/{{ r.maxBorrowCount }}）
          </option>
        </select>
      </div>
      <div style="margin-top: 24px;">
        <button class="btn btn-primary" @click="handleSubmit">借 书</button>
        <router-link to="/borrow" class="btn btn-default">取消</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { borrowApi, bookApi, readerApi } from '../../api/index.js'

export default {
  name: 'BorrowForm',
  data() {
    return {
      form: { bookId: '', readerId: '' },
      books: [],
      readers: []
    }
  },
  mounted() {
    this.loadBooks()
    this.loadReaders()
  },
  methods: {
    async loadBooks() {
      try {
        const data = await bookApi.list({ page: 1, limit: 100 })
        this.books = data.data || []
      } catch (e) {
        alert('获取图书列表失败')
      }
    },
    async loadReaders() {
      try {
        const data = await readerApi.list({ page: 1, limit: 100 })
        this.readers = data.data || []
      } catch (e) {
        alert('获取读者列表失败')
      }
    },
    async handleSubmit() {
      if (!this.form.bookId || !this.form.readerId) {
        alert('请选择图书和读者')
        return
      }
      try {
        const record = await borrowApi.borrow({
          bookId: Number(this.form.bookId),
          readerId: Number(this.form.readerId)
        })
        alert('借书成功：' + record.bookTitle + '，应还日期 ' + record.dueDate)
        this.$router.push('/borrow')
      } catch (e) {
        alert('借书失败：' + (e.response?.data?.message || e.message))
      }
    }
  }
}
</script>
