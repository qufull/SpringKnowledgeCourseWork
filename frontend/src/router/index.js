import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import Profile from '../pages/Profile.vue'
import AdminArticles from '../pages/AdminArticles.vue'
import EditorArticles from '../pages/EditorArticles.vue'
import ArticleView from '../pages/ArticleView.vue'
import ArticleEdit from '../pages/ArticleEdit.vue'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/', name: 'home', component: Home },
  { path: '/login', name: 'login', component: Login },
  { path: '/register', name: 'register', component: Register },
  { path: '/article/:id', name: 'article-view', component: ArticleView, props: true },
  { path: '/article/:id/edit', name: 'article-edit', component: ArticleEdit, props: true },
  { path: '/profile', name: 'profile', component: Profile, meta: { auth: true } },
  { path: '/admin/articles', name: 'admin-articles', component: AdminArticles, meta: { auth: true, role: 'ADMIN' } },
  { path: '/editor/articles', name: 'editor-articles', component: EditorArticles, meta: { auth: true, role: 'EDITOR' } },
  
  {
  path: "/admin/tags",
  name: "admin-tags",
  component: () => import("../pages/AdminTags.vue"),
  meta: { auth: true, role: "ADMIN" }
},
{
  path: "/admin/categories",
  name: "admin-categories",
  component: () => import("../pages/AdminCategories.vue"),
  meta: { auth: true, role: "ADMIN" }
}, {
  path: "/articles/create",
  name: "article-create",
  component: () => import("../pages/ArticleCreate.vue"),
  meta: { auth: true } // страница только для авторизованных
}

]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from) => {
  const user = useUserStore()

  // 1. Проверка аутентификации
  if (to.meta.auth && !user.isAuth) {
    return { name: "login" }
  }

  if (to.meta.role) {
    
    if (user.role !== to.meta.role) {
      
      if (user.role === 'ADMIN') {
        return { name: 'admin-articles' }
      } else if (user.role === 'EDITOR') {
        return { name: 'editor-articles' }
      } else if (user.role === 'READER') {
        return { name: 'home' }
      } else {
        return { name: 'login' }
      }
    }
  }

  // 3. Специфичные проверки для отдельных маршрутов
  if (to.name === "article-create" && user.role === "READER") {
    return { name: "home" }
  }

  return true
})




export default router
