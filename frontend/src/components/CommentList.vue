<template>
  <div class="space-y-3">
    <h3 class="text-sm font-semibold mb-2">Комментарии</h3>

    <div
      v-for="c in comments"
      :key="c.id"
      class="bg-white border border-slate-200 rounded-xl p-3"
    >
      <p class="text-sm text-slate-900">
        {{ c.text }}
      </p>

      <p class="text-[11px] text-slate-500 mt-2">
        {{ c.userName }} · {{ formatDate(c.createdAt) }}
      </p>
    </div>

    <p v-if="!comments.length" class="text-xs text-slate-500">
      Комментариев пока нет.
    </p>
  </div>
</template>

<script setup>
const props = defineProps({
  comments: {
    type: Array,
    required: true,
  },
})

const formatDate = (val) => {
  if (!val) return ""
  return String(val).replace("T", " ").slice(0, 16)
}
</script>
