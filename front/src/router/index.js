import Vue from 'vue'
import Router from 'vue-router'
import chat from "../components/Chat";
import home from "../view/home";
import test from "../view/test";
import login from "../view/login";
import user from "../view/user/user";
import account from "../view/user/account";
import profile from "../view/user/profile";
import user_home from "../view/user/user_home";
import hot from "../view/hot/hot";
import classify from "../view/class/classify";
import index from "../view/home/index";
import list from "../view/home/list";
import hotlist from "../view/hot/hotlist";
import classifyList from "../view/class/classifyList";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [{
    path:'/test',
      component:test,
  },
    {
      path:'/home',
      component:home,
      meta: {
        requireAuth: true
      },
      children:[{
        path:'/home',
        component:index,
        meta: {
          requireAuth: true
        },
        children:[{
          path:'/home',
          component:list,
          meta: {
            requireAuth: true
          },
        }]
      },{
        path:'/classify',
        component:classify,
        meta: {
          requireAuth: true
        },
        children:[{
          path:'/classify',
          component:classifyList,
          meta: {
            requireAuth: true
          },
        }]
      },{
          path:'/hot',
          component:hot,
          meta: {
            requireAuth: true
          },
          children:[{
            path:'/hot',
            component:hotlist,
            meta: {
              requireAuth: true
            },
          }]
        },{
          path:'/chat/:uid',
          component:chat,
          meta: {
            requireAuth: true
          }
        },{
          path:'/user',
          component: user,

          children:[{
            path:'/user/home',
            component:user_home,
            meta: {
              requireAuth: true
            },
          },{
            path:'/user/profile',
            component: profile,
            meta: {
              requireAuth: true
            },
          },{
            path:'/user/account',
            component:account,
            meta: {
              requireAuth: true
            },
        }]
      }]
    },{
      path: '/login',
      component: login,
      meta: {
        logined: true
      },
    }
  ]
})
