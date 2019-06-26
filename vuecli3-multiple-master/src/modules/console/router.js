import Vue from 'vue'
import VueRouter from 'vue-router'
import Header from './view/header.vue'
import Home from './view/home.vue'
import Login from './view/login.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    components: {
      default: Home,
      nav: Header
    },
    name: 'home'
  },
  {
    path: '/login',
    component: Login,
    name: 'login'
  },
  {
    path: '*',
    redirect: '/'
  }
]

export default new VueRouter({
  routes: routes,
  mode: 'history'
})
