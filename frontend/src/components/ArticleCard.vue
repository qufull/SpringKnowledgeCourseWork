<template>
  <article
    class="bg-white border border-slate-200 rounded-xl px-4 py-3 mb-3 hover:border-slate-300 transition cursor-pointer"
    @click="goTo"
  >
    <div class="flex items-center justify-between gap-2 mb-1">
      <h2 class="font-semibold text-slate-900 text-sm line-clamp-1">
        {{ article.title }}
      </h2>
      <span class="text-[11px] text-slate-400">
        {{ formattedDate }}
      </span>
    </div>
  
  </article>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  article: {
    type: Object,
    required: true,
  },
})

const router = useRouter()

const goTo = () => {
  router.push({ name: 'article-view', params: { id: props.article.id } })
}

const preview = computed(() =>
  props.article.content ? props.article.content.slice(0, 160) + '…' : ''
)

const formattedDate = computed(() => {
  if (!props.article.createdAt) return ''
  return String(props.article.createdAt).replace('T', ' ').slice(0, 16)
})
</script>
