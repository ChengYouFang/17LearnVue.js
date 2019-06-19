import Vue from 'vue'
import VueI18n from 'vue-i18n'
import en from './langs/en'
import cn from './langs/cn'

import locale from 'element-ui/lib/locale'
Vue.use(VueI18n)

const messages = {
  en: en,
  cn: cn
}

const i18n = new VueI18n({
  locale: 'en', // 设置默认语言
  messages
})

locale.i18n((key, value) => i18n.t(key, value))
export default i18n
