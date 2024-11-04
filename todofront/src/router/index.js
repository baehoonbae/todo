import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import Login from '../components/Login.vue';
import Join from '../components/Join.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    
    // {
    //   path:'/onlyCalender',
    //   name:'onlyCalender',
    //   component: OnlyCalenderView
    // },
    // {
    //   path:'/calenderAndCategory',
    //   name:'calenderAndCategory',
    //   component: CalenderAndCategory
    // },
  ]
})

export default router
