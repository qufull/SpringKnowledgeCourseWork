<template>
  <div class="max-w-3xl mx-auto">
    <div class="flex items-center justify-between mb-4">
      <button
        class="text-xs text-slate-500 hover:text-slate-800"
        @click="$router.back()"
      >
        ← Назад
      </button>
    </div>

    <div class="bg-white border border-slate-200 rounded-xl p-5">
      <h1 class="text-lg font-semibold mb-2">
        {{ article.title }}
      </h1>
      <p class="text-[11px] text-slate-400 mb-4">
        {{ formattedDate }}
      </p>
      <p class="text-xs text-slate-500 mb-3">
  Автор: {{ article.name }}
</p>
      <div class="prose prose-slate max-w-none text-sm" v-html="article.content"></div>
      
    </div>
    <CommentList :comments="article.comments || []" />

<AddComment
  :articleId="Number(route.params.id)"
  @added="onCommentAdded"
/>

  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getArticle, deleteArticle } from '../api/articles'
import { useUserStore } from '../store/user'
import CommentList from "../components/CommentList.vue"
import AddComment from "../components/AddComment.vue"

const route = useRoute()
const router = useRouter()
const store = useUserStore()

const article = ref({})

const load = async () => {
  article.value = await getArticle(route.params.id)
}

const formattedDate = computed(() => {
  if (!article.value.createdAt) return ''
  return String(article.value.updatedAt).replace('T', ' ').slice(0, 16)
})

const canEdit = computed(() =>
  store.role === 'ADMIN' || (store.role === 'EDITOR' && store.id == article.value.name) 
)

const edit = () => {
  router.push({ name: 'article-edit', params: { id: route.params.id } })
}

const remove = async () => {
  if (!confirm('Удалить статью?')) return
  await deleteArticle(route.params.id)
  router.push({ name: 'home' })
}

const onCommentAdded = (comment) => {
  article.value.comments.push(comment)
}

onMounted(load)
</script>
