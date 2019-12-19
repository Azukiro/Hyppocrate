import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {/*Redirect*/
      path: '/test',
      name: 'test',
      redirect: () => 'home'
    },
    {
      path: '/home',
      name: 'home',
      component: () => import(
        './views/Home.vue'
      )
    }
  ]
})