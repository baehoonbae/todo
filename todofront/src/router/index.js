import { createRouter, createWebHistory } from 'vue-router';
import TodoCalendar from '../components/TodoCalendar.vue';
import TodoBigCalendar from '@/components/TodoBigCalendar.vue';
import CategoryRegist from '@/views/CategoryRegist.vue';
import TodoHome from '@/views/TodoHome.vue';
import UserLogin from '@/views/UserLogin.vue';
import UserSignUp from '@/views/UserSignUp.vue';
import TodoMy from '@/views/TodoMy.vue';
import CategoryList from '@/views/CategoryList.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: TodoHome,
      meta: { hideHeader: true, hideFooter: true },
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: TodoCalendar,
    },
    {
      path: '/big-calendar', 
      name: 'big-calendar',
      component: TodoBigCalendar,
    },
    {
      path: '/my',
      name: 'my', 
      component: TodoMy,
      meta: { hideHeader: true },
    },
    {
      path: '/category',
      name: 'category',
      component: CategoryRegist,
      meta: { hideHeader: true, hideFooter: true },
    },
    {
      path: '/category-list',
      name: 'category-list',
      component: CategoryList,
      meta: { hideHeader: true, hideFooter: true },
    },
    {
      path: '/login',
      name: 'login',
      component: UserLogin,
      meta: { hideHeader: true, hideFooter: true },
    },
    {
      path: '/signup',
      name: 'signup',
      component: UserSignUp, 
      meta: { hideHeader: true, hideFooter: true },
    },
  ]
})

// 인증 필요없는 view 목록
const notRequiredAuths=[
  'login','signup','home',
]

router.beforeEach((to, from, next) => {
  const accessToken = sessionStorage.getItem('accessToken');
  if (!accessToken) {  // 토큰이 없다면
    if (notRequiredAuths.includes(to.name)) {
      next();
    } else {
      next({ name: 'home' }); // TodoHome으로 리다이렉트
    }
  } else {  // 토큰이 있다면
    if (to.name === 'home') {
      next({ name: 'calendar' }); // TodoCalendar로 리다이렉트
    } else {
      next();
    }
  }
})

export default router
