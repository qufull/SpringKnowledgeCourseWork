<template>
  <div class="max-w-xl mx-auto space-y-5">
    <h1 class="text-base font-semibold">Управление категориями</h1>

    <div class="bg-white border border-slate-200 rounded-xl p-4 space-y-3">
      <h2 class="text-sm font-semibold">Добавить категорию</h2>

      <input
        v-model="form.name"
        placeholder="Название"
        class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
      />

      <input
        v-model="form.description"
        placeholder="Описание"
        class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
      />

      <button
        class="bg-slate-900 text-white px-3 py-2 rounded-lg text-sm hover:bg-slate-800"
        @click="create"
      >
        Создать
      </button>
    </div>

    <div class="bg-white border border-slate-200 rounded-xl p-4 space-y-2">
      <h2 class="text-sm font-semibold">Список категорий</h2>

      <div
        v-for="c in categories"
        :key="c.id"
        class="border border-slate-200 rounded-lg p-3 space-y-1"
      >
        <p class="text-sm font-medium">{{ c.name }}</p>
        <p class="text-xs text-slate-500">{{ c.description }}</p>

        <div class="flex gap-2 mt-1">
          <button
            class="text-xs text-blue-600 hover:text-blue-800"
            @click="startEdit(c)"
          >
            изменить
          </button>

          <button
            class="text-xs text-red-600 hover:text-red-800"
            @click="remove(c.id)"
          >
            удалить
          </button>
        </div>
      </div>

      <p v-if="!categories.length" class="text-xs text-slate-500">
        Категорий нет.
      </p>
    </div>

    <div
      v-if="editMode"
      class="bg-white border border-slate-300 rounded-xl p-4 space-y-3"
    >
      <h2 class="text-sm font-semibold">Редактировать категорию</h2>

      <input
        v-model="editForm.name"
        class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
      />

      <input
        v-model="editForm.description"
        class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400"
      />

      <button
        class="bg-blue-600 text-white px-3 py-2 rounded-lg text-sm hover:bg-blue-500"
        @click="update"
      >
        Сохранить
      </button>

      <button
        class="text-xs text-slate-500 underline"
        @click="editMode = false"
      >
        отмена
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import {
  getCategories,
  createCategory,
  updateCategory,
  deleteCategory,
} from "../api/categories";

const categories = ref([]);

const form = ref({
  name: "",
  description: "",
});

const editForm = ref({});
const editMode = ref(false);
let editId = null;

const load = async () => {
  categories.value = await getCategories();
};

const create = async () => {
  if (!form.value.name.trim()) return;

  await createCategory(form.value);
  form.value = { name: "", description: "" };
  load();
};

const startEdit = (c) => {
  editMode.value = true;
  editId = c.id;
  editForm.value = { name: c.name, description: c.description };
};

const update = async () => {
  await updateCategory(editId, editForm.value);
  editMode.value = false;
  load();
};

const remove = async (id) => {
  if (!confirm("Удалить категорию?")) return;
  await deleteCategory(id);
  load();
};

onMounted(load);
</script>
