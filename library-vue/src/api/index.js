import request from './request'

function createApi(path) {
  return {
    list:   (params)        => request.get(path, { params }),
    getById: (id)           => request.get(path + '/' + id),
    add:     (data)         => request.post(path, data),
    update:  (id, data)     => request.put(path + '/' + id, data),
    delete:  (id)           => request.delete(path + '/' + id)
  }
}

export const bookApi     = createApi('/book')
export const readerApi   = createApi('/reader')
export const categoryApi = createApi('/category')
export const fineApi     = createApi('/fine')
export const reviewApi   = createApi('/review')

// 借阅：借书走业务接口（body 传 {bookId, readerId}），还书单独接口
export const borrowApi = {
  ...createApi('/borrow'),
  borrow:     (data) => request.post('/borrow', data),
  returnBook: (id)   => request.put('/borrow/' + id + '/return')
}

// 排行：实时聚合，无分页/增删改
export const rankingApi = {
  list: (params) => request.get('/ranking', { params })
}

// 认证
export const authApi = {
  login:   (data) => request.post('/auth/login', data),
  logout:  ()     => request.post('/auth/logout'),
  current: ()     => request.get('/auth/current')
}
