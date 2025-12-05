<template>
  <div class="max-w-3xl mx-auto">
    <button
        class="text-xs text-slate-500 hover:text-slate-800"
        @click="$router.back()"
      >
        ← Назад
      </button>
    <h1 class="text-xl font-semibold mb-4">Создать статью</h1>

    <div class="space-y-3 bg-white border p-4 rounded-lg">

        

      <input
        v-model="title"
        placeholder="Заголовок"
        class="w-full border rounded-lg px-3 py-2 text-sm"
      />

      <select
        v-model="categoryId"
        class="w-full border rounded-lg px-3 py-2 text-sm"
      >
        <option disabled value="">Выберите категорию</option>
        <option v-for="c in categories" :key="c.id" :value="c.id">
          {{ c.name }}
        </option>
      </select>

      <div>
        <p class="text-xs text-slate-500 mb-1">Теги:</p>
        <div class="flex flex-wrap gap-2">
          <label
            v-for="t in tags"
            :key="t.id"
            class="flex items-center gap-1 text-sm"
          >
            <input type="checkbox" :value="t.id" v-model="tagIds" />
            {{ t.name }}
          </label>
        </div>
      </div>

      <textarea
        v-model="content"
        placeholder="Текст статьи"
        rows="10"
        class="w-full border rounded-lg px-3 py-2 text-sm"
      ></textarea>

      <button
        class="px-4 py-2 bg-slate-900 text-white rounded-lg"
        @click="save"
      >
        Создать
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { createArticle } from "../api/articles"
import { getCategories } from "../api/categories"
import { getTags } from "../api/tags"

const router = useRouter()

const title = ref("")
const content = ref("")
const categoryId = ref("")
const tagIds = ref([])

const categories = ref([])
const tags = ref([])

onMounted(async () => {
  categories.value = await getCategories()
  tags.value = await getTags()
})

const save = async () => {
  const payload = {
    title: title.value,
    content: content.value,
    categoryId: Number(categoryId.value),
    tagIds: tagIds.value.map(Number)
  }

  const created = await createArticle(payload)
  router.push(`/article/${created.id}`)
}
</script>
