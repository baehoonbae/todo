import { createRouter, createWebHistory } from 'vue-router';
import TodoCalendar from '../components/TodoCalendar.vue';
import TodoBigCalendar from '@/components/TodoBigCalendar.vue';
import CategoryRegist from '@/views/CategoryRegist.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: TodoCalendar
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: TodoBigCalendar
    },
    {
      path:'/category',
      name:'category',
      component: CategoryRegist,
      meta:{hideHeaderFooter:true},
    },
    {

    },
  ]
})

export default router
