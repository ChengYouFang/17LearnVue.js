import Vue from 'vue'
import Console from './Client.vue'
import router from './router'
import { test } from '../../api/test'
Vue.use(require('vue-wechat-title'))
console.log(test())
new Vue({
  router,
  render: h => h(Console)
}).$mount('#client')
