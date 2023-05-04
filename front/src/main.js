// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import store from './store'

import global from './util/global.js'
Vue.prototype.global = global

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.prototype.$axios = axios
axios.defaults.baseURL='http://localhost:8081'
axios.defaults.withCredentials = true //为了让前端能够带上 cookie


router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
      if (store.state.user.username) {
        // axios.post('/authentication').then(resp => {
        //   console.log(resp);
        //   if (resp) next()
        // })
        next()
      } else {
        next({
          //跳转到登入页
          path: 'login',
          query: {redirect: to.fullPath}
        })
      }
    } else {
      next()
    }
    if (to.meta.logined) {
      if (store.state.user.username) {
        next({
          //跳转到登入页
          path: 'home',
          query: {redirect: to.fullPath}
        })
      } else {
        next()
      }
    }
  }
)


/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
