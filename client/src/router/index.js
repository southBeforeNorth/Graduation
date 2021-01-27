import Vue from 'vue';
import lodash from 'lodash';

import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/manage',
    component: () => import('@/components/layout/ManageLayout.vue'),
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: 'user',
        component: () => import('@/views/maintenance/user/User.vue'),
        meta: {
          requireAuth: false
        }
      },
      {
        path: 'dictionary',
        component: () => import('@/views/maintenance/dictionary/Dictionary.vue'),
        meta: {
          requireAuth: true
        }
      }
    ]
  },
  {
    path: '/register',
    component: () => import('@/views/feture/register/Register.vue'),
    meta: {
      requireAuth: false
    }
  },
  {
    path: '/login',
    component: () => import('@/views/feture/login/Login.vue'),
    meta: {
      requireAuth: false
    }
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    const token = localStorage.getItem('token');
    localStorage.setItem('preRoute', to.fullPath);
    if (lodash.isEmpty(token)) {
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
