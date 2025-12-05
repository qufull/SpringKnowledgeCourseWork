<template>
  <div class="grid gap-6 md:grid-cols-[2fr,1fr]">
    <section>
      <div class="mb-4">
        <div class="flex items-center gap-2 border border-slate-200 rounded-xl px-3 py-2 bg-white">
          <input
            v-model="search"
            @input="onSearch"
            type="text"
            placeholder="Поиск по статьям…"
            class="w-full bg-transparent text-sm outline-none text-slate-800 placeholder:text-slate-400"
          />
        </div>
      </div>

      <Loader v-if="loading" />
      <div v-else>
        <ArticleCard
          v-for="a in articles"
          :key="a.id"
          :article="a"
        />
        <p v-if="!articles.length && !loading" class="text-xs text-slate-500">
          Статей пока нет.
        </p>
      </div>
    </section>

    <aside class="space-y-4">
      <section class="bg-white border border-slate-200 rounded-xl p-3">
        <h3 class="text-xs font-semibold text-slate-700 mb-2">
          Категории
        </h3>
        <div class="flex flex-wrap gap-1">
          <button
            class="px-2 py-1 rounded-full text-[11px] border border-slate-200 hover:border-slate-400 hover:text-slate-900 transition"
            @click="loadAll"
          >
            Все
          </button>
          <button
            v-for="cat in categories"
            :key="cat.id"
            class="px-2 py-1 rounded-full text-[11px] border border-slate-200 hover:border-slate-400 hover:text-slate-900 transition"
            @click="loadCategory(cat.id)"
          >
            {{ cat.name }}
          </button>
        </div>
      </section>
    </aside>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ArticleCard from '../components/ArticleCard.vue'
import Loader from '../components/Loader.vue'
import { getAllArticles, searchArticles, getByCategory } from '../api/articles'
import { getCategories } from '../api/categories'

const articles = ref([])
const categories = ref([])
const loading = ref(false)
const search = ref('')

const loadAll = async () => {
  loading.value = true
  articles.value = await getAllArticles()
  loading.value = false
}

const loadCategories = async () => {
  categories.value = await getCategories()
}

const onSearch = async () => {
  const q = search.value.trim()
  if (!q) {
    await loadAll()
  } else {
    loading.value = true
    articles.value = await searchArticles(q)
    loading.value = false
  }
}

const loadCategory = async (id) => {
  loading.value = true
  articles.value = await getByCategory(id)
  loading.value = false
}

onMounted(async () => {
  await loadAll()
  await loadCategories()
})
</script>
