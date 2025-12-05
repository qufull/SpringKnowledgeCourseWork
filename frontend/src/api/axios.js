import axios from 'axios'
import { useUserStore } from '../store/user'

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
})

instance.interceptors.request.use((config) => {
  try {
    const store = useUserStore()
    if (store?.token) {
      config.headers.Authorization = `Bearer ${store.token}`
    }
  } catch (e) {
    // Pinia not initialized yet
  }
  return config
})

export default instance
