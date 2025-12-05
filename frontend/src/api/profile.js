import axios from './axios'

export const getProfile = () =>
  axios.get('/profile').then(r => r.data)

// Требуется реализовать PUT /api/profile на бэкенде
export const updateProfile = (data) =>
  axios.put('/profile', data).then(r => r.data)
