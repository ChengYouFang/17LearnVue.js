import Vue from 'vue'
import VueRouter from 'vue-router'
// import Login from './view/login.vue'
// import Header from './view/header.vue'
// import Home from './view/home.vue'
// import UserInfo from './view/userinfo.vue'
// import test from './view/test.vue'
// import test2 from './view//h1.vue'
// import test3 from './view/h2.vue'
const Login = () => import(/* webpackChunkName: "group-foo8" */ './view/login.vue')
const Header = () => import(/* webpackChunkName: "group-foo7" */ './view/header.vue')
const Home = () => import(/* webpackChunkName: "group-foo6" */ './view/home.vue')
const UserInfo = () => import(/* webpackChunkName: "group-foo5" */ './view/userinfo.vue')
const test = () => import(/* webpackChunkName: "group-foo4" */ './view/test.vue')
const test2 = () => import(/* webpackChunkName: "group-foo3" */ './view/h1.vue')
const test3 = () => import(/* webpackChunkName: "group-foo2" */ './view/h2.vue')

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    component: Login,
    name: 'login'
  },
  {
    path: '/',
    components: {
      default: Home,
      nav: Header
    },
    name: 'home'
    // ,
    // meta:{
    //   title: '我是首頁'
    // }
  },
  {
    path: '/test',
    components: {
      default: test,
      nav: Header
    },
    name: 'test',

    children: [
      {
        path: '/h1',
        components: {
          nav2: test2
        },
        name: 'h1'
      },
      {
        path: '/h2',
        components: { nav2: test3 },
        name: 'h2'
      }
    ]
  },

  //子路由的<router-view>必须在HelloWorld.vue中出现
  {
    path: '/h2',
    components: {
      default: test3,
      nav: test
    },
    name: 'h2'
  },
  {
    path: '/userInfo',
    components: {
      default: UserInfo,
      nav: Header
    },
    name: 'userinfo'
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
