import Vue from 'vue';
import lodash from 'lodash';
import VueRouter from 'vue-router';
import store from '@/store';

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
    path: '/feature',
    component: () => import('@/components/layout/FeatureLayout.vue'),
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: 'home',
        component: () => import('@/views/feture/home/Home.vue'),
        meta: {
          requireAuth: false
        }
      },
      {
        path: 'register',
        component: () => import('@/views/feture/register/Register.vue'),
        meta: {
          requireAuth: false
        }
      },
      {
        path: 'login',
        component: () => import('@/views/feture/login/Login.vue'),
        meta: {
          requireAuth: false
        }
      }
    ]
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (from.fullPath === '/feature/home') {
    localStorage.setItem('preRoute', from.fullPath);
  }
  const token = localStorage.getItem('token');
  if (lodash.isEmpty(token)) {
    store.commit('SET_IS_LOGIN', false);
    store.commit('SET_IS_NAME', '');
    if (to.meta.requireAuth) {
      localStorage.setItem('preRoute', to.fullPath);
      next('feature/login');
    }
  }
  next();
});

export default router;
