<template>
  <div class="max-w-3xl mx-auto space-y-6">
    <section class="bg-white border border-slate-200 rounded-xl p-5">
      <h1 class="text-base font-semibold mb-3">Профиль</h1>

      <div class="space-y-2 text-sm">
        <p><span class="text-slate-500">Логин:</span> {{ profile.username }}</p>
        <p><span class="text-slate-500">Роль:</span> {{ store.role }}</p>
      </div>

    
    </section>

    <section v-if="store.role === 'EDITOR'" class="bg-white border border-slate-200 rounded-xl p-5">
      <div class="flex items-center justify-between mb-3">
        <h2 class="text-sm font-semibold">Мои статьи</h2>
        <router-link
          to="/editor/articles"
          class="text-xs text-slate-500 underline"
        >
          Управление
        </router-link>
      </div>
      <ArticleCard
        v-for="a in articles"
        :key="a.id"
        :article="a"
      />
      <p v-if="!articles.length" class="text-xs text-slate-500">
        Статей пока нет.
      </p>
    </section>

  
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import { getProfile, updateProfile } from '../api/profile'
import { getMyArticles } from '../api/articles'
import ArticleCard from '../components/ArticleCard.vue'

const store = useUserStore()

const profile = ref({})
const form = ref({ username: '' })
const editMode = ref(false)
const articles = ref([])

const loadProfile = async () => {
  const data = await getProfile()
  profile.value = data
  form.value.username = data.username
}

const loadArticles = async () => {
  // Сейчас просто все статьи. Фильтрацию по автору можно добавить,
  // когда на бэкенде будет возвращаться автор.
  articles.value = await getMyArticles(profile.username)
  console.log(articles.value)
}

const save = async () => {
  try {
    const updated = await updateProfile({ username: form.value.username })
    profile.value = updated
    editMode.value = false
  } catch (e) {
    // PUT /api/profile должен быть реализован на бэкенде
  }
}

onMounted(async () => {
  await loadProfile()
  await loadArticles()
})
</script>
