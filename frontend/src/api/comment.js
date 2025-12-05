import axios from "./axios"

export const getComments = (articleId) =>
  axios.get(`/articles/${articleId}/comments`).then((r) => r.data)

export const addComment = (articleId, data) =>
  axios.post(`/articles/${articleId}/comments`, data).then((r) => r.data)

export const deleteComment = (commentId) =>
  axios.delete(`/articles/comments/${commentId}`)
