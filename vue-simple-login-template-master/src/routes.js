import Login from './components/login.vue'
import Header from './components/header.vue'
import Home from './components/home.vue'
import UserInfo from './components/userInfo.vue'
import test from './components/test.vue'
import test2 from './components/h1.vue'
import test3 from './components/h2.vue'
export const routes = [
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
  ,
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

export function gettest() {
  console.log(routes)
  return routes
}
