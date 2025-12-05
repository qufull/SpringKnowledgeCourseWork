<template>
  <div class="max-w-4xl mx-auto">
    <div class="flex items-center justify-between mb-4">
      <h1 class="text-base font-semibold">Все статьи (админ)</h1>
      <button
        class="text-xs px-3 py-1.5 border border-slate-300 rounded-lg hover:border-slate-500"
        @click="createNew"
      >
        Новая статья
      </button>
    </div>

    <Loader v-if="loading" />

    <div v-else class="space-y-2">
      <div
        v-for="a in articles"
        :key="a.id"
        class="bg-white border border-slate-200 rounded-xl px-4 py-3 flex items-center justify-between"
      >
        <div>
          <p class="text-sm font-medium text-slate-900">
            {{ a.title }}
          </p>

          <p class="text-[11px] text-slate-500">
            ID: {{ a.id }}
          </p>

          <p class="text-[11px] text-slate-500">
            Автор:
            <span class="font-medium">
              {{ a.name || 'Без автора' }}
            </span>
          </p>
        </div>

        <div class="flex items-center gap-2 text-xs">
          <button
            class="px-2 py-1 border border-slate-300 rounded-lg hover:border-slate-500"
            @click="edit(a.id)"
          >
            Редактировать
          </button>
          <button
            class="px-2 py-1 border border-red-300 text-red-600 rounded-lg hover:border-red-500"
            @click="remove(a.id)"
          >
            Удалить
          </button>
        </div>
      </div>

      <p v-if="!articles.length" class="text-xs text-slate-500">
        Статей пока нет.
      </p>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAllArticles, deleteArticle, createArticle } from '../api/articles'
import Loader from '../components/Loader.vue'

const articles = ref([])
const loading = ref(false)
const router = useRouter()

const load = async () => {
  loading.value = true
  articles.value = await getAllArticles()
  loading.value = false
}

const edit = (id) => {
  router.push({ name: 'article-edit', params: { id } })
}

const remove = async (id) => {
  if (!confirm('Удалить статью?')) return
  await deleteArticle(id)
  await load()
}

const createNew = () => {
  router.push("/articles/create")
}

onMounted(load)
</script>
