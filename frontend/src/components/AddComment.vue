<template>
  <div class="bg-white border border-slate-200 rounded-xl p-4 mt-6">
    <h3 class="text-sm font-semibold mb-3">Добавить комментарий</h3>

    <!-- Если не авторизован -->
    <div v-if="!user.isAuth" class="text-xs text-slate-500">
      Чтобы оставить комментарий — <router-link class="text-blue-600" to="/login">войдите в аккаунт</router-link>.
    </div>

    <!-- Форма только для авторизованных -->
    <div v-else>
      <textarea
        v-model="text"
        rows="3"
        placeholder="Ваш комментарий…"
        class="w-full border border-slate-200 rounded-lg px-3 py-2 text-sm outline-none focus:border-slate-400 resize-none"
      ></textarea>

      <div class="flex justify-end mt-3">
        <button
          class="text-xs bg-slate-900 text-white px-3 py-1.5 rounded-lg hover:bg-slate-800"
          @click="submit"
        >
          Отправить
        </button>
      </div>

      <p v-if="error" class="text-xs text-red-500 mt-2">
        {{ error }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useUserStore } from "../store/user"
import { addComment } from "../api/comment"

const props = defineProps({
  articleId: {
    type: Number,
    required: true,
  },
})

const emit = defineEmits(["added"])

const user = useUserStore()
const text = ref("")
const error = ref("")

const submit = async () => {
  error.value = ""

  if (!user.isAuth) {
    error.value = "Вы должны войти в аккаунт"
    return
  }

  if (!text.value.trim()) {
    error.value = "Комментарий не может быть пустым"
    return
  }

  try {
    const created = await addComment(props.articleId, {
      userId: Number(user.userId),
      text: text.value,
    })
    text.value = ""
    emit("added", created)
  } catch (e) {
    error.value = "Не удалось отправить комментарий"
  }
}
</script>
