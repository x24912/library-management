<template>
  <div class="pagination" v-if="totalPages > 0">
    <div class="pagination-info">
      共 {{ total }} 条，第 {{ current }} / {{ totalPages }} 页
    </div>
    <div class="pagination-controls">
      <button class="btn btn-sm btn-default" :disabled="current <= 1" @click="$emit('change', 1)">首页</button>
      <button class="btn btn-sm btn-default" :disabled="current <= 1" @click="$emit('change', current - 1)">上一页</button>
      <span class="pagination-pages">
        <button
          v-for="p in visiblePages"
          :key="p"
          class="btn btn-sm"
          :class="p === current ? 'btn-primary' : 'btn-default'"
          @click="$emit('change', p)"
        >{{ p }}</button>
      </span>
      <button class="btn btn-sm btn-default" :disabled="current >= totalPages" @click="$emit('change', current + 1)">下一页</button>
      <button class="btn btn-sm btn-default" :disabled="current >= totalPages" @click="$emit('change', totalPages)">末页</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: {
    current: { type: Number, required: true },
    total: { type: Number, required: true },
    limit: { type: Number, default: 10 }
  },
  emits: ['change'],
  computed: {
    totalPages() {
      return Math.ceil(this.total / this.limit)
    },
    visiblePages() {
      const pages = []
      const tp = this.totalPages
      const cur = this.current
      let start = Math.max(1, cur - 2)
      let end = Math.min(tp, cur + 2)
      if (end - start < 4) {
        if (start === 1) end = Math.min(tp, start + 4)
        else start = Math.max(1, end - 4)
      }
      for (let i = start; i <= end; i++) pages.push(i)
      return pages
    }
  }
}
</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 0;
  gap: 12px;
  flex-wrap: wrap;
}
.pagination-info {
  font-size: 13px;
  color: #888;
}
.pagination-controls {
  display: flex;
  align-items: center;
  gap: 4px;
  flex-wrap: wrap;
}
.pagination-pages {
  display: flex;
  gap: 4px;
}
.pagination-controls .btn:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}
</style>
