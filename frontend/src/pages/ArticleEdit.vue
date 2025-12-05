<template>
  <div class="max-w-3xl mx-auto">
    <div class="flex items-center justify-between mb-4">
      <button
        class="text-xs text-slate-500 hover:text-slate-800"
        @click="goBack"
      >
        ← Назад
      </button>
      <h1 class="text-xl font-semibold">
        {{ isNew ? 'Создать статью' : 'Редактировать статью' }}
      </h1>
    </div>

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

      <div class="flex gap-2">
        <button
          class="px-4 py-2 bg-slate-900 text-white rounded-lg hover:bg-slate-800"
          @click="save"
          :disabled="isSaving"
        >
          {{ isSaving ? 'Сохранение...' : (isNew ? 'Создать' : 'Сохранить') }}
        </button>
        
        <button
          v-if="!isNew"
          class="px-4 py-2 border border-slate-300 text-slate-700 rounded-lg hover:bg-slate-50"
          @click="goToView"
        >
          Отмена
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue"
import { useRouter, useRoute } from "vue-router"
import { createArticle, updateArticle, getArticleByUpdate } from "../api/articles"
import { getCategories } from "../api/categories"
import { getTags } from "../api/tags"

const router = useRouter()
const route = useRoute()

const title = ref("")
const content = ref("")
const categoryId = ref("")
const tagIds = ref([])
const isSaving = ref(false)

const categories = ref([])
const tags = ref([])

// Проверяем, создаем ли новую статью или редактируем существующую
const isNew = computed(() => route.params.id === 'new')

onMounted(async () => {
  try {
    // Загружаем категории и теги
    categories.value = await getCategories()
    tags.value = await getTags()

    // Если редактируем существующую статью - загружаем её данные
    if (!isNew.value) {
      await loadArticleData()
    }
  } catch (error) {
    console.error("Ошибка загрузки данных:", error)
    alert("Не удалось загрузить данные")
  }
})

const loadArticleData = async () => {
  try {
    const data = await getArticleByUpdate(route.params.id)
    
    console.log("Данные статьи для редактирования:", data)
    
    title.value = data.title || ""
    content.value = data.content || ""
    
    // categoryId - это число, а не объект
    categoryId.value = data.categoryId || ""
    
    // tagIds - это массив чисел [1, 2, 3]
    if (Array.isArray(data.tagIds)) {
      tagIds.value = data.tagIds
    } else if (data.tagIds && typeof data.tagIds === 'object') {
      tagIds.value = Array.from(data.tagIds)
    } else {
      tagIds.value = []
    }
    
  } catch (error) {
    console.error("Ошибка загрузки статьи:", error)
    alert("Не удалось загрузить данные статьи")
  }
}

const goBack = () => {
  if (window.history.length > 1) {
    router.back()
  } else {
    // Возвращаем на страницу в зависимости от роли
    router.push({ name: 'home' })
  }
}

const goToView = () => {
  if (!isNew.value) {
    router.push({
      name: 'article-view',
      params: { id: route.params.id }
    })
  }
}

const save = async () => {
  if (isSaving.value) return
  
  // Валидация
  if (!title.value.trim()) {
    alert("Введите заголовок статьи")
    return
  }
  
  if (!categoryId.value) {
    alert("Выберите категорию")
    return
  }
  
  isSaving.value = true
  
  try {
    const payload = {
      title: title.value.trim(),
      content: content.value.trim(),
      categoryId: Number(categoryId.value),
      tagIds: tagIds.value.map(id => Number(id))
    }

    console.log("Отправка данных:", payload)

    if (isNew.value) {
      // Создание новой статьи
      const created = await createArticle(payload)
      
      // Успешно создано - переходим на просмотр статьи
      router.push({
        name: 'article-view',
        params: { id: created.id }
      })
      
    } else {
      // Редактирование существующей статьи
      await updateArticle(route.params.id, payload)
      
      // Успешно обновлено - переходим на просмотр статьи
      router.push({
        name: 'article-view',
        params: { id: route.params.id }
      })
    }
    
  } catch (error) {
    console.error("Ошибка сохранения:", error)
    alert("Не удалось сохранить статью. Проверьте данные и попробуйте снова.")
  } finally {
    isSaving.value = false
  }
}
</script>