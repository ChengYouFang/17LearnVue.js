// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
///ui插件
import { Message, Table } from 'element-ui'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import { gettest } from './routes' //路由規則
import axios from 'axios'
import i18n from './assets/i18n/i18n'
import { getRequest } from './api/request'
import { postRequest } from './api/request'
import { deleteRequest } from './api/request'
import { putRequest } from './api/request'
import { getToken } from './api/cookiesex'

Vue.config.productionTip = false
const routes2 = gettest()
///////////router
const router = new VueRouter({
  routes: routes2,
  mode: 'history'
})
router.beforeEach((to, from, next) => {
  // const isLogin = localStorage.getItem('token') == 'ImLogin'
  // if (isLogin) {
  //   next()
  // } else {
  //   if (to.path !== '/login') next('/login')
  //   else next()
  // }

  const isLogin = getToken() == 'ImLogin'
  if (isLogin) {
    next()
  } else {
    if (to.path !== '/login') next('/login')
    else next()
  }
})
Vue.use(VueRouter)

Vue.use(ElementUI)
/////////// 全局引用
Vue.prototype.getRequest = getRequest
Vue.prototype.postRequest = postRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.$message = Message
///////////



///////////
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  components: { App },
  template: '<App/>'
})
