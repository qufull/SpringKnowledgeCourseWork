<template>
  <div class="max-w-xl mx-auto space-y-5">
    <h1 class="text-base font-semibold">Управление тегами</h1>

    <div class="bg-white border border-slate-200 rounded-xl p-4">
      <h2 class="text-sm font-semibold mb-3">Добавить тег</h2>

      <div class="flex gap-2">
        <input
          v-model="newTag"
          placeholder="Название тега"
          class="flex-1 border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
        />

        <button
          class="px-3 py-2 bg-slate-900 text-white text-sm rounded-lg hover:bg-slate-800"
          @click="create"
        >
          +
        </button>
      </div>
    </div>

    <div class="bg-white border border-slate-200 rounded-xl p-4 space-y-2">
      <h2 class="text-sm font-semibold">Список тегов</h2>

      <div
        v-for="t in tags"
        :key="t.id"
        class="flex items-center justify-between border border-slate-200 rounded-lg px-3 py-2"
      >
        <span class="text-sm">{{ t.name }}</span>

        <button
          class="text-xs text-red-500 hover:text-red-700"
          @click="remove(t.id)"
        >
          удалить
        </button>
      </div>

      <p v-if="!tags.length" class="text-xs text-slate-500">
        Тегов нет.
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getTags, createTag, deleteTag } from "../api/tags";

const tags = ref([]);
const newTag = ref("");

const load = async () => {
  tags.value = await getTags();
};

const create = async () => {
  if (!newTag.value.trim()) return;
  await createTag({ name: newTag.value });
  newTag.value = "";
  load();
};

const remove = async (id) => {
  if (!confirm("Удалить тег?")) return;
  await deleteTag(id);
  load();
};

onMounted(load);
</script>
