import { createRouter, createWebHistory } from 'vue-router';
import TodoCalendar from '../components/TodoCalendar.vue';
import TodoBigCalendar from '@/components/TodoBigCalendar.vue';
import CategoryRegist from '@/views/CategoryRegist.vue';
import TodoHome from '@/views/TodoHome.vue';
import UserLogin from '@/views/UserLogin.vue';
import UserSignUp from '@/views/UserSignUp.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      name:'home',
      component: TodoHome,
      meta:{hideHeaderFooter:true},
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: TodoCalendar
    },
    {
      path: '/big-calendar',
      name: 'big-calendar',
      component: TodoBigCalendar
    },
    {
      path:'/category',
      name:'category',
      component: CategoryRegist,
      meta:{hideHeaderFooter:true},
    },
    {
      path:'/login',
      name:'login',
      component: UserLogin,
      meta:{hideHeaderFooter:true},
    },
    {
      path:'/signup',
      name:'signup',
      component: UserSignUp,
      meta:{hideHeaderFooter:true},
    },
  ]
})

export default router
