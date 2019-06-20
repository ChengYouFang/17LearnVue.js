import Login from './components/login.vue'
import Header from './components/header.vue'
import Home from './components/table.vue'
import UserInfo from './components/userInfo.vue'

export const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    components: {
      default: Home,
      nav: Header
    }
  },
  {
    path: '/userInfo',
    components: {
      default: UserInfo,
      nav: Header
    }
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
