import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { useAuthStore } from '../stores/auth'

const routes = [
  { path: '/login',           name: 'login',      component: () => import('../views/LoginView.vue') },
  { path: '/',                name: 'home',        component: HomeView },
  { path: '/book',            name: 'book',        component: () => import('../views/book/BookList.vue') },
  { path: '/book/add',        name: 'bookAdd',     component: () => import('../views/book/BookForm.vue') },
  { path: '/book/edit/:id',   name: 'bookEdit',    component: () => import('../views/book/BookForm.vue') },
  { path: '/reader',          name: 'reader',      component: () => import('../views/reader/ReaderList.vue') },
  { path: '/reader/add',      name: 'readerAdd',   component: () => import('../views/reader/ReaderForm.vue') },
  { path: '/reader/edit/:id', name: 'readerEdit',  component: () => import('../views/reader/ReaderForm.vue') },
  { path: '/borrow',          name: 'borrow',      component: () => import('../views/borrow/BorrowList.vue') },
  { path: '/borrow/add',      name: 'borrowAdd',   component: () => import('../views/borrow/BorrowForm.vue') },
  { path: '/category',        name: 'category',    component: () => import('../views/category/CategoryList.vue') },
  { path: '/category/add',    name: 'categoryAdd', component: () => import('../views/category/CategoryForm.vue') },
  { path: '/category/edit/:id', name: 'categoryEdit', component: () => import('../views/category/CategoryForm.vue') },
  { path: '/fine',            name: 'fine',        component: () => import('../views/fine/FineList.vue') },
  { path: '/fine/add',        name: 'fineAdd',     component: () => import('../views/fine/FineForm.vue') },
  { path: '/fine/edit/:id',   name: 'fineEdit',    component: () => import('../views/fine/FineForm.vue') },
  { path: '/review',          name: 'review',      component: () => import('../views/review/ReviewList.vue') },
  { path: '/review/add',      name: 'reviewAdd',   component: () => import('../views/review/ReviewForm.vue') },
  { path: '/review/edit/:id', name: 'reviewEdit',  component: () => import('../views/review/ReviewForm.vue') },
  { path: '/ranking',         name: 'ranking',     component: () => import('../views/ranking/RankingList.vue') }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// 路由守卫：未登录跳转登录页
router.beforeEach(async (to) => {
  const auth = useAuthStore()
  if (!auth.initialized) {
    await auth.check()
  }
  if (to.path !== '/login' && !auth.username) {
    return { path: '/login' }
  }
  if (to.path === '/login' && auth.username) {
    return { path: '/' }
  }
})

export default router
