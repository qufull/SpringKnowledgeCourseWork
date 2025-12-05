import { defineStore } from 'pinia'
import { loginUser, registerUser } from '../api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    username: localStorage.getItem('username') || null,
    role: localStorage.getItem('role') || null,
    userId: localStorage.getItem('userId') || null,
  }),

  getters: {
    isAuth: (state) => !!state.token,
  },

  actions: {
    async login(username, password) {
      const res = await loginUser({ username, password })
      this._applyAuth(res)
    },

    async register(username, password) {
      const res = await registerUser({ username, password })
      this._applyAuth(res)
    },

    _applyAuth(res) {
      this.token = res.accessToken
      this.username = res.username
      this.role = res.role
      this.userId = String(res.userId)


      localStorage.setItem('token', this.token)
      localStorage.setItem('username', this.username)
      localStorage.setItem('role', this.role)
      localStorage.setItem('userId', this.userId)
    },

    logout() {
      this.token = null
      this.username = null
      this.role = null
      this.userId = null
      localStorage.clear()
    },
  },
})
