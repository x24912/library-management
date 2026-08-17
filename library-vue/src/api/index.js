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
export const borrowApi   = createApi('/borrow')
export const categoryApi = createApi('/category')
export const fineApi     = createApi('/fine')
export const reviewApi   = createApi('/review')
export const rankingApi  = createApi('/ranking')
