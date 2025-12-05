import axios from './axios'

export const loginUser = (data) =>
  axios.post('/auth/login', data).then(r => r.data)

export const registerUser = (data) =>
  axios.post('/auth/register', data).then(r => r.data)
