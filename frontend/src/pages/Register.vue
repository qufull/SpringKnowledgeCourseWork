<template>
  <div class="max-w-sm mx-auto mt-10">
    <div class="bg-white border border-slate-200 rounded-xl p-5">
      <h1 class="text-base font-semibold mb-4">Регистрация</h1>

      <form @submit.prevent="submit" class="space-y-3">
        <div class="space-y-1">
          <label class="text-xs text-slate-600">Логин</label>
          <input
            v-model="username"
            type="text"
            class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
          />
        </div>

        <div class="space-y-1">
          <label class="text-xs text-slate-600">Пароль</label>
          <input
            v-model="password"
            type="password"
            class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
          />
        </div>

        <p v-if="error" class="text-xs text-red-500">
          {{ error }}
        </p>

        <button
          type="submit"
          class="w-full mt-2 bg-slate-900 text-white text-sm py-2 rounded-lg hover:bg-slate-800 transition"
        >
          Зарегистрироваться
        </button>
      </form>

      <p class="mt-3 text-[11px] text-slate-500">
        Уже есть аккаунт?
        <router-link to="/login" class="underline">
          Войти
        </router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const username = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()
const store = useUserStore()

const submit = async () => {
  error.value = ''
  try {
    await store.register(username.value, password.value)
    router.push({ name: 'home' })
  } catch (e) {
    error.value = 'Ошибка регистрации'
  }
}
</script>
