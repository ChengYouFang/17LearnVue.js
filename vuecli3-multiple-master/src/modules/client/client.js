import Vue from 'vue'

import Console from './Client.vue'
import { Message, Table } from 'element-ui'
import i18n from '../../assets/i18n/i18n'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import { getRequest } from '../../api/request'
import { postRequest } from '../../api/request'
import { deleteRequest } from '../../api/request'
import { putRequest } from '../../api/request'
import { getToken } from '../../api/cookiesex'

import router from './router'
Vue.config.productionTip = false

/////////// 全局引用
Vue.prototype.getRequest = getRequest
Vue.prototype.postRequest = postRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.$message = Message
///////////

Vue.use(require('vue-wechat-title'))

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
Vue.use(ElementUI)
new Vue({
  router,
  i18n,
  render: h => h(Console)
}).$mount('#client')
