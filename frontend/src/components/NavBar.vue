<template>
  <nav class="flex items-center justify-between h-14">
    <div class="flex items-center gap-2">
      <div class="w-6 h-6 rounded-md bg-slate-900"></div>
      <router-link
        to="/"
        class="text-sm font-semibold tracking-tight text-slate-900"
      >
        Spring 
      </router-link>
    </div>

    <div class="flex items-center gap-4 text-sm text-slate-600">
      
      <router-link to="/" class="hover:text-slate-900">
        Лента
      </router-link>

      <!-- Только для ADMIN -->
      <template v-if="user.isAuth && user.role === 'ADMIN'">
        <router-link to="/admin/tags" class="hover:text-slate-900">
          Теги
        </router-link>

        <router-link to="/admin/categories" class="hover:text-slate-900">
          Категории
        </router-link>

        <router-link to="/admin/articles" class="hover:text-slate-900">
          Статьи
        </router-link>
      </template>

      <!-- Если пользователь НЕ авторизован -->
      <template v-if="!user.isAuth">
        <router-link to="/login" class="hover:text-slate-900">
          Войти
        </router-link>
        <router-link to="/register" class="hover:text-slate-900">
          Регистрация
        </router-link>
      </template>

      <!-- Если пользователь авторизован -->
      <template v-else>
        <router-link to="/profile" class="hover:text-slate-900">
          {{ user.username }}
        </router-link>

        <button
          @click="logout"
          class="text-slate-400 hover:text-red-500 transition"
        >
          Выход
        </button>
      </template>

    </div>
  </nav>
</template>

<script setup>
import { useUserStore } from '../store/user'
import { useRouter } from 'vue-router'

const user = useUserStore()
const router = useRouter()

const logout = () => {
  user.logout()
  router.push('/')      // редирект на главную
}
</script>
