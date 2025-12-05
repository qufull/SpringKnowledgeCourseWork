import axios from './axios'

export const getAllArticles = () =>
  axios.get('/articles').then(r => r.data)

export const searchArticles = (q) =>
  axios.get('/articles/search', { params: { q } }).then(r => r.data)

export const getByCategory = (id) =>
  axios.get(`/articles/by-category/${id}`).then(r => r.data)

export const getArticle = (id) =>
  axios.get(`/articles/${id}`).then(r => r.data)

export const updateArticle = (id, data) =>
  axios.put(`/articles/${id}`, data).then(r => r.data)

export const createArticle = (data) =>
  axios.post('/articles', data).then(r => r.data)

export const deleteArticle = (id) =>
  axios.delete(`/articles/${id}`)

export const getMyArticles = () =>
  axios.get('/articles/my').then(r => r.data)

export const getArticleByUpdate = (id) =>
  axios.get(`/articles/update/${id}`).then(r => r.data)


